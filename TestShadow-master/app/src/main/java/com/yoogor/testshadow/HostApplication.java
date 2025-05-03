package com.yoogor.testshadow;

import android.app.Application;

import com.tencent.shadow.core.common.LoggerFactory;
import com.tencent.shadow.dynamic.host.DynamicRuntime;
import com.tencent.shadow.dynamic.host.PluginManager;
import com.yoogor.testshadow.shadow.PluginHelper;
import com.yoogor.testshadow.shadow.Shadow;

import java.io.File;

/**
 * @author caipengrui 2019/7/18
 * Package com.yoogor.testshadow
 * Title:  HostApplication
 * Description: (用一句话描述该文件做什么)
 * Copyright (c) 2019 优菓科技有限公司 All rights reserved.
 * Create DateTime: 2019/7/18.
 */
public class HostApplication extends Application {
    private static HostApplication sApp;

    private PluginManager mPluginManager;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        LoggerFactory.setILoggerFactory(new AndroidLogLoggerFactory());
        DynamicRuntime.recoveryRuntime(this);
        PluginHelper.getInstance().init(this);
    }

    public static HostApplication getApp() {
        return sApp;
    }

    public void loadPluginManager(File apk) {
        if (mPluginManager == null) {
            mPluginManager = Shadow.getPluginManager(apk);
        }
    }

    public PluginManager getPluginManager() {
        return mPluginManager;
    }
}
