package io.mindjet.litereader.viewmodel.list;

import android.support.v7.widget.GridLayoutManager;
import android.view.ViewGroup;

import java.util.List;

import io.mindjet.jetgear.databinding.IncludeHeaderSwipeLayoutBinding;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityCompatInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderItemViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.integrated.HeaderSwipeLayoutViewModel;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.jetgear.reactivex.rxbus.RxBus;
import io.mindjet.litereader.R;
import io.mindjet.litereader.http.SimpleHttpSubscriber;
import io.mindjet.litereader.http.ThreadDispatcher;
import io.mindjet.litereader.model.item.douban.DetailStill;
import io.mindjet.litereader.model.list.DoubanStillList;
import io.mindjet.litereader.service.DoubanService;
import io.mindjet.litereader.viewmodel.item.DoubanStillItemViewModel;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 剧照列表 view model
 * <p>
 * Created by Jet on 5/3/17.
 */

public class DoubanStillListViewModel extends HeaderSwipeLayoutViewModel<ActivityCompatInterface<IncludeHeaderSwipeLayoutBinding>> {

    private int start = 0;

    private String movieId;
    private String title;

    private DoubanService service;
    private Subscription stillSub;

    public DoubanStillListViewModel(String movieId, String title) {
        this.movieId = movieId;
        this.title = title;
    }

    @Override
    protected void afterViewAttached() {
        service = ServiceGen.create(DoubanService.class);
    }

    @Override
    protected void afterComponentsBound() {
        changePbColor(R.color.colorPrimary);
        getRecyclerView().setLayoutManager(new GridLayoutManager(getContext(), 3));
    }

    @Override
    protected void initHeader(ViewGroup container) {
        HeaderViewModel headerViewModel = new HeaderViewModel.Builder()
                .sink(true)
                .leftViewModel(new HeaderItemViewModel.BackItemViewModel(getSelfView().getCompatActivity()).icon(R.drawable.ic_arrow_left))
                .leftViewModel(new HeaderItemViewModel.TitleItemViewModel(title))
                .background(R.color.colorPrimary)
                .build();
        ViewModelBinder.bind(container, headerViewModel);
    }

    @Override
    protected void onRefresh() {
        start = 0;
        getStillList();
    }

    @Override
    protected void onLoadMore() {
        getStillList();
    }

    private void getStillList() {
        stillSub = service.getStillList(movieId, start)
                .compose(new ThreadDispatcher<DoubanStillList>())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<DoubanStillList, Observable<DetailStill>>() {
                    @Override
                    public Observable<DetailStill> call(DoubanStillList list) {
                        return Observable.from(list.photos);
                    }
                })
                .map(new Func1<DetailStill, DoubanStillItemViewModel>() {
                    @Override
                    public DoubanStillItemViewModel call(DetailStill detailStill) {
                        return new DoubanStillItemViewModel(detailStill);
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<List<DoubanStillItemViewModel>>() {
                    @Override
                    public void call(List<DoubanStillItemViewModel> list) {
                        getAdapter().onFinishLoadMore(false);
                        getSwipeLayoutViewModel().hideRefreshing();
                    }
                })
                .subscribe(new SimpleHttpSubscriber<List<DoubanStillItemViewModel>>() {
                    @Override
                    public void onNext(List<DoubanStillItemViewModel> list) {
                        if (start == 0) {
                            getAdapter().clear();
                            getAdapter().notifyDataSetChanged();
                        }
                        getAdapter().addAll(list);
                        getAdapter().notifyItemRangeInserted(start, list.size());
                        start += list.size();
                        getAdapter().onFinishLoadMore(list.size() == 0);
                    }
                });
    }

    @Override
    public void onDestroy() {
        RxBus.unSubscribe(stillSub);
    }
}

