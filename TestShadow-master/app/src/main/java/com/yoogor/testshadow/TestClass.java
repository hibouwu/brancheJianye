package com.yoogor.testshadow;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tencent.shadow.dynamic.host.EnterCallback;
import com.yoogor.testshadow.shadow.PluginHelper;

/**
 * @author caipengrui 2019/7/18
 * Package com.yoogor.testshadow
 * Title:  TestClass
 * Description: (用一句话描述该文件做什么)
 * Copyright (c) 2019 优菓科技有限公司 All rights reserved.
 * Create DateTime: 2019/7/18.
 */
public class TestClass {
    public void test(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
