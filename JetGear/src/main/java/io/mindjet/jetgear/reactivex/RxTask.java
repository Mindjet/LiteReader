package io.mindjet.jetgear.reactivex;

import android.support.annotation.NonNull;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * A task handler implemented by RxJava.
 * <p>
 * Created by Jet on 5/4/17.
 */

public class RxTask {

    /**
     * Start an async task which can do things in background and callback when the job is done on the main thread.
     *
     * @param doInBackground action to do in the background.
     * @param doOnFinish     action to do when the job is done.(this is called on main thread)
     * @return the subscription of the task.
     */
    public static Subscription asyncTask(@NonNull Action0 doInBackground, Action0 doOnFinish) {
        return asyncTask(null, doInBackground, doOnFinish);
    }

    /**
     * Start an async task which can do things beforehand, in background and callback when the job is done on the main thread.
     *
     * @param preExecute     action to do beforehand.
     * @param doInBackground action to do in the background.
     * @param doOnFinish     action to do when the job is done.(this is called on main thread)
     * @return the subscription of the task.
     */
    public static Subscription asyncTask(final Action0 preExecute, @NonNull final Action0 doInBackground, final Action0 doOnFinish) {
        return asyncTask(preExecute, doInBackground, doOnFinish, null);
    }

    /**
     * Start an async task which can do things beforehand, in background and callback when the job is done on the main thread, and handle the exception with the given action.
     *
     * @param preExecute     action to do beforehand.
     * @param doInBackground action to do in the background.
     * @param doOnFinish     action to do when the job is done.(this is called on main thread)
     * @param onError        action to do when exceptions are thrown.
     * @return the subscription of the task.
     */
    public static Subscription asyncTask(final Action0 preExecute, @NonNull final Action0 doInBackground, final Action0 doOnFinish, Action1<Throwable> onError) {
        return Observable.just("Hey nerd! This is an async task.")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (preExecute != null) preExecute.call();
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(Actions.toAction1(doInBackground))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (doOnFinish != null) doOnFinish.call();
                    }
                }, onError == null ? RxActions.onError() : onError);
    }

    /**
     * Start an async task which can map things in background and callback when the mapping job is done on the main thread.
     *
     * @param mapper     action to do the mapping job.
     * @param doOnFinish action to do when the job is done.(this is called on main thread)
     * @return the subscription of the whole mapping job.
     */
    public static <T> Subscription asyncMap(@NonNull final Func1<String, T> mapper, final Action1<T> doOnFinish) {
        return asyncMap(null, mapper, doOnFinish, null);
    }

    /**
     * Start an async task which can do things beforehand, map things in background and callback when the mapping job is done on the main thread.
     *
     * @param preExecute action to do beforehand.
     * @param mapper     action to do the mapping job.
     * @param doOnFinish action to do when the job is done.(this is called on main thread)
     * @return the subscription of the whole mapping job.
     */
    public static <T> Subscription asyncMap(final Action0 preExecute, @NonNull final Func1<String, T> mapper, final Action1<T> doOnFinish) {
        return asyncMap(preExecute, mapper, doOnFinish, null);
    }

    /**
     * Start an async task which can do things beforehand, map things in background and callback when the mapping job is done on the main thread, and handle the exception with the given action.
     *
     * @param preExecute action to do beforehand.
     * @param mapper     action to do the mapping job.
     * @param doOnFinish action to do when the job is done.(this is called on main thread)
     * @param onError    action to do when exceptions are thrown.
     * @return the subscription of the whole mapping job.
     */
    public static <T> Subscription asyncMap(final Action0 preExecute, @NonNull final Func1<String, T> mapper, final Action1<T> doOnFinish, Action1<Throwable> onError) {
        return Observable.just("Hey nerd! This is an async map.")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (preExecute != null) preExecute.call();
                    }
                })
                .map(mapper)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<T>() {
                    @Override
                    public void call(T t) {
                        if (doOnFinish != null) doOnFinish.call(t);
                    }
                }, onError == null ? RxActions.onError() : onError);
    }

}
