package descartes.info.l3p2.eyetrek.reconnaissanceArbres;

import android.app.Application;
import android.util.Log;

/**
 * 插件 Application，必须存在并确保初始化完成
 */
public class PluginApplication extends Application {
    private static final String TAG = "PluginApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "PluginApplication.onCreate");
    }
}
