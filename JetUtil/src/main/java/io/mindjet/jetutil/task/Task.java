package io.mindjet.jetutil.task;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by Jet on 2/17/17.
 */

public class Task {

    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void runOnUiThread(Runnable r) {
        handler.post(r);
    }

    public static void runOnUiThread(Runnable r, long time) {
        handler.postDelayed(r, time);
    }

}
