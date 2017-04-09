package io.mindjet.litereader.viewmodel.detail;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import io.mindjet.jetgear.databinding.IncludeCoordinatorCollapseLayoutBinding;
import io.mindjet.jetgear.mvvm.adapter.ViewModelAdapter;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityCompatInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetgear.mvvm.viewmodel.coordinator.CoordinatorCollapseLayoutViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.list.RecyclerViewModel;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.jetutil.anim.RevealUtil;
import io.mindjet.jetwidget.JToolBar;
import io.mindjet.litereader.R;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.model.detail.DoubanMovieDetail;
import io.mindjet.litereader.model.item.douban.Review;
import io.mindjet.litereader.reactivex.ActionHttpError;
import io.mindjet.litereader.service.DoubanService;
import io.mindjet.litereader.ui.activity.DoubanMovieMoreReviewActivity;
import io.mindjet.litereader.viewmodel.detail.douban.DetailImageViewModel;
import io.mindjet.litereader.viewmodel.detail.douban.DetailReviewItemViewModel;
import io.mindjet.litereader.viewmodel.detail.douban.DetailStaffViewModel;
import io.mindjet.litereader.viewmodel.detail.douban.DetailStillViewModel;
import io.mindjet.litereader.viewmodel.detail.douban.DetailSummaryViewModel;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Jet on 3/17/17.
 */

public class DoubanMovieDetailViewModel extends CoordinatorCollapseLayoutViewModel<ActivityCompatInterface<IncludeCoordinatorCollapseLayoutBinding>> {

    private String id;
    private String title;
    private String poster;
    private int touchX;
    private int touchY;
    private String mainlandPubdate;
    private String rating;

    private int index = 0;

    private DoubanService service;
    private RecyclerViewModel recyclerViewModel;
    private DetailSummaryViewModel summaryViewModel;
    private DetailStaffViewModel staffViewModel;
    private DetailStillViewModel stillViewModel;

    private Action1<Boolean> onReviewItemClick;

    @Override
    protected void afterViewAttached() {
        service = ServiceGen.create(DoubanService.class);
        Intent intent = getSelfView().getCompatActivity().getIntent();
        id = intent.getStringExtra(Constant.EXTRA_DOUBAN_MOVIE_ID);
        title = intent.getStringExtra(Constant.EXTRA_DOUBAN_MOVIE_TITLE);
        poster = intent.getStringExtra(Constant.EXTRA_DOUBAN_MOVIE_POSTER);
        touchX = intent.getIntExtra(Constant.EXTRA_TOUCH_X, 0);
        touchY = intent.getIntExtra(Constant.EXTRA_TOUCH_Y, 0);
        mainlandPubdate = intent.getStringExtra(Constant.EXTRA_DOUBAN_MOVIE_MAINLAND_PUBDATE);
        rating = intent.getStringExtra(Constant.EXTRA_DOUBAN_MOVIE_RATING);
        RevealUtil.revealActivity(getSelfView().getCompatActivity(), 1000, touchX, touchY);
        initActions();
    }

    private void initActions() {
        onReviewItemClick = new Action1<Boolean>() {
            @Override
            public void call(Boolean lastOne) {
                if (lastOne) {
                    Intent intent = DoubanMovieMoreReviewActivity.intentFor(getContext());
                    intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_ID, id);
                    intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_TITLE, title);
                    getContext().startActivity(intent);
                }
            }
        };
    }

    @Override
    protected void initCollapsingToolbar(CollapsingToolbarLayout layout) {
        layout.setTitle(title);
        layout.setContentScrimResource(R.color.colorPrimary);
        layout.setExpandedTitleTextAppearance(R.style.ToolbarTextAppearance_DoubanDetailExpanded);
        layout.setExpandedTitleMarginStart((int) getContext().getResources().getDimension(R.dimen.douban_movie_detail_expanded_title_margin_start));
        layout.setExpandedTitleMarginBottom((int) getContext().getResources().getDimension(R.dimen.douban_movie_detail_expanded_title_margin_bottom));
    }

    @Override
    protected void setActionBar(JToolBar toolBar) {
        getSelfView().getCompatActivity().setSupportActionBar(getSelfView().getBinding().toolbar);
    }

    @Override
    protected void initCollapsingContent(ViewGroup container) {
        ViewModelBinder.bind(container, new DetailImageViewModel(poster, mainlandPubdate, rating));
    }

    @Override
    protected void initContent(ViewGroup container) {
        //TODO 添加简介、演职员表、长短评、剧照、标签
        recyclerViewModel = new RecyclerViewModel(true);
        ViewModelBinder.bind(container, recyclerViewModel);
        recyclerViewModel.getRecyclerView().setBackgroundColor(getContext().getResources().getColor(R.color.gray_light_translucent));
        recyclerViewModel.getAdapter().disableLoadMore();
        getMovieDetail();
    }

    @Override
    protected void initToolbar(JToolBar toolbar) {
        toolbar.setNavIcon(R.drawable.ic_arrow_left);
    }

    @Override
    protected void initFab(FloatingActionButton fab) {
        fab.setImageResource(R.drawable.ic_star);
        fab.setBackgroundTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.colorPrimary)));
    }

    @Override
    protected void onNavigationIconClick() {
        RevealUtil.concealActivity(getSelfView().getCompatActivity(), 1000, touchX, touchY);
    }

    @Override
    protected void onFabClick() {

    }

    @Override
    public boolean onCreateOptionMenu(Menu menu) {
        return super.onCreateOptionMenu(menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    private ViewModelAdapter getAdapter() {
        return recyclerViewModel.getAdapter();
    }

    private void getMovieDetail() {
        service.getMovieDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DoubanMovieDetail>() {
                    @Override
                    public void call(DoubanMovieDetail detail) {
                        addItems(detail);
                    }
                }, new ActionHttpError() {
                    @Override
                    protected void onError() {

                    }
                });
    }

    private void addItems(DoubanMovieDetail detail) {
        staffViewModel = new DetailStaffViewModel(detail.writers, detail.directors, detail.actors);
        getAdapter().add(staffViewModel);
        getAdapter().notifyItemInserted(index++);

        stillViewModel = new DetailStillViewModel(detail.photos);
        getAdapter().add(stillViewModel);
        getAdapter().notifyItemInserted(index++);

        summaryViewModel = new DetailSummaryViewModel(detail.summary);
        getAdapter().add(summaryViewModel);
        getAdapter().notifyItemInserted(index++);

        for (Review review : detail.popularReviews) {
            getAdapter().add(new DetailReviewItemViewModel(review));
        }
        getAdapter().notifyItemRangeInserted(index, detail.popularReviews.size());
        index += detail.popularReviews.size();
        getAdapter().add(new DetailReviewItemViewModel(detail.popularReviews.get(0), true).onAction(onReviewItemClick));
        getAdapter().notifyItemInserted(index);
    }

}
