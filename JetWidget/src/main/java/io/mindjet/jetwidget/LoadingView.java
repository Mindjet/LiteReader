package io.mindjet.jetwidget;

import android.content.Context;
import android.support.annotation.StringRes;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Custom Loading View implemented with MaterialDialog.
 * <p>
 * Created by Jet on 4/19/17.
 */

public class LoadingView {

    private static LoadingView loadingView;
    private MaterialDialog sMaterialDialog;

    private static LoadingView get() {
        if (loadingView == null) {
            synchronized (LoadingView.class) {
                if (loadingView == null) {
                    loadingView = new LoadingView();
                    return loadingView;
                }
            }
        }
        return loadingView;
    }

    public static void show(Context context, @StringRes int stringRes) {
        get().showLoadingView(context, stringRes, true);
    }

    public static void show(Context context, @StringRes int stringRes, boolean cancelable) {
        get().showLoadingView(context, stringRes, cancelable);
    }

    public static void dismiss() {
        get().dismissLoadingView();
    }

    /**
     * Show the loading dialog.
     *
     * @param context    the context.
     * @param stringRes  the resource id of the content.
     * @param cancelable whether this loading dialog is cancelable.
     */
    public void showLoadingView(Context context, @StringRes int stringRes, boolean cancelable) {
        if (sMaterialDialog == null) {
            sMaterialDialog = new MaterialDialog.Builder(context)
                    .progress(true, 0)
                    .widgetColorRes(R.color.colorAccent)
                    .content(stringRes)
                    .backgroundColorRes(R.color.white)
                    .contentGravity(GravityEnum.CENTER)
                    .canceledOnTouchOutside(cancelable)
                    .cancelable(cancelable)
                    .build();
            sMaterialDialog.show();
        } else if (sMaterialDialog.isShowing()) {
            sMaterialDialog.setContent(stringRes);
        } else {
            sMaterialDialog.show();
        }
    }

    /**
     * Dismiss current loading dialog and release resources.
     */
    public void dismissLoadingView() {
        if (sMaterialDialog != null && sMaterialDialog.isShowing()) {
            sMaterialDialog.dismiss();
            sMaterialDialog = null;
        }
    }

}
