package io.mindjet.litereader.viewmodel.list;

import io.mindjet.jetgear.mvvm.viewmodel.list.SwipeRecyclerViewModel;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.litereader.R;
import io.mindjet.litereader.model.list.DailyArticle;
import io.mindjet.litereader.reactivex.ActionHttpError;
import io.mindjet.litereader.service.OtherService;
import io.mindjet.litereader.viewmodel.item.DailyArticleItemViewModel;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Jet on 3/16/17.
 */

public class DailyArticleListViewModel extends SwipeRecyclerViewModel {

    private OtherService service;
    private Action0 onNextArticle;

    @Override
    protected void afterViewAttached() {
        service = ServiceGen.create(OtherService.class);

        onNextArticle = new Action0() {
            @Override
            public void call() {
                getAdapter().clear();
                getAdapter().notifyItemRemoved(0);
                loadRandomArticle();
            }
        };
    }

    @Override
    protected void afterComponentsBound() {
        getRecyclerView().setBackgroundColor(getContext().getResources().getColor(R.color.gray_light_translucent));
//        getAdapter().disableLoadMore();//TODO 修改
        changePbColor(R.color.colorPrimary);
        getRecyclerView().setItemAnimator(new FadeInAnimator());
        getSwipeLayout().setDistanceToTriggerSync(500);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        getAdapter().clear();
        getAdapter().notifyItemRemoved(0);
        loadDailyArticle();
    }

    private void loadDailyArticle() {
        service.getDailyArticle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DailyArticle>() {
                    @Override
                    public void call(DailyArticle article) {
                        getAdapter().add(new DailyArticleItemViewModel(article).onAction(onNextArticle));
                        getAdapter().notifyItemInserted(0);
                        hideRefreshing();
                    }
                }, new ActionHttpError() {
                    @Override
                    protected void onError() {
//                        getAdapter().finishLoadMore(false);//TODO 修改
                    }
                });
    }

    private void loadRandomArticle() {
        service.getRandomArticle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DailyArticle>() {
                    @Override
                    public void call(DailyArticle article) {
                        getAdapter().add(new DailyArticleItemViewModel(article).onAction(onNextArticle));
                        getAdapter().notifyItemInserted(0);
                    }
                }, new ActionHttpError() {
                    @Override
                    protected void onError() {

                    }
                });
    }

}
