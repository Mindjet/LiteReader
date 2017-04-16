package io.mindjet.jetgear.mvvm.viewmodel.integrated;

import android.support.annotation.ColorRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.IncludeHeaderSwipeLayoutBinding;
import io.mindjet.jetgear.mvvm.adapter.ViewModelAdapter;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetgear.mvvm.viewmodel.list.SwipeRecyclerViewModel;

/**
 * Created by Jet on 3/15/17.
 */

public abstract class HeaderSwipeLayoutViewModel<V extends ViewInterface<IncludeHeaderSwipeLayoutBinding>> extends BaseViewModel<V> {

    private SwipeLayoutViewModelImpl swipe;

    @Override
    public int getLayoutId() {
        return R.layout.include_header_swipe_layout;
    }

    @Override
    public void onViewAttached(View view) {
        afterViewAttached();
        initHeader(getSelfView().getBinding().llyContainer);
        initSwipeLayout(getSelfView().getBinding().llyContainer);
        afterComponentsBound();
    }

    protected abstract void afterViewAttached();

    protected void afterComponentsBound() {

    }

    protected abstract void initHeader(ViewGroup container);

    private void initSwipeLayout(ViewGroup container) {
        swipe = new SwipeLayoutViewModelImpl();
        ViewModelBinder.bind(container, swipe);
    }

    protected ViewModelAdapter getAdapter() {
        return swipe.getAdapter();
    }

    protected RecyclerView getRecyclerView() {
        return swipe.getRecyclerView();
    }

    protected SwipeLayoutViewModelImpl getSwipeLayout() {
        return swipe;
    }

    protected void hideRefreshing() {
        getSwipeLayout().hideRefreshing();
    }

    protected void showRefreshing() {
        getSwipeLayout().showRefreshing();
    }

    protected void finishLoadMore(boolean lastPage) {
//        getAdapter().finishLoadMore(lastPage); //TODO 修改
    }

    protected void changePbColor(@ColorRes int... color) {
        getSwipeLayout().changePbColor(color);
    }

    protected abstract void onRefresh();

    protected abstract void onLoadMore();

    private class SwipeLayoutViewModelImpl extends SwipeRecyclerViewModel {

        @Override
        public void onRefresh() {
            HeaderSwipeLayoutViewModel.this.onRefresh();
        }

        @Override
        public void onLoadMore() {
            HeaderSwipeLayoutViewModel.this.onLoadMore();
        }

    }

}
