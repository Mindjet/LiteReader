package io.mindjet.litereader.reactivex;

import android.content.Context;
import android.support.annotation.StringRes;

import io.mindjet.jetwidget.LoadingView;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Actions;

/**
 * 适用于RxJava的{@link io.mindjet.jetwidget.LoadingView}
 * <p>
 * Created by Mindjet on 2017/4/26.
 */

public class RxLoadingView {

    public static Action0 show(final Context context, @StringRes final int stringRes) {
        return new Action0() {
            @Override
            public void call() {
                LoadingView.show(context, stringRes);
            }
        };
    }

    public static Action0 dismiss() {
        return new Action0() {
            @Override
            public void call() {
                LoadingView.dismiss();
            }
        };
    }

    public static <T> Action1<T> showAction1(Context context, @StringRes int stringRes) {
        return Actions.toAction1(show(context, stringRes));
    }

}
