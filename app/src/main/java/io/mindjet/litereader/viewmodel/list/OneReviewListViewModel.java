package io.mindjet.litereader.viewmodel.list;

import java.util.List;

import io.mindjet.jetgear.mvvm.viewmodel.list.SwipeRecyclerViewModel;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.jetgear.reactivex.rxbus.RxBus;
import io.mindjet.litereader.R;
import io.mindjet.litereader.http.SimpleHttpSubscriber;
import io.mindjet.litereader.http.ThreadDispatcher;
import io.mindjet.litereader.model.item.one.Review;
import io.mindjet.litereader.model.item.one.ReviewList;
import io.mindjet.litereader.service.OneService;
import io.mindjet.litereader.viewmodel.item.OneReviewItemViewModel;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * ONE 影评列表 view model
 * <p>
 * Created by Mindjet on 5/4/17.
 */

public class OneReviewListViewModel extends SwipeRecyclerViewModel {

    private OneService service;
    private Subscription reviewSub;

    @Override
    protected void afterViewAttached() {
        service = ServiceGen.create(OneService.class);
    }

    @Override
    protected void afterComponentsBound() {
        disableLoadMore();
        changePbColor(R.color.colorPrimary);
        getRecyclerView().setBackgroundColor(getContext().getResources().getColor(R.color.gray_light_translucent));
        getSwipeLayout().setDistanceToTriggerSync(500);
    }

    @Override
    public void onRefresh() {
        getReviewList();
    }

    private void getReviewList() {
        reviewSub = service.getReviewList()
                .compose(new ThreadDispatcher<ReviewList>())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<ReviewList, Observable<Review>>() {
                    @Override
                    public Observable<Review> call(ReviewList reviewList) {
                        return Observable.from(reviewList.data);
                    }
                })
                .map(new Func1<Review, OneReviewItemViewModel>() {
                    @Override
                    public OneReviewItemViewModel call(Review review) {
                        return new OneReviewItemViewModel(review);
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        hideRefreshing();
                    }
                })
                .subscribe(new SimpleHttpSubscriber<List<OneReviewItemViewModel>>() {
                    @Override
                    public void onNext(List<OneReviewItemViewModel> vmList) {
                        int size = getAdapter().size();
                        getAdapter().clear();
                        getAdapter().notifyItemRangeRemoved(0, size);
                        getAdapter().addAll(vmList);
                        getAdapter().notifyItemRangeInserted(0, vmList.size());
                    }
                });
    }

    @Override
    public void onDestroy() {
        RxBus.unSubscribe(reviewSub);
    }
}
