package io.mindjet.litereader.viewmodel.list;

import android.support.v7.widget.GridLayoutManager;

import java.util.List;

import io.mindjet.jetgear.mvvm.viewmodel.list.SwipeRecyclerViewModel;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.litereader.R;
import io.mindjet.litereader.model.item.DoubanMovieItem;
import io.mindjet.litereader.model.list.DoubanMovieList;
import io.mindjet.litereader.reactivex.ActionHttpError;
import io.mindjet.litereader.service.DoubanService;
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

    @Override
    protected void afterViewAttached() {
        service = ServiceGen.create(DoubanService.class);
    }

    @Override
    protected void afterComponentsBound() {
        getRecyclerView().setBackgroundColor(getContext().getResources().getColor(R.color.gray_light_translucent));
        getRecyclerView().setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        service.getMovieList(start, perPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DoubanMovieList>() {
                    @Override
                    public void call(DoubanMovieList movieList) {
                        addMovieList(movieList.movies);
                        start += perPage;
                    }
                }, new ActionHttpError() {
                    @Override
                    protected void onError() {
                        getAdapter().finishLoadMore(false);
                    }
                });
    }

    private void addMovieList(List<DoubanMovieItem> movies) {
        if (movies.size() != 0) {
            for (DoubanMovieItem movie : movies) {
                getAdapter().add(new DoubanMovieItemViewModel(movie));
            }
            getAdapter().updateAndContinue();
        } else {
            getAdapter().finishLoadMore(true);
        }
    }

}
