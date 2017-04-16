package io.mindjet.jetgear.mvvm.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;

import io.mindjet.jetgear.mvvm.base.BaseViewHolder;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;

/**
 * Adapter for RecyclerView for binding View Model.
 * <p>
 * Created by Jet on 2/17/17.
 */

public class ViewModelAdapter<V extends ViewDataBinding> extends ListAdapter<BaseViewModel, V> {

    private Context context;

    public ViewModelAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void onBindVH(BaseViewHolder<V> holder, int position) {
        ViewModelBinder.bind(this, get(position), holder);
    }

    @Override
    public int getItemLayoutId(int position) {
        return get(position).getLayoutId();
    }

    public Context getContext() {
        return context;
    }
}

