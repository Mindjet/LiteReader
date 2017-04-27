package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.AdapterInterface;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemMovieCollectBinding;
import io.mindjet.litereader.model.item.DoubanMovieItem;
import io.mindjet.litereader.util.CollectionManager;
import rx.functions.Action1;
import rx.functions.Action3;

/**
 * 收藏列表中豆瓣电影 item view model
 * <p>
 * Created by Mindjet on 2017/4/27.
 */

public class MovieCollectItemViewModel extends BaseViewModel<ViewInterface<ItemMovieCollectBinding>> {

    private DoubanMovieItem item;
    private Action1<DoubanMovieItem> onClick;
    private Action3<String, String, Integer> onUncollect;

    public MovieCollectItemViewModel(DoubanMovieItem item, Action1<DoubanMovieItem> onClick, Action3<String, String, Integer> onUncollect) {
        this.item = item;
        this.onClick = onClick;
        this.onUncollect = onUncollect;
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
        if (onUncollect != null) {
            int pos = ((AdapterInterface) getSelfView()).getViewHolder().getLayoutPosition();
            onUncollect.call(item.id, CollectionManager.COLLECTION_TYPE_DOUBAN_MOVIE, pos);
        }
    }

    @Override
    public void onViewAttached(View view) {

    }
}
