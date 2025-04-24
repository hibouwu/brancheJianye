package descartes.info.l3p2.base;

import static android.os.Process.myPid;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.tencent.shadow.core.common.LoggerFactory;
import com.tencent.shadow.dynamic.host.DynamicPluginManager;
import com.tencent.shadow.dynamic.host.DynamicRuntime;
import com.tencent.shadow.dynamic.host.PluginManager;

import java.io.File;
import java.util.concurrent.Future;

import descartes.info.l3p2.FixedPathPmUpdater;
import descartes.info.l3p2.utils.AndroidLoggerFactory;

public class MyApplication extends Application {
    private static PluginManager sPluginManager;
    public static String PlUGIN_MANAGER_APK_FILE_PATH = "/data/local/tmp/pluginmanager-debug.apk";

    public static PluginManager getPluginManager() {
        return sPluginManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LoggerFactory.setILoggerFactory(new AndroidLoggerFactory());
        if (isProcess(this, ":plugin")) {
            DynamicRuntime.recoveryRuntime(this);
        } else {
            //PluginManage
            FixedPathPmUpdater fixedPathPmUpdater = new FixedPathPmUpdater(new File(PlUGIN_MANAGER_APK_FILE_PATH));

            boolean needWaitingUpdate = fixedPathPmUpdater.wasUpdating()
                    || fixedPathPmUpdater.getLatest() == null;
            Future<File> update = fixedPathPmUpdater.update();
            if(needWaitingUpdate){
                try {
                    update.get();
                }catch(Exception e){
                    throw new RuntimeException("Sample program test exception",e);
                }

            }

            sPluginManager = new DynamicPluginManager(fixedPathPmUpdater);
        }
    }

    private static boolean isProcess(Context context, String processName){
        String currentProcName = "";
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE) ;
        for(ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()){
            if(processInfo.pid == myPid()){
                currentProcName = processInfo.processName;
                break;
            }
        }
        return currentProcName.endsWith(processName);
    }
}
