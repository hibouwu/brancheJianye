package com.tencent.shadow.dynamic.impl;

import android.content.Context;

import com.tencent.shadow.dynamic.host.ManagerFactory;
import com.tencent.shadow.dynamic.host.PluginManagerImpl;

import descartes.info.pluginmanager.MyPluginManager;
/**
 * Implémentation de la classe ManagerFactory.
 * Cette classe gère la création d'instances de PluginManager.
 * 
 * @author Shi Jianye
 */
public class ManagerFactoryImpl implements ManagerFactory {
    @Override
    public PluginManagerImpl buildManager(Context context) {

        return new MyPluginManager(context);
    }
}