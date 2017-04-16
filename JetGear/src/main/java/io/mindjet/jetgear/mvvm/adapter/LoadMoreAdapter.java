package io.mindjet.jetgear.mvvm.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.ItemProgressBinding;
import io.mindjet.jetgear.mvvm.base.BaseViewHolder;
import io.mindjet.jetgear.mvvm.listener.LoadMoreListener;
import io.mindjet.jetutil.task.Task;

/**
 * RecyclerView Adapter, provides load more feature.
 * <p>
 * Created by Jet on 2/16/17.
 */

@Deprecated
public abstract class LoadMoreAdapter<T, V extends ViewDataBinding> extends ListAdapter<T, V> {

    public LoadMoreListener loadMoreListener;
    private boolean loadMore = true;
    private ItemProgressBinding progressBinding;

    public LoadMoreAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        return loadMore ? size() + 1 : size();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (loadMore && viewType == R.layout.item_progress) {
            progressBinding = ItemProgressBinding.inflate(getInflater(), parent, false);
            return new BaseViewHolder<>(progressBinding);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<V> holder, int position) {
        if (loadMore && position == size()) {
            holder.getBinding().getRoot().setVisibility(View.VISIBLE);
            loadMore();
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (loadMore && position == getItemCount() - 1) {
            return R.layout.item_progress;
        }
        return super.getItemViewType(position);
    }

    public void enableLoadMore() {
        loadMore = true;
    }

    public void disableLoadMore() {
        loadMore = false;
    }

    public void finishLoadMore(boolean lastPage) {
        progressBinding.getRoot().setVisibility(View.GONE);
        if (lastPage) loadMore = false;
    }

    /**
     * This method is used to refresh the new-added items and continue loading more (Basically it triggers the method {@link #onBindViewHolder(BaseViewHolder, int)}).
     */
    public void updateAndContinue() {
        notifyItemInserted(size());
    }

    private void loadMore() {
        if (loadMoreListener != null) {
            Task.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadMoreListener.onLoadMore();
                }
            }, 500);
        }
    }

}
