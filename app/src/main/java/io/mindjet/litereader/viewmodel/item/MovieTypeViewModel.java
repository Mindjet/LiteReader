package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemMovieTypeBinding;

/**
 * 电影类型（标签） view model
 * <p>
 * Created by Jet on 3/20/17.
 */

public class MovieTypeViewModel extends BaseViewModel<ViewInterface<ItemMovieTypeBinding>> {

    private String content;

    public MovieTypeViewModel(String content) {
        this.content = content;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_movie_type;
    }

    public String getContent() {
        return content;
    }

    @Override
    public void onViewAttached(View view) {

    }
}
