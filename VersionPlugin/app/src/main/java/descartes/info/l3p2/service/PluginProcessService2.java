package descartes.info.l3p2.service;

import com.tencent.shadow.dynamic.host.PluginProcessService;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.util.Log;

import com.tencent.shadow.sample.host.lib.LoadPluginCallback;

/**
 * Service de processus de plugin pour le plugin Arbre.
 * Cette classe gère le chargement du plugin Arbre et la gestion des événements de chargement.
 * 
 * @author Shi Jianye
 */
public class PluginProcessService2 extends MainPluginProcessService {
    public PluginProcessService2() {
        LoadPluginCallback.setCallback(new LoadPluginCallback.Callback() {

            @Override
            public void beforeLoadPlugin(String partKey) {
                Log.d("PluginProcessService2", "beforeLoadPlugin(" + partKey + ")");
            }

            @Override
            public void afterLoadPlugin(String partKey, ApplicationInfo applicationInfo, ClassLoader pluginClassLoader, Resources pluginResources) {
                Log.d("PluginProcessService2", "afterLoadPlugin(" + partKey + "," + applicationInfo.className + "{metaData=" + applicationInfo.metaData + "}" + "," + pluginClassLoader + ")");
            }
        });
    }
} 