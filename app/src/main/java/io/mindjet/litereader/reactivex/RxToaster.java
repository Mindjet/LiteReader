package io.mindjet.litereader.reactivex;

import android.content.Context;
import android.support.annotation.StringRes;

import io.mindjet.jetutil.hint.Toaster;
import rx.functions.Action0;

/**
 * 适用于 RxJava 的 {@link io.mindjet.jetutil.hint.Toaster}
 * <p>
 * Created by Mindjet on 2017/4/27.
 */

public class RxToaster {

    public static Action0 show(final Context context, @StringRes final int stringRes) {
        return new Action0() {
            @Override
            public void call() {
                Toaster.toast(context, context.getResources().getString(stringRes));
            }
        };
    }


}
