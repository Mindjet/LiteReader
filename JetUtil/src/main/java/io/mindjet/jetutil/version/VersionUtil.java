package io.mindjet.jetutil.version;

import android.os.Build;

/**
 * Created by Jet on 3/7/17.
 */

public class VersionUtil {

    private static int _version = Build.VERSION.SDK_INT;

    public static boolean afterLollipop() {
        return _version >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean afterAPI(int version) {
        return _version >= version;
    }

    public static boolean afterKitKat() {
        return _version >= Build.VERSION_CODES.KITKAT;
    }

}
