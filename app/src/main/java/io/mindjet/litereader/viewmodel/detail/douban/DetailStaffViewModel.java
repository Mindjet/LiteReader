package io.mindjet.litereader.viewmodel.detail.douban;

import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import io.mindjet.jetgear.mvvm.viewmodel.list.RecyclerViewModel;
import io.mindjet.litereader.databinding.ItemDoubanDetailStaffBinding;
import io.mindjet.litereader.model.item.douban.Staff;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * 电影详情中 演职员表 view model
 * <p>
 * Created by Jet on 3/21/17.
 */

public class DetailStaffViewModel extends RecyclerViewModel<ItemDoubanDetailStaffBinding> {

    private int index = 0;
    private List<Staff> writers;
    private List<Staff> directors;
    private List<Staff> actors;

    public DetailStaffViewModel(List<Staff> writers, List<Staff> directors, List<Staff> actors) {
        super(false);
        this.writers = writers;
        this.directors = directors;
        this.actors = actors;
    }

    @Override
    protected void initRecyclerView() {
        getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        getRecyclerView().setItemAnimator(new SlideInUpAnimator());
        disableLoadMore();
        //这一句非常重要。因为该横向RecyclerView是嵌套在纵向RecyclerView中，该横向RecyclerView会默认捕捉焦点，导致在该横向RecyclerView上纵向滑动时不能触发纵向RecyclerView滑动。
        getRecyclerView().setNestedScrollingEnabled(false);
    }

    @Override
    protected void afterViewAttached() {
        for (Staff writer : writers) {
            getAdapter().add(new DetailStaffItemViewModel(writer, "作者"));
        }
        getAdapter().notifyItemRangeInserted(index, index += writers.size());
        for (Staff director : directors) {
            getAdapter().add(new DetailStaffItemViewModel(director, "导演"));
        }
        getAdapter().notifyItemRangeInserted(index, index += directors.size());
        for (Staff actor : actors) {
            getAdapter().add(new DetailStaffItemViewModel(actor, "主演"));
        }
        getAdapter().notifyItemRangeInserted(index, index += actors.size());
    }
}
