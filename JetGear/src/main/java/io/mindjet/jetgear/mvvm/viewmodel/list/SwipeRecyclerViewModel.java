package io.mindjet.jetgear.mvvm.viewmodel.list;

import android.databinding.ViewDataBinding;
import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.IncludeSwipeRecyclerViewBinding;
import io.mindjet.jetgear.mvvm.adapter.ViewModelAdapter;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.listener.LoadMoreListener;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;

/**
 * Created by Jet on 3/2/17.
 */

public class SwipeRecyclerViewModel<S extends ViewDataBinding, V extends ViewInterface<IncludeSwipeRecyclerViewBinding>> extends BaseViewModel<V> implements LoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeLayout;
    private RecyclerView recyclerView;
    private ViewModelAdapter<S> viewModelAdapter;

    @Override
    public void onViewAttached(View view) {
        afterViewAttached();
        recyclerView = getSelfView().getBinding().recyclerView;
        swipeLayout = getSelfView().getBinding().swipeLayout;
        initSwipeLayout();
        initRecyclerView();
    }

    private void initSwipeLayout() {
        swipeLayout.setOnRefreshListener(this);
    }

    //Can be overridden if not satisfied with the default LinearLayoutManager.
    public void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(getAdapter());
    }

    public SwipeRefreshLayout getSwipeLayout() {
        return swipeLayout;
    }

    public ViewModelAdapter<S> getAdapter() {
        if (viewModelAdapter == null) {
            viewModelAdapter = new ViewModelAdapter<>(getContext());
            viewModelAdapter.setLoadMoreListener(this);
        }
        return viewModelAdapter;
    }

    protected void afterViewAttached() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.include_swipe_recycler_view;
    }

    public void hideRefreshing() {
        swipeLayout.setRefreshing(false);
    }

    public void showRefreshing() {
        swipeLayout.setRefreshing(true);
    }

    public void changePbColor(@ColorRes int... draggingColor) {
        swipeLayout.setColorSchemeResources(draggingColor);
    }

    public void changePbBackground(@ColorRes int background) {
        swipeLayout.setProgressBackgroundColorSchemeResource(background);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
