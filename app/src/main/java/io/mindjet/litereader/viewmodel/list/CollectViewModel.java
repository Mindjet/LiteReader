package io.mindjet.litereader.viewmodel.list;

import android.content.Intent;
import android.view.ViewGroup;

import java.util.List;

import io.mindjet.jetgear.databinding.IncludeHeaderRecyclerBinding;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityCompatInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderItemViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.integrated.HeaderRecyclerViewModel;
import io.mindjet.jetgear.reactivex.RxTask;
import io.mindjet.litereader.R;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.http.SimpleHttpSubscriber;
import io.mindjet.litereader.model.item.DoubanMovieItem;
import io.mindjet.litereader.model.item.ZhihuStoryItem;
import io.mindjet.litereader.model.item.one.Article;
import io.mindjet.litereader.model.item.one.Review;
import io.mindjet.litereader.reactivex.RxLoadingView;
import io.mindjet.litereader.ui.activity.DoubanMovieDetailActivity;
import io.mindjet.litereader.ui.activity.ZhihuStoryDetailActivity;
import io.mindjet.litereader.util.CollectionManager;
import io.mindjet.litereader.viewmodel.item.BlankViewModel;
import io.mindjet.litereader.viewmodel.item.MovieCollectItemViewModel;
import io.mindjet.litereader.viewmodel.item.OneCommonCollectItemViewModel;
import io.mindjet.litereader.viewmodel.item.StoryCollectItemViewModel;
import io.mindjet.litereader.viewmodel.item.ZhihuDateItemViewModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action3;
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
    private List<OneCommonCollectItemViewModel> oneReviewItems;
    private List<OneCommonCollectItemViewModel> oneArticleItems;

    private Action1<DoubanMovieItem> onMovieItemClick;
    private Action1<ZhihuStoryItem> onStoryItemClick;
    private Action3<String, String, Integer> onUnCollect;

    private int movieSize = 0;
    private int storySize = 0;
    private int oneReviewSize = 0;
    private int oneArticleSize = 0;

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
        onUnCollect = new Action3<String, String, Integer>() {
            @Override
            public void call(final String id, final String type, final Integer position) {
                RxTask.asyncTask(new Action0() {
                    @Override
                    public void call() {
                        CollectionManager.getInstance(getContext()).remove(id, type);
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        getAdapter().remove(position.intValue());
                        getAdapter().notifyItemRemoved(position);

                        boolean sectionEmpty = false;
                        switch (type) {
                            case CollectionManager.COLLECTION_TYPE_DOUBAN_MOVIE:
                                movieSize -= 1;
                                sectionEmpty = movieSize == 0;
                                break;
                            case CollectionManager.COLLECTION_TYPE_ZHIHU_STORY:
                                storySize -= 1;
                                sectionEmpty = storySize == 0;
                                break;
                            case CollectionManager.COLLECTION_TYPE_ONE_REVIEW:
                                oneReviewSize -= 1;
                                sectionEmpty = oneReviewSize == 0;
                                break;
                            case CollectionManager.COLLECTION_TYPE_ONE_ARTICLE:
                                oneArticleSize -= 1;
                                sectionEmpty = oneArticleSize == 0;
                                break;
                        }
                        if (sectionEmpty) {
                            getAdapter().remove(position - 1);
                            getAdapter().notifyItemRemoved(position - 1);
                        }
                    }
                });
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
                        return new MovieCollectItemViewModel(doubanMovieItem, onMovieItemClick, onUnCollect);
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
                        return new StoryCollectItemViewModel(item, onStoryItemClick, onUnCollect);
                    }
                })
                .toList()
                .doOnNext(new Action1<List<StoryCollectItemViewModel>>() {
                    @Override
                    public void call(List<StoryCollectItemViewModel> list) {
                        storyItems = list;
                    }
                })
                .map(new Func1<List<StoryCollectItemViewModel>, List<Review>>() {
                    @Override
                    public List<Review> call(List<StoryCollectItemViewModel> storyCollectItemViewModels) {
                        return CollectionManager.getInstance(getContext()).getOneReviewList();
                    }
                })
                .flatMap(new Func1<List<Review>, Observable<Review>>() {
                    @Override
                    public Observable<Review> call(List<Review> reviews) {
                        return Observable.from(reviews);
                    }
                })
                .map(new Func1<Review, OneCommonCollectItemViewModel>() {
                    @Override
                    public OneCommonCollectItemViewModel call(Review review) {
                        return new OneCommonCollectItemViewModel(review, onUnCollect);
                    }
                })
                .toList()
                .doOnNext(new Action1<List<OneCommonCollectItemViewModel>>() {
                    @Override
                    public void call(List<OneCommonCollectItemViewModel> list) {
                        oneReviewItems = list;
                    }
                })
                .map(new Func1<List<OneCommonCollectItemViewModel>, List<Article>>() {
                    @Override
                    public List<Article> call(List<OneCommonCollectItemViewModel> vms) {
                        return CollectionManager.getInstance(getContext()).getOneArticleList();
                    }
                })
                .flatMap(new Func1<List<Article>, Observable<Article>>() {
                    @Override
                    public Observable<Article> call(List<Article> articles) {
                        return Observable.from(articles);
                    }
                })
                .map(new Func1<Article, OneCommonCollectItemViewModel>() {
                    @Override
                    public OneCommonCollectItemViewModel call(Article article) {
                        return new OneCommonCollectItemViewModel(article.id, article.imgUrl, article.title, onUnCollect);
                    }
                })
                .toList()
                .doOnNext(new Action1<List<OneCommonCollectItemViewModel>>() {
                    @Override
                    public void call(List<OneCommonCollectItemViewModel> viewModels) {
                        oneArticleItems = viewModels;
                    }
                })
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .doOnUnsubscribe(RxLoadingView.dismiss())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleHttpSubscriber<List<OneCommonCollectItemViewModel>>() {
                    @Override
                    public void onNext(List<OneCommonCollectItemViewModel> list) {
                        int sum = 0;
                        if (movieItems.size() != 0) {
                            getAdapter().add(new ZhihuDateItemViewModel(R.string.column_douban_movie));
                            sum++;
                        }
                        getAdapter().addAll(movieItems);
                        sum += movieItems.size();
                        if (storyItems.size() != 0) {
                            getAdapter().add(new ZhihuDateItemViewModel(R.string.column_zhihu_daily));
                            sum++;
                        }
                        getAdapter().addAll(storyItems);
                        sum += storyItems.size();
                        if (oneReviewItems.size() != 0) {
                            getAdapter().add(new ZhihuDateItemViewModel(R.string.column_one_review));
                            sum++;
                        }
                        getAdapter().addAll(oneReviewItems);
                        sum += oneReviewItems.size();
                        if (oneArticleItems.size() != 0) {
                            getAdapter().add(new ZhihuDateItemViewModel(R.string.column_one_article));
                            sum++;
                        }
                        getAdapter().addAll(oneArticleItems);
                        sum += oneArticleItems.size();

                        getAdapter().add(new BlankViewModel(R.dimen.common_gap_medium));
                        sum++;
                        movieSize = movieItems.size();
                        storySize = storyItems.size();
                        oneReviewSize = oneReviewItems.size();
                        oneArticleSize = oneArticleItems.size();
                        getAdapter().notifyItemRangeInserted(0, sum);
                    }
                });
    }

}
