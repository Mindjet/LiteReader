package io.mindjet.litereader;

import android.content.Context;
import android.os.Build;

import io.mindjet.jetpack.BuildConfig;
import io.mindjet.jetutil.logger.JLogger;


/**
 * Created by Jet on 2/17/17.
 */

public class BaseEnv {

    private static JLogger jLogger = JLogger.get(BaseEnv.class.getSimpleName());

    public static void init(Context context) {
        jLogger.i("************************************");
        jLogger.i("APPLICATION ID: " + BuildConfig.APPLICATION_ID);
        jLogger.i("BUILD TYPE: " + BuildConfig.BUILD_TYPE);
        jLogger.i("PRODUCT FLAVOR: " + BuildConfig.FLAVOR);
        jLogger.i("SYSTEM API: API " + Build.VERSION.SDK_INT);
        jLogger.i("VERSION CODE: " + BuildConfig.VERSION_CODE);
        jLogger.i("VERSION NAME: " + BuildConfig.VERSION_NAME);
        jLogger.i("BASE URL: " + BuildConfig.BASE_URL);
        jLogger.i("SCREEN SIZE: " + context.getResources().getDisplayMetrics().heightPixels + "x" + context.getResources().getDisplayMetrics().widthPixels);
        jLogger.i("************************************");
    }

}
