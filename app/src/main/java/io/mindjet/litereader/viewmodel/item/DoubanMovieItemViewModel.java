package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemDoubanMovieBinding;
import io.mindjet.litereader.model.item.DoubanMovieItem;
import io.mindjet.litereader.ui.dialog.MovieItemDialog;
import rx.functions.Action1;

/**
 * 豆瓣电影 item view model
 * <p>
 * Created by Mindjet on 3/16/17.
 */

public class DoubanMovieItemViewModel extends BaseViewModel<ViewInterface<ItemDoubanMovieBinding>> {

    private DoubanMovieItem item;
    private Action1<DoubanMovieItem> onAction;

    public DoubanMovieItemViewModel(DoubanMovieItem item) {
        this.item = item;
    }

    public DoubanMovieItemViewModel onAction(Action1<DoubanMovieItem> onAction) {
        this.onAction = onAction;
        return this;
    }

    public String getTitle() {
        return item.title;
    }

    public String getPoster() {
        return item.images.large;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_douban_movie;
    }

    @Override
    public void onViewAttached(View view) {

    }

    public View.OnClickListener getClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAction != null) {
                    onAction.call(item);
                }
            }
        };
    }

    public View.OnLongClickListener getLongClickListener() {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MovieItemDialog dialog = new MovieItemDialog(getContext(), item).onAction(onAction);
                dialog.show();
                return true;
            }
        };
    }
}
