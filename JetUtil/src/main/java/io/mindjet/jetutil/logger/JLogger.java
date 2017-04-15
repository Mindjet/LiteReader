package io.mindjet.jetutil.logger;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jet on 2/16/17.
 */

public class JLogger {

    private final String NULL = "null";
    private String TAG;
    private int JSON_INDENT = 4;
    private int LOG_LENGTH = 2048;

    private JLogger(String TAG) {
        this.TAG = TAG;
    }

    public static JLogger get(String TAG) {
        return new JLogger(TAG);
    }

    private String tag() {
        Throwable t = new Throwable();
        String method = t.getStackTrace()[2].getMethodName();
        return TAG + "#" + method;
    }

    public void e(Object o) {
        if (o == null) {
            Log.e(tag(), NULL);
        } else {
            for (String s : toSections(prettyFormat(o.toString()))) {
                Log.e(tag(), s);
            }
        }
    }

    public void i(Object o) {
        if (o == null) {
            Log.e(tag(), NULL);
        } else {
            for (String s : toSections(prettyFormat(o.toString()))) {
                Log.i(tag(), s);
            }
        }
    }

    public void w(Object o) {
        if (o == null) {
            Log.e(tag(), NULL);
        } else {
            for (String s : toSections(prettyFormat(o.toString()))) {
                Log.w(tag(), s);
            }
        }
    }

    /**
     * Affected by the log-max-length-mechanism, to avoid losing log information, content out of length needs to be cut into sections.
     *
     * @param content content.
     * @return list of sections from the origin content.
     */
    private List<String> toSections(String content) {
        List<String> sections = new ArrayList<>();
        if (content.length() > LOG_LENGTH) {
            for (int i = 0; i < content.length(); i += LOG_LENGTH) {
                if (i + LOG_LENGTH < content.length())
                    sections.add(content.substring(i, i + LOG_LENGTH));
                else
                    sections.add(content.substring(i, content.length()));
            }
        } else {
            sections.add(content);
        }
        return sections;
    }

    /**
     * If a string a JsonObject or JsonArray, make it pretty. Otherwise, return itself.
     *
     * @param json the target string
     * @return the origin string if the input is neither JsonObject nor JsonArray, the pretty json if it is one of them.
     */
    private String prettyFormat(String json) {
        String output;
        try {
            if (json.startsWith("{\"") && json.endsWith("}")) {
                return "JsonObject: \n" + new JSONObject(json).toString(JSON_INDENT);
            } else if (json.startsWith("[{\"") && json.endsWith("}]")) {
                return "JsonArray: \n" + new JSONArray(json).toString(JSON_INDENT);
            } else {
                output = json;
            }
        } catch (JSONException e) {
            output = json;
        }
        return output;
    }

}
