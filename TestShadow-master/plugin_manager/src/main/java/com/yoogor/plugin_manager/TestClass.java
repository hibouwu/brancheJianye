package com.yoogor.plugin_manager;

import android.content.Context;
import android.widget.Toast;

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
