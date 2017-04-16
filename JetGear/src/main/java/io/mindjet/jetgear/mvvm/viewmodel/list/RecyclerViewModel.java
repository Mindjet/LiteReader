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
import io.mindjet.jetgear.mvvm.viewmodel.item.LoadingViewModel;
import io.mindjet.jetutil.task.Task;

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
    private boolean isLoadingMore = false;
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
     * The ScrollLoadMoreListener->LoadMore mechanism should be stated here.
     * <p>
     * Basically, the {@link ScrollLoadMoreListener} can trigger load more operation,
     * however, it can only be triggered by the <code>ScrollStateChange</code> event,
     * which means that if you load not enough items at the the first time(not enough to fill the RecyclerView),
     * the other items wont't be auto-loaded, unless the user scrolls the RecyclerView to trigger load more.
     * <p>
     * So, it's not a perfect approach. And you are fully recommended to load enough items to fill the RecyclerView every time you load more.
     */
    private void initLoadMoreFeature() {
        recyclerView.addOnScrollListener(new ScrollLoadMoreListener() {
            @Override
            protected void onLoadMore() {
                // only if load-more is enabled and the recycler view is not currently loading more,
                // can we do the load more operation.
                if (enableLoadMore && !isLoadingMore) {
                    startLoadMore();
                }
            }
        });
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    /**
     * Set the isLoadingMore true and call the loadMore callback.
     */
    private void startLoadMore() {
        isLoadingMore = true;
        attachLoadingFooter();
        Task.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onLoadMore();
                if (loadMoreListener != null) loadMoreListener.onLoadMore();
            }
        }, 500);
    }

    /**
     * When is loading more, attach a loading footer to the last the the RecyclerView.
     */
    private void attachLoadingFooter() {
        getAdapter().add(getLoadingViewModel());
        getAdapter().notifyItemInserted(getAdapter().size() - 1);
        recyclerView.scrollToPosition(getAdapter().size() - 1);
    }

    /**
     * When load more is finished, the loading footer should be removed before new items are added to the adapter.
     */
    private void removeLoadingFooter() {
        getAdapter().remove(getAdapter().size() - 1);
        getAdapter().notifyItemRemoved(getAdapter().size() - 1);
    }

    /**
     * Get the loading footer. Can be override to custom loading footer.
     *
     * @return loading footer view model.
     */
    protected BaseViewModel getLoadingViewModel() {
        return new LoadingViewModel();
    }

    /**
     * Get the loading more status.
     *
     * @return the loading more status.
     */
    public boolean getIsLoadingMore() {
        return isLoadingMore;
    }

    /**
     * Set the loading more status.
     * <p>
     * If isLoadingMore, the method {@link #onLoadMore()} won't be called.
     * <p>
     * This method should be called after the load more is finished and must be called before the new items are added to the adapter.
     *
     * @param isLoadingMore whether the RecyclerView is loading more.
     */
    public void setIsLoadingMore(boolean isLoadingMore) {
        if (getIsLoadingMore() && !isLoadingMore) {     //if from LoadingMore to not LoadingMore, the loading footer should be removed.
            removeLoadingFooter();
        }
        this.isLoadingMore = isLoadingMore;
    }

    /**
     * This method is called according to the rule in the {@link ScrollLoadMoreListener}.
     * <p>
     * Note: Please call {@link #setIsLoadingMore(boolean)} after the load more operation is done or interrupted if you want the RecyclerView able to load more again.
     */
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
