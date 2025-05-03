package descartes.info.l3p2.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tencent.shadow.dynamic.host.EnterCallback;
import com.tencent.shadow.dynamic.host.PluginManager;

import descartes.info.l3p2.base.MyApplication;
import descartes.info.l3p2.DownloadManager.DownloadManagerForPlugin;

/**
 * Chargeur de modules de plugins, responsable du processus unifié de chargement des plugins.
 * Cette classe fournit des méthodes pour charger différents plugins comme Arbre et Empreintes,
 * en gérant le téléchargement des plugins et leur initialisation.
 * 
 * @author Shi Jianye
 */
public class ModuleLoader {
    private static final String TAG = "ModuleLoader";
    
    // Constantes liées aux plugins
    public static final String KEY_PLUGIN_PATH = "plugin_path_from_host";
    public static final String KEY_PART_KEY = "part_key";
    public static final String KEY_ACTIVITY_CLASS = "activity_class_name";
    public static final String KEY_EXTRA_BUNDLE = "extra_to_plugin_bundle";
    
    // Constantes FromId
    public static final int FROM_ID_START_ACTIVITY = 1002;
    
    /**
     * Charger le plugin Arbre
     * @param context Contexte
     * @param callback Callback
     */
    public static void loadArbrePlugin(Context context, EnterCallback callback) {
        DownloadManagerForPlugin downloadManager = new DownloadManagerForPlugin(context);
        if (!downloadManager.checkAndDownloadArbrePlugin()) {
            Log.e(TAG, "Échec de vérification ou téléchargement du plugin Arbre");
            Toast.makeText(context, "Échec de téléchargement du plugin Arbre", Toast.LENGTH_SHORT).show();
            return;
        }

        Bundle bundle = new Bundle();
        String partKey = "Arbre-plugin";
        
        // Obtenir l'instance de PluginManager correspondant au partKey
        PluginManager pluginManager = MyApplication.getInstance().getPluginManagerByPartKey(partKey);

        // Configurer le chemin du plugin
        String pluginPath = downloadManager.getArbrePluginPath();
        bundle.putString(KEY_PLUGIN_PATH, pluginPath);
        bundle.putString(KEY_PART_KEY, partKey);
        bundle.putString(KEY_ACTIVITY_CLASS, "descartes.info.l3p2.eyetrek.reconnaissanceArbres.activity.Activity");

        // Paramètres à transmettre au plugin
        Bundle bundleForPlugin = new Bundle();
        bundleForPlugin.putString("name", "Host");
        bundle.putBundle(KEY_EXTRA_BUNDLE, bundleForPlugin);

        pluginManager.enter(context, FROM_ID_START_ACTIVITY, bundle, callback);
    }
    
    /**
     * Charger le plugin Empreintes
     * @param context Contexte
     * @param callback Callback
     */
    public static void loadEmpreintesPlugin(Context context, EnterCallback callback) {
        DownloadManagerForPlugin downloadManager = new DownloadManagerForPlugin(context);
        if (!downloadManager.checkAndDownloadEmpreintesPlugin()) {
            Log.e(TAG, "Échec de vérification ou téléchargement du plugin Empreintes");
            Toast.makeText(context, "Échec de téléchargement du plugin Empreintes", Toast.LENGTH_SHORT).show();
            return;
        }

        Bundle bundle = new Bundle();
        String partKey = "Empreintes-plugin";
        
        // Obtenir l'instance de PluginManager correspondant au partKey
        PluginManager pluginManager = MyApplication.getInstance().getPluginManagerByPartKey(partKey);

        // Configurer le chemin du plugin
        String pluginPath = downloadManager.getEmpreintesPluginPath();
        bundle.putString(KEY_PLUGIN_PATH, pluginPath);
        bundle.putString(KEY_PART_KEY, partKey);
        bundle.putString(KEY_ACTIVITY_CLASS, "descartes.info.l3p2.eyetrek.reconnaissanceEmpreintes.activity.Activity");

        // Paramètres à transmettre au plugin
        Bundle bundleForPlugin = new Bundle();
        bundleForPlugin.putString("name", "Host");
        bundle.putBundle(KEY_EXTRA_BUNDLE, bundleForPlugin);

        pluginManager.enter(context, FROM_ID_START_ACTIVITY, bundle, callback);
    }
} 