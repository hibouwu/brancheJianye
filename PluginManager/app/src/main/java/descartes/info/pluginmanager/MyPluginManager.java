package descartes.info.pluginmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;

import com.tencent.shadow.core.manager.BasePluginManager;
import com.tencent.shadow.core.manager.installplugin.AppCacheFolderManager;
import com.tencent.shadow.core.manager.installplugin.InstallPluginException;
import com.tencent.shadow.core.manager.installplugin.InstalledPlugin;
import com.tencent.shadow.core.manager.installplugin.InstalledType;
import com.tencent.shadow.core.manager.installplugin.PluginConfig;
import com.tencent.shadow.dynamic.host.EnterCallback;
import com.tencent.shadow.dynamic.host.FailedException;
import com.tencent.shadow.dynamic.manager.PluginManagerThatUseDynamicLoader;
import descartes.info.pluginmanager.Constant;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.LogManager;
/**
 * Gestionnaire de plugins pour l'application Shadow Framework.
 * Cette classe gère l'installation, le chargement et l'exécution des plugins.
 * Elle s'occupe de l'initialisation des plugins, de la gestion des états et
 * de la communication entre l'hôte et les plugins.
 * 
 * @author Shi Jianye
 */
public class MyPluginManager extends PluginManagerThatUseDynamicLoader {
    private static final String TAG = "ShiTestPluginManager";
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private ExecutorService mFixedPool = Executors.newFixedThreadPool(4);
    private Context mCurrentContext;
    
    public MyPluginManager(Context context) {
        super(context);
        mCurrentContext = context;
    }

    @Override
    protected String getName() {
        return "manager-project";
    }
    public String getAbi() {
        return "";
    }

    @Override
    public void enter(Context context, long fromId, Bundle bundle, EnterCallback callback) {
        mCurrentContext = context;
        
        if (fromId == 1000) { //Constant.FROM_ID_NOOP = 1000
            //do nothing.
        } else if (fromId == Constant.FROM_ID_START_ACTIVITY) {
            onStartActivity(context, bundle, callback);
        } else if (fromId == 1003) { //Constant.FROM_ID_CLOSE = 1003
            close();
        } else {
            if (callback != null) {
                callback.onCloseLoadingView();
            }
        }
    }

    private void onStartActivity(final Context context, Bundle bundle, final EnterCallback callback) {
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
        
        Log.d(TAG, "PluginManager---->"+pluginZipPath);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "PluginManager---->run"+pluginZipPath);
                    Log.d(TAG, "PluginManager---------------->start install plugin");

                    InstalledPlugin installedPlugin = installedPlugin(pluginZipPath, null, true);
                    Log.d(TAG, "PluginManager---------------->installedPluginInfo:" + "UUID:" + installedPlugin.UUID + " partKey:" + installedPlugin.plugins.keySet());
                    
                    Intent pluginIntent = new Intent();
                    pluginIntent.setClassName(
                            context.getPackageName(),
                            className
                    );
                    
                    if (extras != null) {
                        pluginIntent.replaceExtras(extras);
                    }

                    Log.d(TAG, "PluginManager---------------->start plugin activity");
                    startPluginActivity(installedPlugin, partKey, pluginIntent);
                } catch (Exception e) {
                    Log.e(TAG, "PluginManager---------------->error:" + e.getMessage(), e);
                }
                
                if (callback != null) {
                    callback.onCloseLoadingView();
                }
            }
        });
    }

    public void startPluginActivity(InstalledPlugin installedPlugin, String partKey, Intent pluginIntent) throws RemoteException, FailedException, TimeoutException {
        Log.d(TAG, "PluginManager---------------->startPluginActivity");
        Intent intent = convertActivityIntent(installedPlugin, partKey, pluginIntent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mPluginLoader.startActivityInPluginProcess(intent);
    }

    public Intent convertActivityIntent(InstalledPlugin installedPlugin, String partKey, Intent pluginIntent) throws RemoteException, FailedException, TimeoutException {
        Log.d(TAG, "PluginManager---------------->convertActivityIntent");
        
        loadPlugin(installedPlugin.UUID, partKey);
        
        Map map = mPluginLoader.getLoadedPlugin();
        Boolean isCall = (Boolean) map.get(partKey);
        if (isCall == null || !isCall) {
            Log.d(TAG, "PluginManager---------------->再次确认 calling Application.onCreate");
            mPluginLoader.callApplicationOnCreate(partKey);
        }
        
        if (mPluginLoader == null) {
            Log.e(TAG, "PluginManager---------------->Error: mPluginLoader is null");
            throw new IllegalStateException("mPluginLoader is null");
        }
        
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
        Log.d(TAG, "PluginManager---------------->loadPlugin: uuid=" + uuid + ", partKey=" + partKey);
        
        // Charger d'abord PluginLoader et Runtime
        loadPluginLoaderAndRuntime(uuid, partKey);
        
        Map map = mPluginLoader.getLoadedPlugin();
        if (!map.containsKey(partKey)) {
            Log.d(TAG, "PluginManager---------------->loading plugin: " + partKey);
            mPluginLoader.loadPlugin(partKey);
        } else {
            Log.d(TAG, "PluginManager---------------->plugin already loaded: " + partKey);
        }
        
        Boolean isCall = (Boolean) map.get(partKey);
        if (isCall == null || !isCall) {
            Log.d(TAG, "PluginManager---------------->calling Application.onCreate");
            mPluginLoader.callApplicationOnCreate(partKey);
        }
    }

    private void loadPluginLoaderAndRuntime(String uuid, String partKey) throws RemoteException, TimeoutException, FailedException {
        Log.d(TAG, "PluginManager---------------->loadPluginLoaderAndRuntime");
        if (mPpsController == null) {
            Log.d(TAG, "PluginManager---------------->bind service");
            // Choisir le service de processus de plugin approprié en fonction du partKey
            bindPluginProcessService(getPluginProcessServiceName(partKey));
            waitServiceConnected(10, TimeUnit.SECONDS);
        }
        
        Log.d(TAG, "PluginManager---------------->load runtime");
        loadRunTime(uuid);
        
        Log.d(TAG, "PluginManager---------------->load plugin loader");
        loadPluginLoader(uuid);
    }

    /**
     * Sélectionne le service de processus de plugin en fonction de la clé de partie
     * Référence à l'implémentation officielle de Shadow
     * @param partKey Clé de partie du plugin
     * @return Nom complet de classe du service de processus de plugin correspondant
     */
    protected String getPluginProcessServiceName(String partKey) {
        if ("Empreintes-plugin".equals(partKey)){
            return "descartes.info.l3p2.service.PluginProcessService1";
        }else if ("Arbre-plugin".equals(partKey)){
            return "descartes.info.l3p2.service.PluginProcessService2";
        }else if ("plugin3".equals(partKey)){
            return "descartes.info.l3p2.service.PluginProcessService3";
        }else if ("plugin4".equals(partKey)){
            return "descartes.info.l3p2.service.PluginProcessService4";
        }else if ("plugin5".equals(partKey)){
            return "descartes.info.l3p2.service.PluginProcessService5";
        }else if ("plugin6".equals(partKey)){
            return "descartes.info.l3p2.service.PluginProcessService6";
        }else if ("plugin7".equals(partKey)){
            return "descartes.info.l3p2.service.PluginProcessService7";
        }else{
            return "descartes.info.l3p2.service.MainPluginProcessService";
        }
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
                    oDexPluginLoaderOrRunTime(uuid,InstalledType.TYPE_PLUGIN_RUNTIME,pluginConfig.runTime.file);
                    return null;
                }
            });
            futures.add(odexRunTime);
            Future odexLoader = mFixedPool.submit(new Callable() {
                @Override
                public Object call() throws Exception{
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
        
        Log.d(TAG, "PluginManager---------------->onInstallCompleted(pluginConfig)，pluginConfig.UUID:" + pluginConfig.UUID);
        onInstallCompleted(pluginConfig);
        
        // Obtenir le plugin installé par son UUID
        InstalledPlugin latestPlugin = getInstalledPlugin(pluginConfig.UUID);
        
        return latestPlugin;
    }
}