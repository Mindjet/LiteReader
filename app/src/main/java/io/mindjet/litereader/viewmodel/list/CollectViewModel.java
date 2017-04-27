package io.mindjet.litereader.viewmodel.list;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.ViewGroup;

import java.util.List;

import io.mindjet.jetgear.databinding.IncludeHeaderRecyclerBinding;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityCompatInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderItemViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.integrated.HeaderRecyclerViewModel;
import io.mindjet.litereader.R;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.http.SimpleHttpSubscriber;
import io.mindjet.litereader.model.item.DoubanMovieItem;
import io.mindjet.litereader.model.item.ZhihuStoryItem;
import io.mindjet.litereader.reactivex.RxLoadingView;
import io.mindjet.litereader.ui.activity.DoubanMovieDetailActivity;
import io.mindjet.litereader.ui.activity.ZhihuStoryDetailActivity;
import io.mindjet.litereader.util.CollectionManager;
import io.mindjet.litereader.viewmodel.item.DoubanMovieItemViewModel;
import io.mindjet.litereader.viewmodel.item.MovieCollectItemViewModel;
import io.mindjet.litereader.viewmodel.item.StoryCollectItemViewModel;
import io.mindjet.litereader.viewmodel.item.ZhihuDateItemViewModel;
import io.mindjet.litereader.viewmodel.item.ZhihuStoryItemViewModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 收藏列表 view model
 * <p>
 * Created by Mindjet on 2017/4/26.
 */

public class CollectViewModel extends HeaderRecyclerViewModel<ActivityCompatInterface<IncludeHeaderRecyclerBinding>> {

    private List<MovieCollectItemViewModel> movieItems;
    private List<StoryCollectItemViewModel> storyItems;

    private Action1<DoubanMovieItem> onMovieItemClick;
    private Action1<ZhihuStoryItem> onStoryItemClick;

    @Override
    protected void afterViewAttached() {

    }

    @Override
    protected void initHeader(ViewGroup container) {
        HeaderViewModel headerViewModel = new HeaderViewModel.Builder()
                .sink(true)
                .background(R.color.colorPrimary)
                .leftViewModel(new HeaderItemViewModel.BackItemViewModel(getSelfView().getCompatActivity()).icon(R.drawable.ic_arrow_left))
                .centerViewModel(new HeaderItemViewModel.TitleItemViewModel(getString(R.string.my_collection)))
                .build();
        ViewModelBinder.bind(container, headerViewModel);
    }

    @Override
    protected void afterComponentBound() {
        getRecyclerViewModel().disableLoadMore();
        getRecyclerViewModel().getRecyclerView().setLayoutManager(new GridLayoutManager(getContext(), 3));
        ((GridLayoutManager) getRecyclerViewModel().getRecyclerView().getLayoutManager())
                .setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return getAdapter().get(position) instanceof DoubanMovieItemViewModel ? 1 : 3;
                    }
                });
        initActions();
        addItems();
    }

    private void initActions() {
        onMovieItemClick = new Action1<DoubanMovieItem>() {
            @Override
            public void call(DoubanMovieItem item) {
                Intent intent = DoubanMovieDetailActivity.intentFor(getContext());
                intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_ID, item.id);
                intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_POSTER, item.images.large);
                intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_TITLE, item.title);
                intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_MAINLAND_PUBDATE, item.mainlandPubdate);
                intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_RATING, item.rating.average + "/" + 10);
                getContext().startActivity(intent);
            }
        };
        onStoryItemClick = new Action1<ZhihuStoryItem>() {
            @Override
            public void call(ZhihuStoryItem zhihuStoryItem) {
                Intent intent = ZhihuStoryDetailActivity.intentFor(getContext());
                intent.putExtra(Constant.EXTRA_ZHIHU_STORY_ID, zhihuStoryItem.id);
                getContext().startActivity(intent);
            }
        };
    }

    private void addItems() {
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(RxLoadingView.show(getContext(), R.string.getting_collection))
                .observeOn(Schedulers.io())
                .map(new Func1<String, List<DoubanMovieItem>>() {
                    @Override
                    public List<DoubanMovieItem> call(String s) {
                        return CollectionManager.getInstance(getContext()).getDoubanMovieList();
                    }
                })
                .flatMap(new Func1<List<DoubanMovieItem>, Observable<DoubanMovieItem>>() {
                    @Override
                    public Observable<DoubanMovieItem> call(List<DoubanMovieItem> list) {
                        return Observable.from(list);
                    }
                })
                .map(new Func1<DoubanMovieItem, MovieCollectItemViewModel>() {
                    @Override
                    public MovieCollectItemViewModel call(DoubanMovieItem doubanMovieItem) {
                        return new MovieCollectItemViewModel(doubanMovieItem, onMovieItemClick);
                    }
                })
                .toList()
                .doOnNext(new Action1<List<MovieCollectItemViewModel>>() {
                    @Override
                    public void call(List<MovieCollectItemViewModel> list) {
                        movieItems = list;
                    }
                })
                .map(new Func1<List<MovieCollectItemViewModel>, List<ZhihuStoryItem>>() {
                    @Override
                    public List<ZhihuStoryItem> call(List<MovieCollectItemViewModel> doubanMovieItemViewModels) {
                        return CollectionManager.getInstance(getContext()).getZhihuStoryList();
                    }
                })
                .flatMap(new Func1<List<ZhihuStoryItem>, Observable<ZhihuStoryItem>>() {
                    @Override
                    public Observable<ZhihuStoryItem> call(List<ZhihuStoryItem> list) {
                        return Observable.from(list);
                    }
                })
                .map(new Func1<ZhihuStoryItem, StoryCollectItemViewModel>() {
                    @Override
                    public StoryCollectItemViewModel call(ZhihuStoryItem item) {
                        return new StoryCollectItemViewModel(item, onStoryItemClick);
                    }
                })
                .toList()
                .doOnNext(new Action1<List<StoryCollectItemViewModel>>() {
                    @Override
                    public void call(List<StoryCollectItemViewModel> list) {
                        storyItems = list;
                    }
                })
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .doOnUnsubscribe(RxLoadingView.dismiss())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleHttpSubscriber<List<StoryCollectItemViewModel>>() {
                    @Override
                    public void onNext(List<StoryCollectItemViewModel> list) {
                        getAdapter().add(new ZhihuDateItemViewModel(R.string.column_douban_movie));
                        getAdapter().addAll(movieItems);
                        getAdapter().add(new ZhihuDateItemViewModel(R.string.column_zhihu_daily));
                        getAdapter().addAll(storyItems);
                        getAdapter().notifyItemRangeInserted(0, movieItems.size() + storyItems.size() + 2);
                    }
                });
    }

}
