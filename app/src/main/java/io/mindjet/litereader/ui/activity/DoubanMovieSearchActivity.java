package io.mindjet.litereader.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import io.mindjet.jetgear.mvvm.viewmodel.activity.ViewModelActivity;
import io.mindjet.litereader.viewmodel.detail.DoubanMovieSearchViewModel;

/**
 * Created by Mindjet on 2018/3/12.
 */

public class DoubanMovieSearchActivity extends ViewModelActivity<DoubanMovieSearchViewModel> {

    public static Intent intentFor(Context from) {
        return new Intent(from, DoubanMovieSearchActivity.class);
    }

    @Override
    public void onViewAttached(DoubanMovieSearchViewModel viewModel) {

    }

    @Override
    public DoubanMovieSearchViewModel giveMeViewModel() {
        return new DoubanMovieSearchViewModel();
    }

}
