package io.mindjet.litereader.http;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.mindjet.jetutil.hint.Toaster;
import io.mindjet.jetutil.logger.JLogger;
import io.mindjet.litereader.BaseApp;
import rx.Subscriber;

/**
 * 统一处理 error 信息
 * <p>
 * Created by Mindjet on 2017/4/19.
 */

public abstract class SimpleHttpSubscriber<T> extends Subscriber<T> {

    private JLogger jLogger = JLogger.get(getClass().getSimpleName());

    @Override
    public void onError(Throwable throwable) {
        if (throwable instanceof UnknownHostException) {
            Toaster.toast(BaseApp.me(), "无法连接至服务器，请检查您的网络连接或者稍后再试。");
        } else if (throwable instanceof SocketTimeoutException) {
            Toaster.toast(BaseApp.me(), "连接服务器超时，请重试。");
        } else {
            Toaster.toast(BaseApp.me(), throwable.getMessage());
        }
        throwable.printStackTrace();
        onFailed();
        onCompleted();          //默认情况下，跑入error后不会执行onComplete
    }

    protected void onFailed() {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public abstract void onNext(T t);

}
