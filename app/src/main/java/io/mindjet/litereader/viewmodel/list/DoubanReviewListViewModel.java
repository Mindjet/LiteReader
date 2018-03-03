package io.mindjet.litereader.viewmodel.list;

import android.content.Intent;

import java.util.List;

import io.mindjet.jetgear.mvvm.viewmodel.list.SwipeRecyclerViewModel;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.litereader.R;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.http.ThreadDispatcher;
import io.mindjet.litereader.model.item.douban.Review;
import io.mindjet.litereader.model.list.DoubanReviewList;
import io.mindjet.litereader.reactivex.ActionHttpError;
import io.mindjet.litereader.service.DoubanService;
import io.mindjet.litereader.ui.activity.DoubanMovieReviewActivity;
import io.mindjet.litereader.viewmodel.item.DoubanReviewItemViewModel;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 长影评列表 view model
 * <p>
 * Created by Jet on 3/22/17.
 */

public class DoubanReviewListViewModel extends SwipeRecyclerViewModel {

    private String id;
    private int start = 0;
    private int perPage = 10;
    private DoubanService service;

    private Action1<List<Review>> onLoadMore;
    private Action1<List<Review>> onRefresh;
    private Action1<Review> onItemClick;

    public DoubanReviewListViewModel(String id) {
        this.id = id;
    }

    @Override
    protected void afterViewAttached() {
        service = ServiceGen.create(DoubanService.class);
    }

    @Override
    protected void afterComponentsBound() {
        changePbColor(R.color.colorPrimary);
        getRecyclerView().setBackgroundColor(getContext().getResources().getColor(R.color.gray_light_translucent));

        onLoadMore = new Action1<List<Review>>() {
            @Override
            public void call(List<Review> reviews) {
                addItems(reviews);
                start += perPage;
            }
        };

        onRefresh = new Action1<List<Review>>() {
            @Override
            public void call(List<Review> reviews) {
                getAdapter().clear();
                addItems(reviews);
                start += perPage;
                hideRefreshing();
            }
        };

        onItemClick = new Action1<Review>() {
            @Override
            public void call(Review review) {
                Intent intent = DoubanMovieReviewActivity.intentFor(getContext());
                intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_REVIEW, review);
                getContext().startActivity(intent);
            }
        };

    }

    @Override
    public void onRefresh() {
        start = 0;
        getReviewList(onRefresh);
    }

    @Override
    public void onLoadMore() {
        getReviewList(onLoadMore);
    }

    private void getReviewList(final Action1<List<Review>> onNext) {
        service.getReviewList(id, start, perPage)
                .compose(new ThreadDispatcher<DoubanReviewList>())
                .map(new Func1<DoubanReviewList, List<Review>>() {
                    @Override
                    public List<Review> call(DoubanReviewList list) {
                        return list.reviews;
                    }
                })
                .subscribe(onNext, new ActionHttpError() {
                    @Override
                    protected void onError() {
                        hideRefreshing();
                        getAdapter().onFinishLoadMore(false);
                    }
                });
    }

    private void addItems(List<Review> reviews) {
        if (reviews.size() != 0) {      //只有 reviews 不为空时才能调用 notifyDataSetChanged，不然又会触发 LoadMore 进入死循环
            for (Review review : reviews) {
                getAdapter().add(new DoubanReviewItemViewModel(review, onItemClick));
            }
            getAdapter().notifyDataSetChanged();
        }
        getAdapter().onFinishLoadMore(reviews.size() == 0);
    }


}
