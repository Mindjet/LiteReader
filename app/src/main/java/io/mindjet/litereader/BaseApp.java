package io.mindjet.litereader;

import android.app.Application;
import android.os.Environment;

import java.io.File;

import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.jetimage.picker.ImagePicker;
import io.mindjet.litereader.BuildConfig;

/**
 * Created by Jet on 2/8/17.
 */

public class BaseApp extends Application {

    private static BaseApp baseApp;
    private File appDir;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApp = this;
        BaseEnv.init(this);
        ServiceGen.init(BuildConfig.BASE_URL, BuildConfig.BUILD_TYPE);
        makeAppDir();
        ImagePicker.setImagePath(Environment.getExternalStorageDirectory() + "/" + BuildConfig.DATA_PATH);
    }

    /**
     * Make a directory for this application.
     */
    private void makeAppDir() {
        appDir = new File(Environment.getExternalStorageDirectory() + "/" + BuildConfig.DATA_PATH);
        if (!appDir.exists())
            appDir.mkdir();
    }

    public static BaseApp me() {
        return baseApp;
    }

}
