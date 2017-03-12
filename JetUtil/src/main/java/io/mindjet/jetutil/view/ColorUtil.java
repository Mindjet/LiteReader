package io.mindjet.jetutil.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;

import io.mindjet.jetutil.logger.JLogger;

/**
 * Created by Jet on 3/2/17.
 */

public class ColorUtil extends View {

    private static JLogger jLogger = JLogger.get("ColorUtil");

    public ColorUtil(Context context) {
        super(context);
    }

    public static ColorStateList createColorStateList(int normalColor, int selectedColor) {
        final int[][] states = new int[2][];
        final int[] colors = new int[2];

        states[0] = View.SELECTED_STATE_SET;
        colors[0] = selectedColor;

        // Default enabled state
        states[1] = EMPTY_STATE_SET;
        colors[1] = normalColor;

        return new ColorStateList(states, colors);
    }

}
