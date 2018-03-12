package io.mindjet.litereader.viewmodel.detail;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.listener.ViewAttachedListener;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityCompatInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetgear.mvvm.viewmodel.list.RecyclerViewModel;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.jetutil.manager.ActivityManager;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ActivityDoubanMovieSearchBinding;
import io.mindjet.litereader.databinding.ItemDoubanMovieBinding;
import io.mindjet.litereader.http.SimpleHttpSubscriber;
import io.mindjet.litereader.http.ThreadDispatcher;
import io.mindjet.litereader.model.item.DoubanMovieItem;
import io.mindjet.litereader.model.list.DoubanMovieList;
import io.mindjet.litereader.service.DoubanService;
import io.mindjet.litereader.viewmodel.item.DoubanMovieItemViewModel;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Mindjet on 2018/3/12.
 */

public class DoubanMovieSearchViewModel extends BaseViewModel<ActivityCompatInterface<ActivityDoubanMovieSearchBinding>> {

    private RecyclerViewModel<ItemDoubanMovieBinding> mRecyclerViewModel;
    private DoubanService mService;

    @Override
    public int getLayoutId() {
        return R.layout.activity_douban_movie_search;
    }

    @Override
    public void onViewAttached(View view) {
        mService = ServiceGen.create(DoubanService.class);
        initRecyclerViewModel();
        search();
    }

    private void initRecyclerViewModel() {
        mRecyclerViewModel = new RecyclerViewModel<>(true);
        mRecyclerViewModel.setViewAttachedListener(new ViewAttachedListener() {
            @Override
            public void onViewAttached(BaseViewModel viewModel) {
                mRecyclerViewModel.getRecyclerView().setLayoutManager(new GridLayoutManager(getContext(), 3));
            }
        });
        ViewModelBinder.bind(getSelfView().getBinding().flyContainer, mRecyclerViewModel);
    }

    private void search() {
        mService.getMovieList(0, 10)
                .compose(new ThreadDispatcher<DoubanMovieList>())
                .flatMap(new Func1<DoubanMovieList, Observable<DoubanMovieItem>>() {
                    @Override
                    public Observable<DoubanMovieItem> call(DoubanMovieList doubanMovieList) {
                        return Observable.from(doubanMovieList.movies);
                    }
                })
                .map(new Func1<DoubanMovieItem, DoubanMovieItemViewModel>() {
                    @Override
                    public DoubanMovieItemViewModel call(DoubanMovieItem doubanMovieItem) {
                        return new DoubanMovieItemViewModel(doubanMovieItem);
                    }
                })
                .subscribe(new SimpleHttpSubscriber<DoubanMovieItemViewModel>() {
                    @Override
                    public void onNext(DoubanMovieItemViewModel vm) {
                        mRecyclerViewModel.getAdapter().add(vm);
                    }

                    @Override
                    public void onCompleted() {
                        mRecyclerViewModel.getAdapter().notifyDataSetChanged();
                    }
                });
    }

    public void onBack(View v) {
        ActivityManager.finishActivity();
    }

}
