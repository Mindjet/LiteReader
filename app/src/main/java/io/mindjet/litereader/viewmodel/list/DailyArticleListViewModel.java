package io.mindjet.litereader.viewmodel.list;

import io.mindjet.jetgear.mvvm.viewmodel.list.SwipeRecyclerViewModel;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.litereader.R;
import io.mindjet.litereader.http.SimpleHttpSubscriber;
import io.mindjet.litereader.http.ThreadDispatcher;
import io.mindjet.litereader.model.list.DailyArticle;
import io.mindjet.litereader.service.OtherService;
import io.mindjet.litereader.viewmodel.item.DailyArticleItemViewModel;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import rx.functions.Action0;

/**
 * 每日散文 view model
 * <p>
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
        changePbColor(R.color.colorPrimary);
        getRecyclerView().setItemAnimator(new FadeInAnimator());
        getSwipeLayout().setDistanceToTriggerSync(500);
    }

    @Override
    public void onRefresh() {
        getAdapter().clear();
        getAdapter().notifyItemRemoved(0);
        loadDailyArticle();
    }

    private void loadDailyArticle() {
        service.getDailyArticle()
                .compose(new ThreadDispatcher<DailyArticle>())
                .subscribe(new SimpleHttpSubscriber<DailyArticle>() {
                    @Override
                    public void onNext(DailyArticle article) {
                        getAdapter().add(new DailyArticleItemViewModel(article).onAction(onNextArticle));
                        getAdapter().notifyItemInserted(0);
                    }

                    @Override
                    public void onCompleted() {
                        hideRefreshing();
                    }
                });
    }

    private void loadRandomArticle() {
        service.getRandomArticle()
                .compose(new ThreadDispatcher<DailyArticle>())
                .subscribe(new SimpleHttpSubscriber<DailyArticle>() {
                    @Override
                    public void onNext(DailyArticle article) {
                        getAdapter().add(new DailyArticleItemViewModel(article).onAction(onNextArticle));
                        getAdapter().notifyItemInserted(0);
                    }
                });
    }

}
