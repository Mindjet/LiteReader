package io.mindjet.litereader.viewmodel.detail;

import android.content.res.ColorStateList;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import io.mindjet.jetgear.databinding.IncludeCoordinatorCollapseLayoutBinding;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityCompatInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetgear.mvvm.viewmodel.coordinator.CoordinatorCollapseLayoutViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.list.RecyclerViewModel;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.jetutil.anim.RevealUtil;
import io.mindjet.jetutil.manager.ShareManager;
import io.mindjet.jetwidget.JToolBar;
import io.mindjet.litereader.R;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.http.SimpleHttpSubscriber;
import io.mindjet.litereader.http.ThreadDispatcher;
import io.mindjet.litereader.model.detail.ZhihuStoryDetail;
import io.mindjet.litereader.reactivex.RxToaster;
import io.mindjet.litereader.service.ZhihuDailyService;
import io.mindjet.litereader.ui.dialog.ShareDialog;
import io.mindjet.litereader.util.CollectionManager;
import io.mindjet.litereader.viewmodel.detail.zhihu.ZhihuStoryArticleViewModel;
import io.mindjet.litereader.viewmodel.detail.zhihu.ZhihuStoryImageViewModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 知乎日报 文章 view model
 * <p>
 * Created by Jet on 3/14/17.
 */

public class ZhihuStoryDetailViewModel extends CoordinatorCollapseLayoutViewModel<ActivityCompatInterface<IncludeCoordinatorCollapseLayoutBinding>> {

    private ZhihuDailyService service;

    private RecyclerViewModel recyclerViewModel;
    private ZhihuStoryImageViewModel storyImage;

    private Menu menu;

    private String id;
    private boolean isCollect = false;
    private ZhihuStoryDetail detail;

    public ZhihuStoryDetailViewModel() {
        service = ServiceGen.create(ZhihuDailyService.class);
    }

    @Override
    protected void initCollapsingToolbar(CollapsingToolbarLayout layout) {
        layout.setTitle(getContext().getResources().getString(R.string.home));
        layout.setContentScrimResource(R.color.colorPrimary);
        layout.setExpandedTitleColor(getContext().getResources().getColor(R.color.transparent));
    }

    @Override
    protected void initCollapsingContent(ViewGroup container) {
        storyImage = new ZhihuStoryImageViewModel("", "");
        ViewModelBinder.bind(container, storyImage);
    }

    @Override
    protected void initContent(ViewGroup container) {
        recyclerViewModel = new RecyclerViewModel();
        ViewModelBinder.bind(container, recyclerViewModel);
        recyclerViewModel.disableLoadMore();
        recyclerViewModel.getRecyclerView().setBackgroundColor(getContext().getResources().getColor(R.color.gray_light_translucent));
    }

    @Override
    protected void initToolbar(JToolBar toolbar) {
        toolbar.setNavIcon(R.drawable.ic_arrow_left);
    }

    @Override
    protected void initFab(FloatingActionButton fab) {
        fab.setVisibility(View.GONE);
        fab.setImageResource(R.drawable.ic_drawer);
        fab.setBackgroundTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.colorPrimary)));
    }

    @Override
    protected void setActionBar(JToolBar toolBar) {
        getSelfView().getCompatActivity().setSupportActionBar(getSelfView().getBinding().toolbar);
    }

    @Override
    protected void afterViewAttached() {
        int centerX = getSelfView().getCompatActivity().getIntent().getIntExtra(Constant.EXTRA_TOUCH_X, 0);
        int centerY = getSelfView().getCompatActivity().getIntent().getIntExtra(Constant.EXTRA_TOUCH_Y, 0);
        id = getSelfView().getCompatActivity().getIntent().getStringExtra(Constant.EXTRA_ZHIHU_STORY_ID);
        RevealUtil.revealActivity(getSelfView().getCompatActivity(), 1000, centerX, centerY);
        service.getStoryDetail(id)
                .compose(new ThreadDispatcher<ZhihuStoryDetail>())
                .subscribe(new SimpleHttpSubscriber<ZhihuStoryDetail>() {
                    @Override
                    public void onNext(ZhihuStoryDetail detail) {
                        renderStoryImage(detail);
                        renderArticle(detail);
                    }
                });
    }

    private void renderStoryImage(ZhihuStoryDetail detail) {
        storyImage.setImageUrl(detail.image);
        storyImage.setImageSource(detail.imageSource);
    }

    private void renderArticle(ZhihuStoryDetail detail) {
        this.detail = detail;
        recyclerViewModel.getAdapter().add(new ZhihuStoryArticleViewModel(detail.title, detail.body));
        recyclerViewModel.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onNavigationIconClick() {
        getSelfView().getCompatActivity().finish();
    }

    @Override
    protected void onFabClick() {

    }

    @Override
    public boolean onCreateOptionMenu(Menu menu) {
        this.menu = menu;
        getSelfView().getCompatActivity().getMenuInflater().inflate(R.menu.menu_zhihu_story, menu);
        initCollect();
        return true;
    }

    private void initCollect() {
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return CollectionManager.getInstance(getContext()).contain(id, CollectionManager.COLLECTION_TYPE_ZHIHU_STORY);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleHttpSubscriber<Boolean>() {
                    @Override
                    public void onNext(Boolean isCollect) {
                        updateCollectIcon(isCollect);
                    }
                });
    }

    private void updateCollectIcon(boolean isCollect) {
        this.isCollect = isCollect;
        menu.getItem(1).setIcon(isCollect ? R.drawable.ic_star : R.drawable.ic_star_empty);
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

    private void manipulateCollect() {
        if (isCollect) {
            disCollect();
        } else {
            collect();
        }
    }

    /**
     * 取消收藏
     */
    private void disCollect() {
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        CollectionManager.getInstance(getContext()).remove(id, CollectionManager.COLLECTION_TYPE_ZHIHU_STORY);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(RxToaster.showAction1(getContext(), R.string.remove_from_my_collection))
                .subscribe(new SimpleHttpSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        updateCollectIcon(false);
                    }
                });
    }

    /**
     * 加入收藏
     */
    private void collect() {
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        CollectionManager.getInstance(getContext()).collect(detail);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .doOnUnsubscribe(RxToaster.showAction0(getContext(), R.string.collect_success))
                .subscribe(new SimpleHttpSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        updateCollectIcon(true);
                    }
                });
    }

}
