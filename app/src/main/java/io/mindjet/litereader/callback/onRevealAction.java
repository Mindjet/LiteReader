package io.mindjet.litereader.callback;

import rx.functions.Action3;

/**
 * Created by Jet on 3/20/17.
 */

public interface onRevealAction<T> {
    T onAction(Action3<String, Integer, Integer> onAction);
}
