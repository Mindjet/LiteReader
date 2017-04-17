package io.mindjet.litereader.http;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.mindjet.jetutil.hint.Toaster;
import io.mindjet.litereader.BaseApp;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 统一切换线程和 Toast 错误信息，可以自己添加错误处理
 * <p>
 * Created by Mindjet on 2017/4/16.
 */

public class SimpleHttpHandler<T> implements Observable.Transformer<T, T> {

    private Action1<Throwable> onError;

    public SimpleHttpHandler(Action1<Throwable> onError) {
        this.onError = onError;
    }

    public SimpleHttpHandler() {
    }

    @Override
    public Observable<T> call(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof UnknownHostException) {
                            Toaster.toast(BaseApp.me(), "无法连接至服务器，请检查您的网络连接或者稍后再试。");
                        } else if (throwable instanceof SocketTimeoutException) {
                            Toaster.toast(BaseApp.me(), "连接服务器超时，请重试。");
                        } else {
                            Toaster.toast(BaseApp.me(), throwable.getMessage());
                        }
                        if (onError != null) onError.call(throwable);
                    }
                });
    }
}
