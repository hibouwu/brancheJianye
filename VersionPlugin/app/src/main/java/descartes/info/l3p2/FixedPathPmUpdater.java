package descartes.info.l3p2;

import com.tencent.shadow.dynamic.host.PluginManagerUpdater;

import java.io.File;
import java.util.concurrent.Future;

public class FixedPathPmUpdater implements PluginManagerUpdater {
    private final File apk;
    public FixedPathPmUpdater(File apk){this.apk = apk;}
    @Override
    public boolean wasUpdating() {
        return false;
    }

    @Override
    public Future<File> update() {
        return null;
    }

    @Override
    public File getLatest() {
        return apk;
    }

    @Override
    public Future<Boolean> isAvailable(File file) {
        return null;
    }
}
