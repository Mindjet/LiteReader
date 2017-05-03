package io.mindjet.litereader.viewmodel.detail.douban;

import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import io.mindjet.jetgear.mvvm.viewmodel.list.RecyclerViewModel;
import io.mindjet.litereader.databinding.ItemDoubanDetailStillBinding;
import io.mindjet.litereader.model.item.douban.Still;
import io.mindjet.litereader.ui.activity.DoubanStillListActivity;
import rx.functions.Action0;

/**
 * 电影详情中 剧照列表 view model
 * <p>
 * Created by Jet on 3/21/17.
 */

public class DetailStillViewModel extends RecyclerViewModel<ItemDoubanDetailStillBinding> {

    private String id;
    private String title;
    private List<Still> stills;

    public DetailStillViewModel(List<Still> stills, String id, String title) {
        super(false);
        this.stills = stills;
        this.id = id;
        this.title = title;
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
            getAdapter().add(new DetailStillItemViewModel(stills.get(0), true, new Action0() {
                @Override
                public void call() {
                    getContext().startActivity(DoubanStillListActivity.intentFor(getContext(), id, title));
                }
            }));
            getAdapter().notifyItemRangeInserted(0, stills.size());
        }
    }
}
