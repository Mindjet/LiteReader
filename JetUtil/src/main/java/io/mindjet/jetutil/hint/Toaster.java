package io.mindjet.jetutil.hint;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Toast helper.
 * <p>
 * Created by Mindjet on 2/9/17.
 */

public class Toaster {

    private static Toast toast;

    public static void toast(Context context, @StringRes int stringRes) {
        toast(context, context.getResources().getString(stringRes), Toast.LENGTH_SHORT);
    }

    public static void toast(Context context, @StringRes int stringRes, int duration) {
        toast(context, context.getResources().getString(stringRes), duration);
    }

    public static void toast(Context context, String message) {
        toast(context, message, Toast.LENGTH_SHORT);
    }

    public static void toast(Context context, String message, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, message, duration);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

}
