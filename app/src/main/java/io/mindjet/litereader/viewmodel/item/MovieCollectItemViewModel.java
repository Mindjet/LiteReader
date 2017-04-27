package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemMovieCollectBinding;
import io.mindjet.litereader.model.item.DoubanMovieItem;
import rx.functions.Action1;

/**
 * 收藏列表中豆瓣电影 item view model
 * <p>
 * Created by Mindjet on 2017/4/27.
 */

public class MovieCollectItemViewModel extends BaseViewModel<ViewInterface<ItemMovieCollectBinding>> {

    private DoubanMovieItem item;
    private Action1<DoubanMovieItem> onClick;

    public MovieCollectItemViewModel(DoubanMovieItem item, Action1<DoubanMovieItem> onClick) {
        this.item = item;
        this.onClick = onClick;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_movie_collect;
    }

    public DoubanMovieItem getItem() {
        return item;
    }

    public String getPoster() {
        return item.images.large;
    }

    public String getRating() {
        return getContext().getResources().getString(R.string.douban_rating, item.rating.average);
    }

    public String getPubDate() {
        return getContext().getResources().getString(R.string.pubdate, item.mainlandPubdate);
    }

    public void onClick() {
        if (onClick != null) onClick.call(item);
    }

    public void onUncollect() {

    }

    @Override
    public void onViewAttached(View view) {

    }
}
