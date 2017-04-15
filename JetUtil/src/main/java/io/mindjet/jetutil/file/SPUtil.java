package io.mindjet.jetutil.file;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * SharedPreference Utility
 * <p>
 * Created by Mindjet on 2017/4/11.
 */

public class SPUtil {

    /**
     * Get SharedPreference
     *
     * @param context context
     */
    private static SharedPreferences getSP(Context context) {
        return context.getSharedPreferences("_preferences", Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getSP(context).edit();
    }

    /**
     * Save a String
     *
     * @param context context
     * @param key     key
     * @param value   value
     */
    public static void save(Context context, String key, String value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Save a String set
     *
     * @param context  context
     * @param key      key
     * @param valueSet valueSet
     */
    public static void save(Context context, String key, Set<String> valueSet) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putStringSet(key, valueSet);
        editor.commit();
    }

    /**
     * Get a String
     *
     * @param context context
     * @param key     key
     * @return the corresponding value, null if not exists.
     */
    public static String getString(Context context, String key) {
        SharedPreferences sp = getSP(context);
        return sp.getString(key, null);
    }

    /**
     * Get a String set
     *
     * @param context context
     * @param key     key
     * @return the corresponding value set, null if not exists.
     */
    public static Set<String> getStringSet(Context context, String key) {
        SharedPreferences sp = getSP(context);
        return sp.getStringSet(key, null);
    }

}
