package com.yoogor.plugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(MainActivity.this, "com.yoogor.testshadow.MainActivity");
                startActivity(intent);
            }
        });
        findViewById(R.id.text1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Class _class = Class.forName("com.yoogor.plugin_manager.TestClass");
                    Object testClass = _class.newInstance();
                    Method method = _class.getDeclaredMethod("test", Context.class, String.class);
                    method.invoke(testClass, MainActivity.this, "hjello");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.text2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("KEY_PLUGIN_PART_KEY", "plugin1");
                intent.putExtra("KEY_ACTIVITY_CLASSNAME", "com.yoogor.plugin1.MainActivity");
                intent.setClassName(MainActivity.this, "com.yoogor.testshadow.PluginLoadActivity");
                startActivity(intent);
            }
        });
    }
}
