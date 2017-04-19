package io.mindjet.litereader.viewmodel.detail.douban;

import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import io.mindjet.jetgear.mvvm.viewmodel.list.RecyclerViewModel;
import io.mindjet.litereader.databinding.ItemDoubanDetailStillBinding;
import io.mindjet.litereader.model.item.douban.Still;

/**
 * 电影详情中 剧照列表 view model
 * <p>
 * Created by Jet on 3/21/17.
 */

public class DetailStillViewModel extends RecyclerViewModel<ItemDoubanDetailStillBinding> {

    private List<Still> stills;

    public DetailStillViewModel(List<Still> stills) {
        super(false);
        this.stills = stills;
    }

    @Override
    protected void initRecyclerView() {
        getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        disableLoadMore();
        //这一句非常重要。因为该横向RecyclerView是嵌套在纵向RecyclerView中，该横向RecyclerView会默认捕捉焦点，导致在该横向RecyclerView上纵向滑动时不能触发纵向RecyclerView滑动。
        getRecyclerView().setNestedScrollingEnabled(false);
    }

    @Override
    protected void afterViewAttached() {
        for (Still still : stills) {
            getAdapter().add(new DetailStillItemViewModel(still));
        }
        if (stills.size() != 0) {
            getAdapter().add(new DetailStillItemViewModel(stills.get(0), true));
            getAdapter().notifyDataSetChanged();
        }
    }
}
