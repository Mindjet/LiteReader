package io.mindjet.litereader.viewmodel.list;

import java.util.List;

import io.mindjet.jetgear.mvvm.viewmodel.list.SwipeRecyclerViewModel;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.litereader.R;
import io.mindjet.litereader.model.item.douban.Comment;
import io.mindjet.litereader.model.list.DoubanCommentList;
import io.mindjet.litereader.reactivex.ActionHttpError;
import io.mindjet.litereader.service.DoubanService;
import io.mindjet.litereader.viewmodel.item.DoubanCommentItemViewModel;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 短影评列表 view model
 * <p>
 * Created by Jet on 3/22/17.
 */

public class DoubanCommentListViewModel extends SwipeRecyclerViewModel {

    private String id;

    private DoubanService service;
    private Action1<List<Comment>> onLoadMore;
    private Action1<List<Comment>> onRefresh;

    private int start = 0;
    private int perPage = 10;

    public DoubanCommentListViewModel(String id) {
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

        onLoadMore = new Action1<List<Comment>>() {
            @Override
            public void call(List<Comment> comments) {
                setIsLoadingMore(false);
                addItems(comments);
                start += perPage;
                hideRefreshing();
            }
        };

        onRefresh = new Action1<List<Comment>>() {
            @Override
            public void call(List<Comment> comments) {
                getAdapter().clear();
                addItems(comments);
                start += perPage;
                hideRefreshing();
                enableLoadMore();
            }
        };
    }

    @Override
    public void onRefresh() {
        if (getAdapter().size() == 0) {
            getCommentList(onLoadMore);
        } else {
            start = 0;
            getCommentList(onRefresh);
        }
    }

    @Override
    public void onLoadMore() {
        getCommentList(onLoadMore);
    }

    private void getCommentList(Action1<List<Comment>> onNext) {
        service.getCommentList(id, start, perPage)
                .subscribeOn(Schedulers.io())
                .map(new Func1<DoubanCommentList, List<Comment>>() {
                    @Override
                    public List<Comment> call(DoubanCommentList list) {
                        return list.comments;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, new ActionHttpError() {
                    @Override
                    protected void onError() {
                        setIsLoadingMore(false);
                    }
                });
    }

    private void addItems(List<Comment> comments) {
        if (comments.size() != 0) {         //只有 comments 不为空时才能调用 notifyDataSetChanged，不然又会触发 LoadMore 进入死循环
            for (Comment comment : comments) {
                getAdapter().add(new DoubanCommentItemViewModel(comment));
            }
            getAdapter().notifyDataSetChanged();
        } else {
            disableLoadMore();
        }
    }

}
