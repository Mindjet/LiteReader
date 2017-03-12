package io.mindjet.jetgear.mvvm.viewinterface;

import android.databinding.ViewDataBinding;

import io.mindjet.jetgear.mvvm.adapter.ViewModelAdapter;
import io.mindjet.jetgear.mvvm.base.BaseViewHolder;

/**
 * Created by Jet on 2/17/17.
 */

public interface AdapterInterface<V extends ViewDataBinding> extends ViewInterface<V> {

    ViewModelAdapter<V> getAdapter();

    BaseViewHolder<V> getViewHolder();

}
