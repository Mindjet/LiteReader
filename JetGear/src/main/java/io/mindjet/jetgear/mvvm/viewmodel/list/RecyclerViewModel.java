package io.mindjet.jetgear.mvvm.viewmodel.list;

import android.databinding.BindingAdapter;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.IncludeRecyclerViewBinding;
import io.mindjet.jetgear.mvvm.adapter.ViewModelAdapter;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.listener.LoadMoreListener;
import io.mindjet.jetgear.mvvm.listener.ScrollLoadMoreListener;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;

/**
 * RecyclerView ViewModel, provides load more feature.
 * <p>
 * Created by Jet on 2/17/17.
 */

public class RecyclerViewModel<V extends ViewDataBinding> extends BaseViewModel<ViewInterface<IncludeRecyclerViewBinding>> {

    private ViewModelAdapter<V> adapter;
    private RecyclerView recyclerView;
    private boolean hasLayoutManager = false;
    private boolean matchParent = true;

    private boolean enableLoadMore = true;
    private LoadMoreListener loadMoreListener;

    public RecyclerViewModel() {
    }

    public RecyclerViewModel(boolean matchParent) {
        this.matchParent = matchParent;
    }

    @BindingAdapter("android:adapter")
    public static void initAdapter(RecyclerView recyclerView, ViewModelAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    public int getHeight() {
        if (matchParent)
            return 0;
        return -1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_recycler_view;
    }

    @Override
    public void onViewAttached(View view) {
        recyclerView = getSelfView().getBinding().recyclerView;
        if (!hasLayoutManager) {
            initRecyclerView();
            hasLayoutManager = true;
            afterViewAttached();
            initLoadMoreFeature();
        }
    }

    /**
     * Initialize the RecyclerView with a specific LayoutManager, this method can be overridden to change the LayoutManager.
     */
    protected void initRecyclerView() {
        getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    protected void afterViewAttached() {

    }

    public void disableLoadMore() {
        this.enableLoadMore = false;
    }

    public void enableLoadMore() {
        this.enableLoadMore = true;
    }

    /**
     * Hide the loading view.
     */
    public void finishLoadMore() {
        //TODO Hide loading view.
    }

    private void initLoadMoreFeature() {
        recyclerView.addOnScrollListener(new ScrollLoadMoreListener() {
            @Override
            protected void onLoadMore() {
                if (enableLoadMore) {
                    RecyclerViewModel.this.onLoadMore();
                    if (loadMoreListener != null) loadMoreListener.onLoadMore();
                }
            }
        });
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public void onLoadMore() {

    }

    /**
     * If you call this method, please ensure that the RecyclerViewModel has been bound to the View. Otherwise, {@link NullPointerException} will be thrown.
     *
     * @return RecyclerView
     */
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    /**
     * If you call this method, please ensure that the RecyclerViewModel has been bound to the View. Otherwise, {@link NullPointerException} will be thrown.
     *
     * @return ViewModelAdapter
     */
    public ViewModelAdapter<V> getAdapter() {
        if (adapter == null) {
            return adapter = new ViewModelAdapter<>(getContext());
        }
        return adapter;
    }

}
