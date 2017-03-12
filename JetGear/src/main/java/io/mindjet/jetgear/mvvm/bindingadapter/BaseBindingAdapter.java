package io.mindjet.jetgear.mvvm.bindingadapter;

import android.animation.LayoutTransition;
import android.databinding.BindingAdapter;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.mindjet.jetgear.R;
import io.mindjet.jetutil.logger.JLogger;

/**
 * Created by Jet on 2/20/17.
 */

public class BaseBindingAdapter {

    private static JLogger jLogger = JLogger.get(BaseBindingAdapter.class.getSimpleName());

    @BindingAdapter("app:layout_height")
    public static void height(View view, int height) {      //height=0 => MATCH_PARENT, height=-1 => WRAP_CONTENT
        if (height == -1) {
            view.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else if (height == 0) {
            view.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        } else if (height < -1) {
            jLogger.w("invalid height");
            view.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            view.getLayoutParams().height = height;
        }
    }

    @BindingAdapter("app:layout_width")
    public static void width(View view, int width) {
        if (width == -1) {
            view.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else if (width == 0) {
            view.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else if (width < -1) {
            jLogger.w("invalid width");
            view.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            view.getLayoutParams().width = width;
        }
    }

    @BindingAdapter("app:elevation")
    public static void elevation(View view, float elevation) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setElevation(elevation);
        } else {
            jLogger.w("Sorry, the system version of the device is under API 21, elevation will take no effect.");
        }
    }

    @BindingAdapter("app:elevation")
    public static void elevationBoolean(View view, boolean elevation) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && elevation) {
            view.setElevation(view.getContext().getResources().getInteger(R.integer.common_elevation));
        } else {
            jLogger.w("Sorry, the system version of the device is under API 21, elevation will take no effect.");
        }
    }

    @BindingAdapter("app:margin")
    public static void margin(View view, List<Integer> margins) {
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        lp.setMargins(margins.get(0), margins.get(1), margins.get(2), margins.get(3));
        view.setLayoutParams(lp);
    }

    @BindingAdapter("app:enable_changeAnim")
    public static void enableLayoutChangeAnim(ViewGroup viewGroup, boolean enable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            viewGroup.setLayoutTransition(enable ? new LayoutTransition() : null);
        }
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, int visibility) {
        switch (visibility) {
            case 0:
                view.setVisibility(View.VISIBLE);
                break;
            case 4:
                view.setVisibility(View.INVISIBLE);
                break;
            case 8:
                view.setVisibility(View.GONE);
                break;
            default:
                view.setVisibility(View.GONE);
                break;
        }
    }

    @BindingAdapter("android:visibility")
    public static void setVisibilityBoolean(View view, boolean visibility) {
        view.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

}
