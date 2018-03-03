package io.mindjet.jetgear.mvvm.viewmodel.list;

import android.databinding.ViewDataBinding;
import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.IncludeSwipeRecyclerViewBinding;
import io.mindjet.jetgear.mvvm.adapter.ViewModelAdapter;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.listener.LoadMoreListener;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetutil.task.Task;

/**
 * SwipeRefreshLayout+RecyclerView ViewModel, provides refresh and load more features.
 * <p>
 * Created by Mindjet on 3/2/17.
 */

public class SwipeRecyclerViewModel<S extends ViewDataBinding, V extends ViewInterface<IncludeSwipeRecyclerViewBinding>> extends BaseViewModel<V> implements LoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeLayout;
    private RecyclerViewModel<S> recyclerViewModel;

    @Override
    public void onViewAttached(View view) {
        swipeLayout = getSelfView().getBinding().swipeLayout;
        afterViewAttached();
        initSwipeLayout();
        initRecyclerView();
        afterComponentsBound();

        //auto refresh at the very first time
        swipeLayout.setRefreshing(true);
        Task.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        }, 600);
    }

    private void initSwipeLayout() {
        swipeLayout.setOnRefreshListener(this);
    }

    @SuppressWarnings("unchecked")
    private void initRecyclerView() {
        recyclerViewModel = new RecyclerViewModel(true);
        ViewModelBinder.bind(swipeLayout, recyclerViewModel);
        getAdapter().setLoadMoreListener(this);
    }

    /**
     * Please call the method after all components are bound. Otherwise, {@link NullPointerException} will be thrown.
     *
     * @return SwipeRefreshLayout.
     */
    protected SwipeRefreshLayout getSwipeLayout() {
        return swipeLayout;
    }

    /**
     * Please call the method after all components are bound. Otherwise, {@link NullPointerException} will be thrown.
     *
     * @return RecyclerView.
     */
    public RecyclerView getRecyclerView() {
        return recyclerViewModel.getRecyclerView();
    }

    /**
     * Please call the method after all components are bound. Otherwise, {@link NullPointerException} will be thrown.
     *
     * @return adapter of the RecyclerView.
     */
    public ViewModelAdapter<S> getAdapter() {
        if (recyclerViewModel != null) {
            return recyclerViewModel.getAdapter();
        } else {
            throw new NullPointerException("The RecyclerViewModel is NULL");
        }
    }

    protected void afterViewAttached() {

    }

    /**
     * All components are safe to manipulate in this method, as all of them are already bound.
     */
    protected void afterComponentsBound() {

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

    /**
     * Change the progress color in progress bar.
     *
     * @param draggingColor progress color.
     */
    public void changePbColor(@ColorRes int... draggingColor) {
        swipeLayout.setColorSchemeResources(draggingColor);
    }

    /**
     * Change the background color of the progress bar.
     *
     * @param background color resource.
     */
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
