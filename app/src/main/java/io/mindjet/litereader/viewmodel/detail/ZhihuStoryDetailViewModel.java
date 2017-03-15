package io.mindjet.litereader.viewmodel.detail;

import android.content.res.ColorStateList;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import io.mindjet.jetgear.databinding.IncludeCoordinatorCollapseLayoutBinding;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityCompatInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetgear.mvvm.viewmodel.coordinator.CoordinatorCollapseLayoutViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.list.RecyclerViewModel;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.jetpack.R;
import io.mindjet.jetutil.anim.RevealUtil;
import io.mindjet.jetwidget.JToolBar;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.model.detail.ZhihuStoryDetail;
import io.mindjet.litereader.reactivex.RxAction;
import io.mindjet.litereader.service.ZhihuDailyService;
import io.mindjet.litereader.viewmodel.item.ZhihuStoryArticleViewModel;
import io.mindjet.litereader.viewmodel.item.ZhihuStoryImageViewModel;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Jet on 3/14/17.
 */

public class ZhihuStoryDetailViewModel extends CoordinatorCollapseLayoutViewModel<ActivityCompatInterface<IncludeCoordinatorCollapseLayoutBinding>> {

    private ZhihuDailyService service;

    private RecyclerViewModel recyclerViewModel;
    private ZhihuStoryImageViewModel storyImage;

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
        recyclerViewModel.getAdapter().disableLoadMore();
        recyclerViewModel.getRecyclerView().setBackgroundColor(getContext().getResources().getColor(R.color.gray_light_translucent));
    }

    @Override
    protected void initToolbar(JToolBar toolbar) {
        toolbar.setNavIcon(R.drawable.ic_left_arrow);
    }

    @Override
    protected void initFab(FloatingActionButton fab) {
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
        RevealUtil.revealActivity(getSelfView().getCompatActivity(), 500, centerX, centerY);
        service.getStoryDetail(getSelfView().getCompatActivity().getIntent().getStringExtra(Constant.EXTRA_ZHIHU_STORY_ID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ZhihuStoryDetail>() {
                    @Override
                    public void call(ZhihuStoryDetail detail) {
                        renderStoryImage(detail);
                        renderArticle(detail);
                    }
                }, RxAction.onError());
    }

    private void renderStoryImage(ZhihuStoryDetail detail) {
        storyImage.setImageUrl(detail.image);
        storyImage.setImageSource(detail.imageSource);
    }

    private void renderArticle(ZhihuStoryDetail detail) {
        recyclerViewModel.getAdapter().add(new ZhihuStoryArticleViewModel(detail.title, detail.body));
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
        getSelfView().getCompatActivity().getMenuInflater().inflate(R.menu.menu_zhihu_story, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
