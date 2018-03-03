package io.mindjet.litereader.viewmodel.list;

import java.util.List;

import io.mindjet.jetgear.mvvm.viewmodel.list.SwipeRecyclerViewModel;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.litereader.R;
import io.mindjet.litereader.http.SimpleHttpSubscriber;
import io.mindjet.litereader.http.ThreadDispatcher;
import io.mindjet.litereader.model.item.one.Article;
import io.mindjet.litereader.model.list.OneDataList;
import io.mindjet.litereader.service.OneService;
import io.mindjet.litereader.viewmodel.item.OneArticleItemViewModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * ONE文章 列表 view model
 * <p>
 * Created by Mindjet on 5/5/17.
 */

public class OneArticleListViewModel extends SwipeRecyclerViewModel {

    private OneService service;

    @Override
    protected void afterViewAttached() {
        service = ServiceGen.create(OneService.class);
    }

    @Override
    protected void afterComponentsBound() {
        getAdapter().disableLoadMore();
        changePbColor(R.color.colorPrimary);
        getRecyclerView().setBackgroundColor(getContext().getResources().getColor(R.color.gray_light_translucent));
        getSwipeLayout().setDistanceToTriggerSync(500);
    }

    @Override
    public void onRefresh() {
        getArticleList();
    }

    private void getArticleList() {
        service.getArticleList()
                .compose(new ThreadDispatcher<OneDataList<Article>>())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<OneDataList<Article>, Observable<Article>>() {
                    @Override
                    public Observable<Article> call(OneDataList<Article> list) {
                        return Observable.from(list.data);
                    }
                })
                .map(new Func1<Article, OneArticleItemViewModel>() {
                    @Override
                    public OneArticleItemViewModel call(Article article) {
                        return new OneArticleItemViewModel(article);
                    }
                })
                .toList()
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        hideRefreshing();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleHttpSubscriber<List<OneArticleItemViewModel>>() {
                    @Override
                    public void onNext(List<OneArticleItemViewModel> viewModelList) {
                        getAdapter().clear();
                        getAdapter().notifyDataSetChanged();

                        getAdapter().addAll(viewModelList);
                        getAdapter().notifyItemRangeInserted(0, viewModelList.size());
                    }
                });
    }

}
