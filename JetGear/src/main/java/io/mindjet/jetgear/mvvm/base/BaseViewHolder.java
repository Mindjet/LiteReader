package io.mindjet.jetgear.mvvm.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.mindjet.jetgear.BR;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;

/**
 * Created by Jet on 2/10/17.
 */

public class BaseViewHolder<V extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private V binding;
    private BaseViewModel<ViewInterface<V>> viewModel;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public BaseViewHolder(V binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Object o) {
        binding.setVariable(BR.data, o);
        binding.executePendingBindings();
    }

    public V getBinding() {
        return binding;
    }

}
