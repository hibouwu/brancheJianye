package descartes.info.l3p2.DownloadManager;

import android.content.Context;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
/**
 * Gestionnaire de téléchargement pour le plugin manager.
 * Cette classe gère le téléchargement du plugin manager depuis une URL distante
 * et l'enregistre dans le répertoire des plugins de l'application.
 * 
 * @author Shi Jianye
 */
public class DownloadManagerForPluginManager {
    private static final String TAG = "DownloadManagerForPluginManager";
    private static final String PLUGIN_MANAGER_URL = "https://github.com/hibouwu/brancheJianye/releases/download/1.0.0/pluginmanager-debug.apk";
    private static final String PLUGIN_MANAGER_DIR = "pluginmanager";
    private static final String PLUGIN_MANAGER_FILENAME = "pluginmanager-debug.apk";

    private Context context;
    private OkHttpClient client;
    private String pluginManagerApkFilePath;

    public DownloadManagerForPluginManager(Context context) {
        this.context = context;
        this.client = new OkHttpClient.Builder()
                .build();
        this.pluginManagerApkFilePath = context.getFilesDir().getAbsolutePath() + "/" + PLUGIN_MANAGER_DIR + "/" + PLUGIN_MANAGER_FILENAME;
    }

    public boolean checkAndDownloadPluginManager() {
        File pluginManagerFile = new File(pluginManagerApkFilePath);
        File pluginManagerDir = pluginManagerFile.getParentFile();

        if (!pluginManagerDir.exists()) {
            if (!pluginManagerDir.mkdirs()) {
                Log.e(TAG, "Failed to create plugin manager directory");
                return false;
            }
        }

        if (!pluginManagerFile.exists()) {
            return downloadPluginManager(pluginManagerFile);
        }

        return true;
    }

    private boolean downloadPluginManager(File targetFile) {
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean success = new AtomicBoolean(false);

        Thread downloadThread = new Thread(() -> {
            try {
                Request request = new Request.Builder()
                        .url(PLUGIN_MANAGER_URL)
                        .build();

                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Failed to download plugin manager: " + response.code());
                    return;
                }

                try (InputStream inputStream = response.body().byteStream();
                     FileOutputStream outputStream = new FileOutputStream(targetFile)) {
                    
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    
                    success.set(true);
                } catch (IOException e) {
                    Log.e(TAG, "Error writing plugin manager file", e);
                }
            } catch (IOException e) {
                Log.e(TAG, "Error downloading plugin manager", e);
            } finally {
                latch.countDown();
            }
        });

        downloadThread.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            Log.e(TAG, "Download interrupted", e);
            return false;
        }

        return success.get();
    }
}
