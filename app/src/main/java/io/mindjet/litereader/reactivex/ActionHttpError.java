package io.mindjet.litereader.reactivex;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.mindjet.jetutil.hint.Toaster;
import io.mindjet.litereader.BaseApp;
import rx.functions.Action1;

/**
 * Created by Jet on 3/16/17.
 */

public abstract class ActionHttpError implements Action1<Throwable> {

    @Override
    public void call(Throwable throwable) {
        Toaster.toast(BaseApp.me(), resolveThrowable(throwable), 1500);
        onError();
    }

    private static String resolveThrowable(Throwable throwable) {
        if (throwable instanceof UnknownHostException) {
            return "无法连接至服务器，请检查您的网络连接或者稍后再试。";
        } else if (throwable instanceof SocketTimeoutException) {
            return "连接服务器超时，请重试。";
        } else {
            return throwable.getMessage();
        }
    }

    protected abstract void onError();

}
