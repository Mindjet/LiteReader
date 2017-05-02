package io.mindjet.litereader.reactivex;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.Log;

import io.mindjet.jetutil.hint.Toaster;
import io.mindjet.jetutil.logger.JLogger;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Actions;

/**
 * 适用于 RxJava 的 {@link io.mindjet.jetutil.hint.Toaster}
 * <p>
 * Created by Mindjet on 2017/4/27.
 */

public class RxToaster {

    private static JLogger jLogger = JLogger.get(RxToaster.class.getClass().getSimpleName());

    public static Action0 showAction0(final Context context, @StringRes final int stringRes) {
        return new Action0() {
            @Override
            public void call() {
                Toaster.toast(context, context.getResources().getString(stringRes));
            }
        };
    }


    public static <T> Action1<T> showAction1(final Context context, @StringRes final int stringRes) {
        return Actions.toAction1(showAction0(context, stringRes));
    }

}
