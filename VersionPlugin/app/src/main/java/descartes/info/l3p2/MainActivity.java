package descartes.info.l3p2;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import descartes.info.l3p2.MainActivity;
import descartes.info.l3p2.base.MyApplication;

import com.tencent.shadow.dynamic.host.EnterCallback;
import com.tencent.shadow.dynamic.host.PluginManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startPlugin(View view) {
        Bundle bundle = new Bundle();

        PluginManager pluginManager = MyApplication.getPluginManager();

        //transform for PluginManager
        String pluginPath = getFilesDir().getAbsolutePath() + "/plugins/plugin-debug.zip";
        bundle.putString("plugin_path_from_host",pluginPath);
        bundle.putString("part_key","sample-plugin");
        bundle.putString("activity_class_name","descartes.info.l3p2.eyetrek.reconnaissanceArbres.activity.Activity" );
        //bundle.putString("part_key","reconnaisseArbre");

        //transsform for Plugin
        Bundle bundleForPlugin = new Bundle();
        bundleForPlugin.putString("name","Shi");
        bundle.putBundle("extra_to_plugin_bundle", bundleForPlugin);

        pluginManager.enter(MainActivity.this, 1, bundle, new EnterCallback() {
            @Override
            public void onShowLoadingView(View view) {
                //appel in PluginManager.MyPluginManager.enter
            }

            @Override
            public void onCloseLoadingView() {
                //load finish and close this dialog
            }

            @Override
            public void onEnterComplete() {
                //enter sucessful plugin
            }
        });
    }
}