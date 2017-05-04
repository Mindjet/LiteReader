package io.mindjet.jetgear.reactivex.rxbus;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * A bus implemented by RxJava.
 * <p>
 * Created by Mindjet on 2017/4/11.
 */

public class RxBus {

    private static RxBus rxBus;
    private final Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());

    public static RxBus getInstance() {
        if (rxBus == null) {
            synchronized (RxBus.class) {
                if (rxBus == null) {
                    rxBus = new RxBus();
                    return rxBus;
                }
            }
        }
        return rxBus;
    }

    public static void unSubscribe(Subscription... subscriptions) {
        for (Subscription subscription : toList(subscriptions)) {
            if (subscription != null && !subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static List<Subscription> toList(Subscription... subscriptions) {
        return subscriptions == null ? Collections.EMPTY_LIST : Arrays.asList(subscriptions);
    }

    public void send(Object o, @NonNull String signal) {
        bus.onNext(new RxEvent(signal, o));
    }

    @SuppressWarnings("unchecked")
    public <T> Observable<T> receive(final Class<T> type, final String signal) {
        return bus
                .filter(new Func1<Object, Boolean>() {
                    @Override
                    public Boolean call(Object o) {
                        if (!RxEvent.class.isInstance(o)) {
                            return false;
                        }
                        final RxEvent event = (RxEvent) o;
                        return type.isInstance(event.second) && signal.equals(event.first);
                    }
                })
                .map(new Func1<Object, T>() {
                    @Override
                    public T call(Object o) {
                        return ((T) ((RxEvent) o).second);
                    }
                });
    }

}
