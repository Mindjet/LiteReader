package io.mindjet.litereader.viewmodel.list;

import android.support.v7.widget.GridLayoutManager;

import java.util.List;

import io.mindjet.jetgear.mvvm.viewmodel.list.SwipeRecyclerViewModel;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.litereader.R;
import io.mindjet.litereader.http.SimpleHttpSubscriber;
import io.mindjet.litereader.model.item.book.Book;
import io.mindjet.litereader.model.item.book.BookList;
import io.mindjet.litereader.service.DoubanService;
import io.mindjet.litereader.viewmodel.item.DoubanBookItemViewModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 豆瓣图书列表 view model
 * <p>
 * Created by Mindjet on 5/5/17.
 */

public class DoubanBookListViewModel extends SwipeRecyclerViewModel {

    private DoubanService service;
    private int start = 0;
    private int perPage = 10;
    private String tag = "热门";

    @Override
    protected void afterViewAttached() {
        service = ServiceGen.create(DoubanService.class);
    }

    @Override
    protected void afterComponentsBound() {
        changePbColor(R.color.colorPrimary);
        getRecyclerView().setBackgroundColor(getContext().getResources().getColor(R.color.gray_light_translucent));
        getRecyclerView().setLayoutManager(new GridLayoutManager(getContext(), 3));
        getSwipeLayout().setDistanceToTriggerSync(500);
    }

    @Override
    public void onRefresh() {
        start = 0;
        getBookList();
    }

    @Override
    public void onLoadMore() {
        getBookList();
    }

    private void getBookList() {
        service.getBookList(tag, start, perPage)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<BookList, Observable<Book>>() {
                    @Override
                    public Observable<Book> call(BookList list) {
                        return Observable.from(list.books);
                    }
                })
                .map(new Func1<Book, DoubanBookItemViewModel>() {
                    @Override
                    public DoubanBookItemViewModel call(Book book) {
                        return new DoubanBookItemViewModel(book);
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
                .subscribe(new SimpleHttpSubscriber<List<DoubanBookItemViewModel>>() {
                    @Override
                    public void onNext(List<DoubanBookItemViewModel> list) {
                        setIsLoadingMore(false);
                        if (start == 0) {
                            getAdapter().clear();
                            getAdapter().notifyDataSetChanged();
                        }
                        getAdapter().addAll(list);
                        getAdapter().notifyItemRangeInserted(start, list.size());
                        start += list.size();
                        if (list.size() == 0) {
                            disableLoadMore();
                        } else {
                            enableLoadMore();
                        }
                    }
                });
    }

}
