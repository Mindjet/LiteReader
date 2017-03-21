package io.mindjet.litereader.viewmodel.list;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;

import java.util.List;

import io.mindjet.jetgear.mvvm.viewmodel.list.SwipeRecyclerViewModel;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.litereader.R;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.model.item.DoubanMovieItem;
import io.mindjet.litereader.model.list.DoubanMovieList;
import io.mindjet.litereader.reactivex.ActionHttpError;
import io.mindjet.litereader.service.DoubanService;
import io.mindjet.litereader.ui.activity.DoubanMovieDetailActivity;
import io.mindjet.litereader.viewmodel.item.DoubanMovieItemViewModel;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Jet on 3/16/17.
 */

public class DoubanMovieListViewModel extends SwipeRecyclerViewModel {

    private DoubanService service;
    private int start = 0;
    private int perPage = 6;
    private Action1<DoubanMovieList> onLoadMore;
    private Action1<DoubanMovieList> onRefresh;
    private Action1<DoubanMovieItem> onItemClick;

    @Override
    protected void afterViewAttached() {
        service = ServiceGen.create(DoubanService.class);
    }

    @Override
    protected void afterComponentsBound() {
        changePbColor(R.color.colorPrimary);
        getRecyclerView().setBackgroundColor(getContext().getResources().getColor(R.color.gray_light_translucent));
        getRecyclerView().setLayoutManager(new GridLayoutManager(getContext(), 3));

        onLoadMore = new Action1<DoubanMovieList>() {
            @Override
            public void call(DoubanMovieList movieList) {
                addMovieList(movieList.movies);
                start += perPage;
                hideRefreshing();
            }
        };

        onRefresh = new Action1<DoubanMovieList>() {
            @Override
            public void call(DoubanMovieList movieList) {
                getAdapter().clear();
                getAdapter().finishLoadMore(false);
                getAdapter().notifyDataSetChanged();
                addMovieList(movieList.movies);
                start += perPage;
                hideRefreshing();
            }
        };

        onItemClick = new Action1<DoubanMovieItem>() {
            @Override
            public void call(DoubanMovieItem item) {
                Intent intent = DoubanMovieDetailActivity.intentFor(getContext());
                intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_ID, item.id);
                intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_POSTER, item.images.large);
                intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_TITLE, item.title);
                intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_MAINLAND_PUBDATE, item.mainlandPubdate);
                intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_RATING, item.rating.average + "/" + item.rating.max);
                getContext().startActivity(intent);
            }
        };
    }

    @Override
    public void onRefresh() {
        if (getAdapter().size() == 0) {
            getMovieList(onLoadMore);
        } else {
            start = 0;
            getMovieList(onRefresh);
        }
    }

    private void getMovieList(Action1<DoubanMovieList> onNext) {
        service.getMovieList(start, perPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, new ActionHttpError() {
                    @Override
                    protected void onError() {
                        getAdapter().finishLoadMore(false);
                        hideRefreshing();
                    }
                });
    }

    @Override
    public void onLoadMore() {
        getMovieList(onLoadMore);
    }

    private void addMovieList(List<DoubanMovieItem> movies) {
        if (movies.size() != 0) {
            for (DoubanMovieItem movie : movies) {
                getAdapter().add(new DoubanMovieItemViewModel(movie).onAction(onItemClick));
            }
            getAdapter().notifyDataSetChanged();
        } else {
            getAdapter().finishLoadMore(true);
        }
    }

}
