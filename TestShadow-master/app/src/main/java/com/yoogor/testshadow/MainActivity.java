package com.yoogor.testshadow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tencent.shadow.dynamic.host.EnterCallback;
import com.yoogor.testshadow.shadow.PluginHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = new Bundle();
        bundle.putString("KEY_PLUGIN_PART_KEY", "plugin");
        bundle.putString("KEY_ACTIVITY_CLASSNAME", "com.yoogor.plugin.MainActivity");
        Intent intent = new Intent(this, PluginLoadActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
