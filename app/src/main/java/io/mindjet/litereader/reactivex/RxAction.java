package io.mindjet.litereader.reactivex;

import io.mindjet.jetutil.hint.Toaster;
import io.mindjet.litereader.BaseApp;
import rx.functions.Action1;

/**
 * Created by Jet on 3/14/17.
 */

public class RxAction {

    public static Action1<Throwable> onError() {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Toaster.toast(BaseApp.me(), throwable.toString());
            }
        };
    }

}
