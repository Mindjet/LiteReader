package io.mindjet.jetgear.mvvm.viewinterface;

import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Jet on 3/6/17.
 */

public interface ActivityCompatInterface<V extends ViewDataBinding> extends ViewInterface<V> {

    AppCompatActivity getCompatActivity();

}
