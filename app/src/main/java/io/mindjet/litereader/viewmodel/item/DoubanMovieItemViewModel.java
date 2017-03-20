package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemDoubanMovieBinding;
import io.mindjet.litereader.model.item.DoubanMovieItem;
import io.mindjet.litereader.ui.dialog.MovieItemDialog;

/**
 * Created by Jet on 3/16/17.
 */

public class DoubanMovieItemViewModel extends BaseViewModel<ViewInterface<ItemDoubanMovieBinding>> {

    private DoubanMovieItem item;

    public DoubanMovieItemViewModel(DoubanMovieItem item) {
        this.item = item;
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

    public View.OnLongClickListener getLongClickListener() {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MovieItemDialog dialog = new MovieItemDialog(getContext(), item);
                dialog.show();
                return true;
            }
        };
    }
}
