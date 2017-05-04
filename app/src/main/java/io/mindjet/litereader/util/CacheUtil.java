package io.mindjet.litereader.util;

import android.content.Context;
import android.os.Environment;

import io.mindjet.jetutil.file.SPUtil;
import io.mindjet.jetutil.logger.JLogger;

/**
 * Cache and data Utility
 * <p>
 * Created by Mindjet on 5/4/17.
 */

public class CacheUtil {

    private static JLogger jLogger = JLogger.get("CacheUtil");

    /**
     * Print basic information of cache and data, including ExternalStorageDirectory, ExternalStorageState, ExternalCacheDir,
     * CacheDir, ExternalFilesDir, FilesDir.
     *
     * @param context context
     */
    public static void printCacheInfo(Context context) {
        jLogger.e("ExternalStorageDirectory: " + Environment.getExternalStorageDirectory().getAbsolutePath());
        jLogger.e("ExternalStorageState: " + Environment.getExternalStorageState());
        try {
            jLogger.e("ExternalCacheDir: " + context.getExternalCacheDir().getAbsolutePath());
        } catch (NullPointerException ignored) {

        }
        jLogger.e("CacheDir: " + context.getCacheDir().getAbsolutePath());
        jLogger.e("ExternalFilesDir: " + context.getExternalFilesDir(null));
        jLogger.e("FilesDir: " + context.getFilesDir());
    }

    /**
     * Clean up the {@link android.content.SharedPreferences}.
     *
     * @param context context
     */
    public static void clearSharedPreferences(Context context) {
        SPUtil.clear(context);
    }

}
