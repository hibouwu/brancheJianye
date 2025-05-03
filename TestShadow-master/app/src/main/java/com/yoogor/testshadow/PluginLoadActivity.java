package com.yoogor.testshadow;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tencent.shadow.dynamic.host.EnterCallback;
import com.yoogor.testshadow.shadow.PluginHelper;

import androidx.annotation.Nullable;

/**
 * @author caipengrui 2019/7/19
 * Package com.yoogor.testshadow
 * Title:  PluginLoadActivity
 * Description: (用一句话描述该文件做什么)
 * Copyright (c) 2019 优菓科技有限公司 All rights reserved.
 * Create DateTime: 2019/7/19.
 */
public class PluginLoadActivity extends Activity {
    private ViewGroup mViewGroup;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewGroup = new RelativeLayout(this);
        setContentView(mViewGroup);
        startPlugin();
    }

    public void startPlugin() {

        PluginHelper.getInstance().singlePool.execute(new Runnable() {
            @Override
            public void run() {
                HostApplication.getApp().loadPluginManager(PluginHelper.getInstance().pluginManagerFile);

                Bundle bundle = new Bundle();
                bundle.putString("pluginZipPath", PluginHelper.getInstance().pluginZipFile.getAbsolutePath());
                bundle.putString("KEY_PLUGIN_PART_KEY", getIntent().getStringExtra("KEY_PLUGIN_PART_KEY"));
                bundle.putString("KEY_ACTIVITY_CLASSNAME", getIntent().getStringExtra("KEY_ACTIVITY_CLASSNAME"));

                HostApplication.getApp().getPluginManager()
                        .enter(PluginLoadActivity.this, 0, bundle, new EnterCallback() {
                            @Override
                            public void onShowLoadingView(final View view) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mViewGroup.addView(view);
                                    }
                                });
                            }

                            @Override
                            public void onCloseLoadingView() {
                                finish();
                            }

                            @Override
                            public void onEnterComplete() {

                            }
                        });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewGroup.removeAllViews();
    }
}
