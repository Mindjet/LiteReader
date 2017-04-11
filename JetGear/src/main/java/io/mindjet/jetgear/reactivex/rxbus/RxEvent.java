package io.mindjet.jetgear.reactivex.rxbus;

import android.support.v4.util.Pair;

/**
 * Created by Mindjet on 2017/4/11.
 */

public class RxEvent extends Pair<String, Object> {
    /**
     * Constructor for a Pair.
     *
     * @param first  the first object in the Pair
     * @param second the second object in the pair
     */
    public RxEvent(String first, Object second) {
        super(first, second);
    }
}
