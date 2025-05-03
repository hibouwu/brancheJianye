package descartes.info.l3p2.base;

import static android.os.Process.myPid;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.webkit.WebView;

import com.tencent.shadow.core.common.LoggerFactory;
import com.tencent.shadow.dynamic.host.DynamicPluginManager;
import com.tencent.shadow.dynamic.host.DynamicRuntime;
import com.tencent.shadow.dynamic.host.PluginManager;
import java.io.File;
import java.util.concurrent.Future;
import java.util.concurrent.ConcurrentHashMap;

import descartes.info.l3p2.FixedPathPmUpdater;
import descartes.info.l3p2.utils.AndroidLoggerFactory;
import descartes.info.l3p2.DownloadManager.DownloadManagerForPluginManager;

/**
 * Application principale qui gère les instances de PluginManager.
 * Cette classe est responsable de l'initialisation du framework Shadow,
 * de la gestion de plusieurs instances de PluginManager pour différents plugins,
 * et de la fourniture d'accès à ces instances.
 * 
 * @author Shi Jianye
 */
public class MyApplication extends Application {
    
    // Instance singleton
    private static MyApplication sInstance;
    
    // Map stockant plusieurs instances de PluginManager, utilisant partKey comme clé
    private final ConcurrentHashMap<String, PluginManager> mPluginManagerMap = new ConcurrentHashMap<>();
    
    private String pluginManagerApkFilePath;
    private static final String TAG = "MyApplication";

    /**
     * Obtenir l'instance singleton de l'Application
     */
    public static MyApplication getInstance() {
        return sInstance;
    }
    
    /**
     * Obtenir le PluginManager correspondant à la clé de partie du plugin
     * @param partKey Clé de partie du plugin, cohérente avec celle du framework Shadow
     * @return L'instance PluginManager correspondante
     */
    public PluginManager getPluginManagerByPartKey(String partKey) {
        // Si la Map ne contient pas de PluginManager correspondant, en créer un
        if (!mPluginManagerMap.containsKey(partKey)) {
            synchronized (mPluginManagerMap) {
                if (!mPluginManagerMap.containsKey(partKey)) {
                    PluginManager pluginManager = createPluginManager(partKey);
                    mPluginManagerMap.put(partKey, pluginManager);
                    return mPluginManagerMap.get(partKey);
                }
            }
        }
        return mPluginManagerMap.get(partKey);
    }
    
    /**
     * Créer une nouvelle instance de PluginManager
     * @param partKey Clé de partie du plugin
     * @return La nouvelle instance de PluginManager créée
     */
    private PluginManager createPluginManager(String partKey) {
        // Créer et initialiser un PluginManager
        Log.i(TAG, "Création d'une nouvelle instance de PluginManager: " + partKey);
        FixedPathPmUpdater fixedPathPmUpdater = new FixedPathPmUpdater(new File(pluginManagerApkFilePath));
        try {
            if (fixedPathPmUpdater.wasUpdating() || fixedPathPmUpdater.getLatest() == null) {
                fixedPathPmUpdater.update().get();
            }
        } catch (Exception e) {
            Log.e(TAG, "Échec de création du PluginManager: " + partKey, e);
            throw new RuntimeException("Échec de création du PluginManager: " + partKey, e);
        }
        
        return new DynamicPluginManager(fixedPathPmUpdater);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialiser le singleton
        sInstance = this;

        // Initialiser le chemin du fichier
        pluginManagerApkFilePath = getFilesDir().getAbsolutePath() + "/pluginmanager/pluginmanager-debug.apk";

        // Vérifier et télécharger le PluginManager
        DownloadManagerForPluginManager downloadManager = new DownloadManagerForPluginManager(this);
        boolean success = downloadManager.checkAndDownloadPluginManager();
        
        if (success) {
            Log.i(TAG, "Vérification et téléchargement du PluginManager réussis");
        } else {
            Log.e(TAG, "Échec de vérification ou téléchargement du PluginManager");
        }

        setWebViewDataDirectorySuffix();

        // Configurer la fabrique de journaux
        LoggerFactory.setILoggerFactory(new AndroidLoggerFactory());

        // Vérifier si nous sommes dans un processus de plugin
        if (isProcess(this, ":plugin")) {
            DynamicRuntime.recoveryRuntime(this);
        }
    }

    private static void setWebViewDataDirectorySuffix(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            return;
        }
        WebView.setDataDirectorySuffix(Application.getProcessName());
    }
    
    private static boolean isProcess(Context context, String processName){
        String currentProcName = "";
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE) ;
        for(ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()){
            if(processInfo.pid == myPid()){
                currentProcName = processInfo.processName;
                break;
            }
        }
        return currentProcName.endsWith(processName);
    }
}
