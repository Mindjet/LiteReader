package io.mindjet.litereader.viewmodel.detail.douban;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.AdapterInterface;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemDoubanStaffWorkBinding;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.model.item.douban.Work;
import io.mindjet.litereader.ui.activity.DoubanMovieDetailActivity;

/**
 * 影人作品 item view model
 * <p>
 * Created by Mindjet on 2017/4/18.
 */

public class StaffDetailWorkItemViewModel extends BaseViewModel<ViewInterface<ItemDoubanStaffWorkBinding>> {

    private Work work;

    public StaffDetailWorkItemViewModel(Work work) {
        this.work = work;
    }

    public String getPoster() {
        return work.subject.images.large;
    }

    public String getTitle() {
        return work.subject.title;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_douban_staff_work;
    }

    @Override
    public void onViewAttached(View view) {
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(getSelfView().getBinding().cvContainer.getLayoutParams());
        int margin0 = (int) getContext().getResources().getDimension(R.dimen.common_gap);
        int margin1 = (int) getContext().getResources().getDimension(R.dimen.common_gap_medium);
        int margin2 = (int) getContext().getResources().getDimension(R.dimen.common_gap_small);
        int margin3 = (int) getContext().getResources().getDimension(R.dimen.common_gap_medium);
        if (getSelfView() instanceof AdapterInterface) {
            AdapterInterface ai = ((AdapterInterface) getSelfView());
            if (ai.getViewHolder().getLayoutPosition() == 0) {
                lp.setMargins(margin1, margin0, 0, margin3);
            } else if (ai.getViewHolder().getLayoutPosition() == ai.getAdapter().size() - 1) {
                lp.setMargins(margin2, margin0, margin1, margin3);
            } else {
                lp.setMargins(margin2, margin0, 0, margin3);
            }
        }
        getSelfView().getBinding().cvContainer.setLayoutParams(lp);
    }

    public void onClick() {
        Intent intent = DoubanMovieDetailActivity.intentFor(getContext());
        intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_ID, work.subject.id);
        intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_TITLE, work.subject.title);
        intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_MAINLAND_PUBDATE, work.subject.year);
        intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_POSTER, work.subject.images.large);
        intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_RATING, work.subject.rating.average + "/" + work.subject.rating.max);
        getContext().startActivity(intent);
    }


}
