package io.mindjet.litereader.viewmodel.detail.douban;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.BounceInterpolator;

import java.util.List;

import io.mindjet.jetgear.mvvm.viewmodel.list.RecyclerViewModel;
import io.mindjet.litereader.databinding.ItemDoubanMovieBinding;
import io.mindjet.litereader.model.item.douban.Work;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;

/**
 * 影人作品列表 view model
 * <p>
 * Created by Mindjet on 2017/4/17.
 */

public class StaffDetailWorkViewModel extends RecyclerViewModel<ItemDoubanMovieBinding> {

    private List<Work> workList;
    private boolean hasUpdate = false;

    public StaffDetailWorkViewModel(List<Work> workList) {
        super(false);
        this.workList = workList;
    }

    @Override
    protected void afterViewAttached() {
        super.afterViewAttached();
    }

    @Override
    protected void initRecyclerView() {
        getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        getRecyclerView().setItemAnimator(new SlideInDownAnimator(new BounceInterpolator()));
    }

    @Override
    public void onViewAttached(View view) {
        super.onViewAttached(view);
        disableLoadMore();
        if (!hasUpdate) {
            update(workList);
            hasUpdate = true;
        }
    }

    private void update(List<Work> workList) {
        for (Work work : workList) {
            getAdapter().add(new StaffDetailWorkItemViewModel(work));
        }
        if (workList.size() != 0)
            getAdapter().notifyItemRangeInserted(0, workList.size());
    }

}
