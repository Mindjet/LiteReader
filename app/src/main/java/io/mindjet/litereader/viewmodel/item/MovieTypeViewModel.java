package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemMovieTypeBinding;

/**
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

    @Override
    public void onViewAttached(View view) {
        getSelfView().getBinding().tv.setText(content);
    }
}
