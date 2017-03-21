package io.mindjet.litereader.ui.activity;

import android.content.Context;
import android.content.Intent;

import io.mindjet.jetgear.mvvm.viewmodel.activity.ViewModelCompatActivity;
import io.mindjet.litereader.viewmodel.detail.DoubanMovieDetailViewModel;

/**
 * Created by Jet on 3/17/17.
 */

public class DoubanMovieDetailActivity extends ViewModelCompatActivity<DoubanMovieDetailViewModel> {

    public static Intent intentFor(Context context) {
        return new Intent(context, DoubanMovieDetailActivity.class);
    }

    @Override
    public void onViewAttached(DoubanMovieDetailViewModel viewModel) {

    }

    @Override
    public DoubanMovieDetailViewModel giveMeViewModel() {
        return new DoubanMovieDetailViewModel();
    }
}
