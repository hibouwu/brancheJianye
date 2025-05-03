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
 * Gestionnaire de téléchargement pour les plugins.
 * Cette classe gère le téléchargement des plugins depuis des URL distantes
 * et les enregistre dans le répertoire des plugins de l'application.
 * 
 * @author Shi Jianye
 */
public class DownloadManagerForPlugin {
    private static final String TAG = "DownloadManagerForPlugin";
    private static final String ARBRE_PLUGIN_URL = "https://github.com/hibouwu/brancheJianye/releases/download/1.0.0/Arbre-plugin-debug.zip";
    private static final String EMPREINTES_PLUGIN_URL = "https://github.com/hibouwu/brancheJianye/releases/download/1.0.0/Empreintes-plugin-debug.zip";
    private static final String PLUGINS_DIR = "plugins";
    private static final String ARBRE_PLUGIN_FILENAME = "Arbre-plugin-debug.zip";
    private static final String EMPREINTES_PLUGIN_FILENAME = "Empreintes-plugin-debug.zip";
    private Context context;
    private OkHttpClient client;
    private String arbrePluginFilePath;
    private String empreintesPluginFilePath;
    public DownloadManagerForPlugin(Context context) {
        this.context = context;
        this.client = new OkHttpClient.Builder()
                .build();
        this.arbrePluginFilePath = context.getFilesDir().getAbsolutePath() + "/" + PLUGINS_DIR + "/" + ARBRE_PLUGIN_FILENAME;
        this.empreintesPluginFilePath = context.getFilesDir().getAbsolutePath() + "/" + PLUGINS_DIR + "/" + EMPREINTES_PLUGIN_FILENAME;
    }

    public boolean checkAndDownloadArbrePlugin() {
        File pluginFile = new File(arbrePluginFilePath);
        File pluginDir = pluginFile.getParentFile();

        if (!pluginDir.exists()) {
            if (!pluginDir.mkdirs()) {
                Log.e(TAG, "Failed to create plugin directory");
                return false;
            }
        }

        if (!pluginFile.exists()) {
            return downloadPlugin(pluginFile, ARBRE_PLUGIN_URL);
        }

        if (pluginFile.exists() && pluginFile.length() > 0) {
            Log.d(TAG, "Arbre plugin file exists: " + pluginFile.getAbsolutePath() + ", size: " + pluginFile.length());
            return true;
        } else {
            Log.e(TAG, "Arbre plugin file exists but is invalid, redownloading...");
            return downloadPlugin(pluginFile, ARBRE_PLUGIN_URL);
        }
    }

    public boolean checkAndDownloadEmpreintesPlugin() {
        File pluginFile = new File(empreintesPluginFilePath);
        File pluginDir = pluginFile.getParentFile();

        if (!pluginDir.exists()) {
            if (!pluginDir.mkdirs()) {
                Log.e(TAG, "Failed to create plugin directory");
                return false;
            }
        }

        if (!pluginFile.exists()) {
            return downloadPlugin(pluginFile, EMPREINTES_PLUGIN_URL);
        }

        if (pluginFile.exists() && pluginFile.length() > 0) {
            Log.d(TAG, "Empreintes plugin file exists: " + pluginFile.getAbsolutePath() + ", size: " + pluginFile.length());
            return true;
        } else {
            Log.e(TAG, "Empreintes plugin file exists but is invalid, redownloading...");
            return downloadPlugin(pluginFile, EMPREINTES_PLUGIN_URL);
        }
    }

    private boolean downloadPlugin(File targetFile, String url) {
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean success = new AtomicBoolean(false);

        Thread downloadThread = new Thread(() -> {
            try {
                if (targetFile.exists()) {
                    targetFile.delete();
                }
                
                Log.d(TAG, "Downloading plugin from URL: " + url);
                Log.d(TAG, "Saving plugin to: " + targetFile.getAbsolutePath());
                
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Failed to download plugin: " + response.code());
                    return;
                }

                try (InputStream inputStream = response.body().byteStream();
                     FileOutputStream outputStream = new FileOutputStream(targetFile)) {
                    
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    long totalBytesRead = 0;
                    
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                        totalBytesRead += bytesRead;
                    }
                    
                    Log.d(TAG, "Download completed. Total bytes: " + totalBytesRead);
                    success.set(true);
                } catch (IOException e) {
                    Log.e(TAG, "Error writing plugin file", e);
                }
            } catch (IOException e) {
                Log.e(TAG, "Error downloading plugin", e);
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

        if (success.get()) {
            Log.d(TAG, "Plugin file downloaded successfully. Size: " + targetFile.length());
        } else {
            Log.e(TAG, "Plugin download failed or file is invalid");
        }

        return success.get();
    }

    public String getArbrePluginPath() {
        File pluginFile = new File(arbrePluginFilePath);
        if (!pluginFile.exists() || pluginFile.length() == 0) {
            Log.e(TAG, "getArbrePluginPath: Plugin file does not exist or is empty: " + arbrePluginFilePath);
        } else {
            Log.d(TAG, "getArbrePluginPath: Using plugin file: " + arbrePluginFilePath + ", size: " + pluginFile.length());
        }
        return arbrePluginFilePath;
    }

    public String getEmpreintesPluginPath() {
        File pluginFile = new File(empreintesPluginFilePath);
        if (!pluginFile.exists() || pluginFile.length() == 0) {
            Log.e(TAG, "getEmpreintesPluginPath: Plugin file does not exist or is empty: " + empreintesPluginFilePath);
        } else {
            Log.d(TAG, "getEmpreintesPluginPath: Using plugin file: " + empreintesPluginFilePath + ", size: " + pluginFile.length());
        }
        return empreintesPluginFilePath;
    }
}