package io.mindjet.jetgear.reactivex;

import io.mindjet.jetutil.logger.JLogger;
import rx.functions.Action1;

/**
 * Simple actions for RxJava.
 * <p>
 * Created by Mindjet on 2017/4/11.
 */

public class RxActions {

    private static JLogger jLogger = JLogger.get("RxActions");

    public static Action1<Throwable> onError() {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                jLogger.e(throwable);
            }
        };
    }

}
