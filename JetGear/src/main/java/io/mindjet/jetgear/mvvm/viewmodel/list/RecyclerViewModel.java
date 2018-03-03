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
import io.mindjet.jetgear.mvvm.listener.ViewAttachedListener;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;

/**
 * RecyclerView view model.
 * <p>
 * Created by Jet on 2/17/17.
 */

public class RecyclerViewModel<V extends ViewDataBinding> extends BaseViewModel<ViewInterface<IncludeRecyclerViewBinding>> {

    private ViewModelAdapter<V> adapter;
    private RecyclerView recyclerView;
    private boolean matchParent = true;

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
        initRecyclerView();
        afterViewAttached();
    }

    /**
     * Initialize the RecyclerView with a specific LayoutManager.
     */
    protected void initRecyclerView() {
        RecyclerView.LayoutManager lm = getLayoutManager();
        getRecyclerView().setLayoutManager(lm != null ? lm : new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    /**
     * Override this method to custom the LayoutManager of the RecyclerView.
     *
     * @return LayoutManager for RecyclerView.
     */
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    protected void afterViewAttached() {

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
            adapter = new ViewModelAdapter<>(getContext());
            return adapter;
        }
        return adapter;
    }

}
