package io.mindjet.jetgear.mvvm.viewmodel.integrated;

import android.view.View;
import android.view.ViewGroup;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.IncludeHeaderRecyclerBinding;
import io.mindjet.jetgear.mvvm.adapter.ViewModelAdapter;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetgear.mvvm.viewmodel.list.RecyclerViewModel;

/**
 * Created by Mindjet on 2017/4/6.
 */

public abstract class HeaderRecyclerViewModel<V extends ViewInterface<IncludeHeaderRecyclerBinding>> extends BaseViewModel<V> {

    RecyclerViewModel recyclerViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.include_header_recycler;
    }

    @Override
    public void onViewAttached(View view) {
        afterViewAttached();
        initHeader(getSelfView().getBinding().llyContainer);
        initRecyclerView(getSelfView().getBinding().llyContainer);
        afterComponentBound();
    }

    protected void afterViewAttached() {

    }

    protected abstract void initHeader(ViewGroup container);

    private void initRecyclerView(ViewGroup container) {
        recyclerViewModel = new RecyclerViewModel(true);
        ViewModelBinder.bind(container, recyclerViewModel);
    }

    protected void afterComponentBound() {

    }

    public RecyclerViewModel getRecyclerViewModel() {
        return recyclerViewModel;
    }

    protected ViewModelAdapter getAdapter() {
        if (recyclerViewModel != null) {
            return recyclerViewModel.getAdapter();
        } else {
            throw new NullPointerException("The RecyclerViewModel hasn't been attached to the view, please invoke this method in afterComponentBound.");
        }
    }


}
