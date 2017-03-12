package io.mindjet.jetgear.mvvm.viewinterface;

import android.app.Activity;
import android.databinding.ViewDataBinding;

/**
 * Created by Jet on 2/17/17.
 */

public interface ActivityInterface<V extends ViewDataBinding> extends ViewInterface<V> {

    Activity getActivity();

}
