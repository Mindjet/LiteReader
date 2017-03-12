package io.mindjet.jetgear.mvvm.viewinterface;

import android.content.Context;
import android.databinding.ViewDataBinding;

/**
 * Created by Jet on 2/17/17.
 */

public interface ViewInterface<V extends ViewDataBinding> {

    Context getContext();

    V getBinding();

}
