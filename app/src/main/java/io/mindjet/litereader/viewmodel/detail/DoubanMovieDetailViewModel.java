package io.mindjet.litereader.viewmodel.detail;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import io.mindjet.jetgear.databinding.IncludeCoordinatorCollapseLayoutBinding;
import io.mindjet.jetgear.mvvm.adapter.ViewModelAdapter;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityCompatInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetgear.mvvm.viewmodel.coordinator.CoordinatorCollapseLayoutViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.list.RecyclerViewModel;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.jetgear.reactivex.RxTask;
import io.mindjet.jetgear.reactivex.rxbus.RxBus;
import io.mindjet.jetutil.anim.RevealUtil;
import io.mindjet.jetutil.hint.Toaster;
import io.mindjet.jetutil.manager.ShareManager;
import io.mindjet.jetwidget.JToolBar;
import io.mindjet.litereader.R;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.http.SimpleHttpSubscriber;
import io.mindjet.litereader.http.ThreadDispatcher;
import io.mindjet.litereader.model.detail.DoubanMovieDetail;
import io.mindjet.litereader.model.item.douban.Review;
import io.mindjet.litereader.service.DoubanService;
import io.mindjet.litereader.ui.activity.DoubanMovieMoreReviewActivity;
import io.mindjet.litereader.ui.activity.DoubanMovieReviewActivity;
import io.mindjet.litereader.ui.dialog.ShareDialog;
import io.mindjet.litereader.util.CollectionManager;
import io.mindjet.litereader.viewmodel.ICollection;
import io.mindjet.litereader.viewmodel.detail.douban.DetailImageViewModel;
import io.mindjet.litereader.viewmodel.detail.douban.DetailReviewItemViewModel;
import io.mindjet.litereader.viewmodel.detail.douban.DetailStaffViewModel;
import io.mindjet.litereader.viewmodel.detail.douban.DetailStillViewModel;
import io.mindjet.litereader.viewmodel.detail.douban.DetailSummaryViewModel;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func1;

/**
 * 豆瓣电影详情 view model
 * <p>
 * Created by Jet on 3/17/17.
 */

public class DoubanMovieDetailViewModel extends CoordinatorCollapseLayoutViewModel<ActivityCompatInterface<IncludeCoordinatorCollapseLayoutBinding>> implements ICollection {

    private String id;
    private String title;
    private String poster;
    private int touchX;
    private int touchY;
    private String mainlandPubdate;
    private String rating;

    private DoubanMovieDetail detail;
    private int index = 0;

    private DoubanService service;
    private RecyclerViewModel recyclerViewModel;
    private DetailSummaryViewModel summaryViewModel;
    private DetailStaffViewModel staffViewModel;
    private DetailStillViewModel stillViewModel;

    private Action2<Boolean, Review> onReviewItemClick;
    private Subscription detailSub;

    private Menu menu;
    private boolean isCollect;

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
        onReviewItemClick = new Action2<Boolean, Review>() {
            @Override
            public void call(Boolean lastOne, Review review) {
                // 如果是最后一项，则跳到评论列表，否则跳到评论详情
                Intent intent;
                if (lastOne) {
                    intent = DoubanMovieMoreReviewActivity.intentFor(getContext());
                    intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_ID, id);
                } else {
                    intent = DoubanMovieReviewActivity.intentFor(getContext());
                    intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_REVIEW, review);
                }
                intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_TITLE, title);
                getContext().startActivity(intent);
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
        recyclerViewModel = new RecyclerViewModel(true);
        ViewModelBinder.bind(container, recyclerViewModel);
        recyclerViewModel.getRecyclerView().setBackgroundColor(getContext().getResources().getColor(R.color.gray_light_translucent));
        getAdapter().disableLoadMore();
        getMovieDetail();
    }

    @Override
    protected void initToolbar(JToolBar toolbar) {
        toolbar.setNavIcon(R.drawable.ic_arrow_left);
    }

    @Override
    protected void initFab(FloatingActionButton fab) {
        fab.setVisibility(View.GONE);
        fab.setImageResource(R.drawable.ic_share);
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
        this.menu = menu;
        getSelfView().getCompatActivity().getMenuInflater().inflate(R.menu.menu_common, menu);
        initCollect();
        return true;
    }

    @Override
    public void initCollect() {
        RxTask.asyncMap(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return CollectionManager.getInstance(getContext()).contain(id, CollectionManager.COLLECTION_TYPE_DOUBAN_MOVIE);
            }
        }, new Action1<Boolean>() {
            @Override
            public void call(Boolean isCollect) {
                updateCollectIcon(isCollect);
            }
        });
    }

    @Override
    public void updateCollectIcon(boolean isCollect) {
        this.isCollect = isCollect;
        menu.getItem(1).setIcon(isCollect ? R.drawable.ic_star : R.drawable.ic_star_empty);
        menu.getItem(1).setTitle(isCollect ? R.string.menu_discollect : R.string.menu_collect);
    }

    @Override
    public void manipulateCollect() {
        if (isCollect) {
            disCollect();
        } else {
            collect();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_share:
                if (detail != null)
                    new ShareDialog(getContext(), detail.title + " " + detail.shareUrl, false).show();
                break;
            case R.id.item_collect:
                if (detail != null)
                    manipulateCollect();
                break;
            case R.id.item_more:
                if (detail != null)
                    ShareManager.with(getContext()).shareAll(detail.title + " " + detail.shareUrl);
                break;
        }
        return true;
    }

    @Override
    public void disCollect() {
        RxTask.asyncTask(new Action0() {
            @Override
            public void call() {
                CollectionManager.getInstance(getContext()).remove(id, CollectionManager.COLLECTION_TYPE_DOUBAN_MOVIE);
            }
        }, new Action0() {
            @Override
            public void call() {
                Toaster.toast(getContext(), R.string.remove_from_my_collection);
                updateCollectIcon(false);
            }
        });
    }

    @Override
    public void collect() {
        RxTask.asyncTask(new Action0() {
            @Override
            public void call() {
                CollectionManager.getInstance(getContext()).collect(detail);
            }
        }, new Action0() {
            @Override
            public void call() {
                Toaster.toast(getContext(), R.string.collect_success);
                updateCollectIcon(true);
            }
        });
    }

    private ViewModelAdapter getAdapter() {
        return recyclerViewModel.getAdapter();
    }

    private void getMovieDetail() {
        detailSub = service.getMovieDetail(id)
                .compose(new ThreadDispatcher<DoubanMovieDetail>())
                .subscribe(new SimpleHttpSubscriber<DoubanMovieDetail>() {
                    @Override
                    public void onNext(DoubanMovieDetail detail) {
                        addItems(detail);
                    }
                });
    }

    private void addItems(DoubanMovieDetail detail) {
        //TODO 不要回收简介view model，因为高度变化时可能被回收导致高度变化动画停止。
//        recyclerViewModel.getRecyclerView().getRecycledViewPool().setMaxRecycledViews(R.layout.item_douban_detail_summary, 100);
        this.detail = detail;

        staffViewModel = new DetailStaffViewModel(title, detail.writers, detail.directors, detail.actors);
        getAdapter().add(staffViewModel);
        getAdapter().notifyItemInserted(index++);

        stillViewModel = new DetailStillViewModel(detail.photos, detail.id, detail.title);
        getAdapter().add(stillViewModel);
        getAdapter().notifyItemInserted(index++);

        summaryViewModel = new DetailSummaryViewModel(detail.summary);
        getAdapter().add(summaryViewModel);
        getAdapter().notifyItemInserted(index++);

        for (Review review : detail.popularReviews) {
            getAdapter().add(new DetailReviewItemViewModel(review).onAction(onReviewItemClick));
        }
        if (detail.popularReviews.size() != 0) {
            getAdapter().notifyItemRangeInserted(index, detail.popularReviews.size());
            index += detail.popularReviews.size();
            getAdapter().add(new DetailReviewItemViewModel(detail.popularReviews.get(0), true).onAction(onReviewItemClick));
            getAdapter().notifyItemInserted(index);
        }
    }

    @Override
    public void onDestroy() {
        RxBus.unSubscribe(detailSub);
    }
}
