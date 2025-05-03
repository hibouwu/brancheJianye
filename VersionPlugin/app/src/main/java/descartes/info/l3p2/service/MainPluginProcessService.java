package descartes.info.l3p2.service;

import com.tencent.shadow.dynamic.host.PluginProcessService;

import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.util.Log;

import com.tencent.shadow.sample.host.lib.LoadPluginCallback;

/**
 * Service principal pour le processus de plugin.
 * Cette classe gère le chargement des plugins et la gestion des événements de chargement.
 * 
 * @author Shi Jianye
 */
public class MainPluginProcessService extends PluginProcessService {
    public MainPluginProcessService() {
        LoadPluginCallback.setCallback(new LoadPluginCallback.Callback() {

            @Override
            public void beforeLoadPlugin(String partKey) {
                Log.d("MainPluginProcessService", "beforeLoadPlugin(" + partKey + ")");
            }

            @Override
            public void afterLoadPlugin(String partKey, ApplicationInfo applicationInfo, ClassLoader pluginClassLoader, Resources pluginResources) {
                Log.d("MainPluginProcessService", "afterLoadPlugin(" + partKey + "," + applicationInfo.className + "{metaData=" + applicationInfo.metaData + "}" + "," + pluginClassLoader + ")");
            }
        });
    }
}
