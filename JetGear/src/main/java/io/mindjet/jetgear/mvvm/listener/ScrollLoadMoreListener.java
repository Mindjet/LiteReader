package io.mindjet.jetgear.mvvm.listener;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import io.mindjet.jetutil.logger.JLogger;

/**
 * A scroll Listener for RecyclerView with load more feature.
 * <p>
 * It performs well while be able to detect the layout manager of the RecyclerView and act differently.
 * <p>
 * Created by Mindjet on 2017/4/16.
 */

public abstract class ScrollLoadMoreListener extends RecyclerView.OnScrollListener {

    private JLogger jLogger = JLogger.get(getClass().getSimpleName());

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (newState != RecyclerView.SCROLL_STATE_IDLE) {
            return;
        }

        int lastVisibleItemPos;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager manager = ((GridLayoutManager) layoutManager);
            int spanCount = manager.getSpanCount();
            int itemCount = recyclerView.getAdapter().getItemCount();
            lastVisibleItemPos = manager.findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPos < spanCount || lastVisibleItemPos >= itemCount - 1 - spanCount) {
                onLoadMore();
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;
            int spanCount = manager.getSpanCount();
            int itemCount = recyclerView.getAdapter().getItemCount();
            lastVisibleItemPos = manager.findLastCompletelyVisibleItemPositions(null)[spanCount - 1];
            if (lastVisibleItemPos < spanCount || lastVisibleItemPos >= itemCount - 1 - spanCount) {
                onLoadMore();
            }
        } else {
            LinearLayoutManager manager = ((LinearLayoutManager) layoutManager);
            int itemCount = recyclerView.getAdapter().getItemCount();
            lastVisibleItemPos = manager.findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPos == 0 || lastVisibleItemPos >= itemCount - 2) {
                onLoadMore();
            }
        }
    }

    protected abstract void onLoadMore();

}
