package io.mindjet.litereader.viewmodel.detail;

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
import io.mindjet.jetutil.hint.Toaster;
import io.mindjet.jetutil.manager.ShareManager;
import io.mindjet.jetwidget.JToolBar;
import io.mindjet.litereader.R;
import io.mindjet.litereader.http.SimpleHttpSubscriber;
import io.mindjet.litereader.http.ThreadDispatcher;
import io.mindjet.litereader.model.item.one.ArticleDetail;
import io.mindjet.litereader.model.list.OneData;
import io.mindjet.litereader.service.OneService;
import io.mindjet.litereader.ui.dialog.ShareDialog;
import io.mindjet.litereader.util.CollectionManager;
import io.mindjet.litereader.viewmodel.ICollection;
import io.mindjet.litereader.viewmodel.detail.one.OneArticleContentViewModel;
import io.mindjet.litereader.viewmodel.detail.one.OneCommonImageViewModel;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * ONE文章 详情 view model
 * <p>
 * Created by Mindjet on 5/5/17.
 */

public class OneArticleDetailViewModel extends CoordinatorCollapseLayoutViewModel<ActivityCompatInterface<IncludeCoordinatorCollapseLayoutBinding>> implements ICollection {

    private OneService service;
    private ArticleDetail detail;

    private String articleId;
    private String image;

    private Subscription detailSub;
    private boolean isCollect;

    private Menu menu;
    private OneCommonImageViewModel imageViewModel;
    private OneArticleContentViewModel contentViewModel;

    public OneArticleDetailViewModel(String articleId, String image) {
        this.articleId = articleId;
        this.image = image;
    }

    @Override
    protected void afterViewAttached() {
        service = ServiceGen.create(OneService.class);
        getArticleDetail();
    }

    @Override
    protected void initCollapsingToolbar(CollapsingToolbarLayout layout) {
        layout.setTitle(getString(R.string.column_one_article));
        layout.setContentScrimResource(R.color.colorPrimary);
        layout.setExpandedTitleTextAppearance(R.style.ToolbarTextAppearance_DoubanDetailExpanded);
        layout.setExpandedTitleColor(getContext().getResources().getColor(R.color.transparent));
    }

    @Override
    protected void setActionBar(JToolBar toolBar) {
        getSelfView().getCompatActivity().setSupportActionBar(toolBar);
    }

    @Override
    protected void initCollapsingContent(ViewGroup container) {
        imageViewModel = new OneCommonImageViewModel("", image);
        ViewModelBinder.bind(container, imageViewModel);
    }

    @Override
    protected void initContent(ViewGroup container) {
        RecyclerViewModel recyclerViewModel = new RecyclerViewModel(false);
        ViewModelBinder.bind(container, recyclerViewModel);
        recyclerViewModel.disableLoadMore();
        contentViewModel = new OneArticleContentViewModel();
        recyclerViewModel.getAdapter().add(contentViewModel);
        recyclerViewModel.getAdapter().notifyItemInserted(0);
    }

    @Override
    protected void initToolbar(JToolBar toolbar) {
        toolbar.setNavIcon(R.drawable.ic_arrow_left);
    }

    @Override
    protected void initFab(FloatingActionButton fab) {
        fab.setVisibility(View.GONE);
    }

    @Override
    protected void onNavigationIconClick() {
        getSelfView().getCompatActivity().finish();
    }

    @Override
    protected void onFabClick() {

    }

    private void getArticleDetail() {
        detailSub = service.getArticleDetail(articleId)
                .compose(new ThreadDispatcher<OneData<ArticleDetail>>())
                .map(new Func1<OneData<ArticleDetail>, ArticleDetail>() {
                    @Override
                    public ArticleDetail call(OneData<ArticleDetail> data) {
                        return data.data;
                    }
                })
                .subscribe(new SimpleHttpSubscriber<ArticleDetail>() {
                    @Override
                    public void onNext(ArticleDetail articleDetail) {
                        detail = articleDetail;
                        imageViewModel.update(articleDetail.authorIntroduce);
                        contentViewModel.update(detail);
                    }
                });
    }

    @Override
    public boolean onCreateOptionMenu(Menu menu) {
        this.menu = menu;
        getSelfView().getCompatActivity().getMenuInflater().inflate(R.menu.menu_common, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_share:
                if (detail != null)
                    new ShareDialog(getContext(), detail.title + " " + detail.webUrl, false).show();
                break;
            case R.id.item_collect:
                if (detail != null)
                    manipulateCollect();
                break;
            case R.id.item_more:
                if (detail != null)
                    ShareManager.with(getContext()).shareAll(detail.title + " " + detail.webUrl);
                break;
        }
        return true;
    }

    @Override
    public void initCollect() {
        RxTask.asyncMap(new Func1<String, Boolean>() {
            @Override
            public Boolean call(String s) {
                return CollectionManager.getInstance(getContext()).contain(articleId, CollectionManager.COLLECTION_TYPE_ONE_ARTICLE);
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
    public void disCollect() {
        RxTask.asyncTask(new Action0() {
            @Override
            public void call() {
                CollectionManager.getInstance(getContext()).remove(articleId, CollectionManager.COLLECTION_TYPE_ONE_ARTICLE);
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
                CollectionManager.getInstance(getContext()).collect(detail, image);
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
