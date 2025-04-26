package descartes.info.pluginmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;

import com.tencent.shadow.core.manager.installplugin.InstallPluginException;
import com.tencent.shadow.core.manager.installplugin.InstalledPlugin;
import com.tencent.shadow.core.manager.installplugin.InstalledType;
import com.tencent.shadow.core.manager.installplugin.PluginConfig;
import com.tencent.shadow.dynamic.host.EnterCallback;
import com.tencent.shadow.dynamic.host.FailedException;
import com.tencent.shadow.dynamic.manager.PluginManagerThatUseDynamicLoader;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/***
 * gestion plugin (Analyse config.json / Load plugin)
 * open plugin
 */
public class MyPluginManager extends PluginManagerThatUseDynamicLoader {
    private static final String TAG = "ShiTestPluginManager";
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private ExecutorService mFixedPool = Executors.newFixedThreadPool(4);
    
    public MyPluginManager(Context context) {
        super(context);
        Log.d(TAG, "PluginManager---------------->constructor");
    }

    //TODO

    /**
     *
     * @return
     */
    @Override
    protected String getName() {
        Log.d(TAG, "PluginManager---------------->getName");
        return "manager-project";
    }

    @Override
    public void enter(Context context, long fromId, Bundle bundle, EnterCallback callback) {
        Log.d(TAG, "PluginManager---------------->enter");
        final String pluginZipPath = bundle.getString("plugin_path_from_host");
        final String partKey = bundle.getString("part_key");
        final String className = bundle.getString("activity_class_name");
                    
        if(className == null){
            Log.e(TAG, "PluginManager---------------->className is null");
            throw new NullPointerException("className == null");
        }

        File pluginFile = new File(pluginZipPath);
        if (!pluginFile.exists()) {
            Log.e(TAG, "PluginManager---------------->plugin file not found");
            if (callback != null) {
                callback.onCloseLoadingView();
            }
            throw new RuntimeException("Plugin file not found: " + pluginZipPath);
        }
        
        final Bundle extras = bundle.getBundle("extra_to_plugin_bundle");
        if(callback != null){
            callback.onShowLoadingView(null);
        }
        
        executorService.execute(new Runnable(){
            @Override
            public void run() {
                try {
                    Log.d(TAG, "PluginManager---------------->start install plugin");
                    InstalledPlugin installedPlugin = installedPlugin(pluginZipPath,null,true);
                    
                    Intent pluginIntent = new Intent();
                    pluginIntent.setClassName(context.getPackageName(), className);
                    
                    if (extras != null){
                        pluginIntent.replaceExtras(extras);
                    }
                    
                    Log.d(TAG, "PluginManager---------------->start plugin activity");
                    startPluginActivity(context, installedPlugin, partKey, pluginIntent);
                } catch (Exception e) {
                    Log.e(TAG, "PluginManager---------------->error:" + e.getMessage(), e);
                    if(callback != null){
                        callback.onCloseLoadingView();
                    }
                }
            }
        });
    }

    public void startPluginActivity(Context context, InstalledPlugin installedPlugin, String partKey, Intent pluginIntent) throws RemoteException, FailedException, TimeoutException {
        Log.d(TAG, "PluginManager---------------->startPluginActivity");
        Intent intent = convertActivityIntent(installedPlugin, partKey, pluginIntent);
        if(!(context instanceof Activity)){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public Intent convertActivityIntent(InstalledPlugin installedPlugin, String partKey, Intent pluginIntent) throws RemoteException, FailedException, TimeoutException {
        Log.d(TAG, "PluginManager---------------->convertActivityIntent");
        
        loadPlugin(installedPlugin.UUID, partKey);
        
        if (pluginIntent.getComponent() == null) {
            Log.e(TAG, "PluginManager---------------->Error: pluginIntent component is null");
            throw new IllegalArgumentException("Intent component cannot be null");
        }
        
        try {
            Intent convertedIntent = mPluginLoader.convertActivityIntent(pluginIntent);
            
            if (convertedIntent.getComponent() == null) {
                Log.e(TAG, "PluginManager---------------->Converted Intent has NULL component!");
            }
            
            return convertedIntent;
        } catch (Exception e) {
            Log.e(TAG, "PluginManager---------------->Error converting Activity Intent: " + e.getMessage(), e);
            throw e;
        }
    }

    protected void loadPlugin(String uuid, String partKey) throws RemoteException, TimeoutException, FailedException {
        Log.d(TAG, "PluginManager---------------->loadPlugin");
        
        try {
            loadPluginLoaderAndRuntime(uuid);
            
            Map map = mPluginLoader.getLoadedPlugin();
            
            if (!map.containsKey(partKey)) {
                Log.d(TAG, "PluginManager---------------->loading plugin");
                mPluginLoader.loadPlugin(partKey);
                
                try {
                    // 检查插件组件
                } catch (Exception e) {
                    Log.e(TAG, "PluginManager---------------->Error getting plugin info: " + e.getMessage());
                }
            }
            
            Boolean isCall = (Boolean) map.get(partKey);
            
            if (isCall == null || !isCall) {
                Log.d(TAG, "PluginManager---------------->calling Application.onCreate");
                mPluginLoader.callApplicationOnCreate(partKey);
            }
        } catch (Exception e) {
            Log.e(TAG, "PluginManager---------------->Error in loadPlugin: " + e.getMessage(), e);
            throw e;
        }
    }

    private void loadPluginLoaderAndRuntime(String uuid) throws RemoteException, TimeoutException, FailedException {
        Log.d(TAG, "PluginManager---------------->loadPluginLoaderAndRuntime");
        if (mPpsController == null) {
            Log.d(TAG, "PluginManager---------------->bind service");
            bindPluginProcessService(getPluginProcessServiceName());
            waitServiceConnected(10, TimeUnit.SECONDS);
        }
        
        if (mPpsController == null) {
            throw new IllegalStateException("mPpsController is still null after service connected");
        }
        
        Log.d(TAG, "PluginManager---------------->load runtime");
        loadRunTime(uuid);
        
        Log.d(TAG, "PluginManager---------------->load plugin loader");
        loadPluginLoader(uuid);
        
        if (mPluginLoader == null) {
            throw new IllegalStateException("mPluginLoader is null after loadPluginLoader");
        }
    }

    /**
     *
     * @return Class MainPluginProcessService in host
     */
    private String getPluginProcessServiceName() {
        return "descartes.info.l3p2.service.MainPluginProcessService";
    }


    public InstalledPlugin installedPlugin(String zip, String hash, Boolean odex) throws IOException{
        Log.d(TAG, "PluginManager---------------->installedPlugin");
        final PluginConfig pluginConfig;
        try {
            pluginConfig = installPluginFromZip(new File(zip),hash);
        } catch (JSONException e) {
            Log.e(TAG, "PluginManager---------------->config error:" + e.getMessage(), e);
            throw new RuntimeException(e);
        }
        final String uuid = pluginConfig.UUID;
        List<Future> futures = new LinkedList<>();
        if(pluginConfig.runTime != null && pluginConfig.pluginLoader != null){
            Future odexRunTime = mFixedPool.submit(new Callable() {
                @Override
                public Object call() throws Exception{
                    Log.d(TAG, "PluginManager---------------->process runtime");
                    oDexPluginLoaderOrRunTime(uuid,InstalledType.TYPE_PLUGIN_RUNTIME,pluginConfig.runTime.file);
                    return null;
                }
            });
            futures.add(odexRunTime);
            Future odexLoader = mFixedPool.submit(new Callable() {
                @Override
                public Object call() throws Exception{
                    Log.d(TAG, "PluginManager---------------->process loader");
                    oDexPluginLoaderOrRunTime(uuid,InstalledType.TYPE_PLUGIN_LOADER,pluginConfig.pluginLoader.file);
                    return null;
                }
            });
            futures.add(odexLoader);
        }
        for(Map.Entry<String, PluginConfig.PluginFileInfo> plugin : pluginConfig.plugins.entrySet()) {
            final String partKey = plugin.getKey();
            final File apkFile = plugin.getValue().file;
            Future extractSo = mFixedPool.submit(new Callable() {
                @Override
                public Object call() throws Exception{
                    Log.d(TAG, "PluginManager---------------->extract .so");
                    extractSo(uuid,partKey,apkFile);
                    return null;
                }
            });
            futures.add(extractSo);

            Future odexPlugin = mFixedPool.submit(new Callable() {
                @Override
                public Object call() throws Exception{
                    Log.d(TAG, "PluginManager---------------->process .dex");
                    oDexPlugin(uuid,partKey,apkFile);
                    return null;
                }
            });
            futures.add(odexPlugin);
        }
        for (Future future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                Log.e(TAG, "PluginManager---------------->task error:" + e.getMessage(), e);
            }
        }
        onInstallCompleted(pluginConfig);
        return getInstalledPlugins(1).get(0);
    }
}
