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
import io.mindjet.litereader.model.detail.ZhihuStoryDetail;
import io.mindjet.litereader.service.ZhihuDailyService;
import io.mindjet.litereader.ui.dialog.ShareDialog;
import io.mindjet.litereader.util.CollectionManager;
import io.mindjet.litereader.viewmodel.ICollection;
import io.mindjet.litereader.viewmodel.detail.zhihu.ZhihuStoryArticleViewModel;
import io.mindjet.litereader.viewmodel.detail.zhihu.ZhihuStoryImageViewModel;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 知乎日报 文章 view model
 * <p>
 * Created by Jet on 3/14/17.
 */

public class ZhihuStoryDetailViewModel extends CoordinatorCollapseLayoutViewModel<ActivityCompatInterface<IncludeCoordinatorCollapseLayoutBinding>> implements ICollection {

    private ZhihuDailyService service;

    private RecyclerViewModel recyclerViewModel;
    private ZhihuStoryImageViewModel storyImage;

    private Subscription detailSub;

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
        recyclerViewModel.getAdapter().disableLoadMore();
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
        detailSub = service.getStoryDetail(id)
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
        getSelfView().getCompatActivity().getMenuInflater().inflate(R.menu.menu_common, menu);
        initCollect();
        return true;
    }

    @Override
    public void initCollect() {
        RxTask.asyncMap(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return CollectionManager.getInstance(getContext()).contain(id, CollectionManager.COLLECTION_TYPE_ZHIHU_STORY);
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
    public void manipulateCollect() {
        if (isCollect) {
            disCollect();
        } else {
            collect();
        }
    }

    @Override
    public void disCollect() {
        RxTask.asyncTask(new Action0() {
            @Override
            public void call() {
                CollectionManager.getInstance(getContext()).remove(id, CollectionManager.COLLECTION_TYPE_ZHIHU_STORY);
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

    @Override
    public void onDestroy() {
        RxBus.unSubscribe(detailSub);
    }
}
