package io.mindjet.litereader.ui.activity;

import android.content.Context;
import android.content.Intent;

import io.mindjet.jetgear.mvvm.viewmodel.activity.ViewModelCompatActivity;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.viewmodel.detail.DoubanMovieMoreReviewViewModel;

/**
 * Created by Jet on 3/21/17.
 */

public class DoubanMovieMoreReviewActivity extends ViewModelCompatActivity<DoubanMovieMoreReviewViewModel> {

    public static Intent intentFor(Context context) {
        return new Intent(context, DoubanMovieMoreReviewActivity.class);
    }

    @Override
    public void onViewAttached(DoubanMovieMoreReviewViewModel viewModel) {

    }

    @Override
    public DoubanMovieMoreReviewViewModel giveMeViewModel() {
        String id = getIntent().getStringExtra(Constant.EXTRA_DOUBAN_MOVIE_ID);
        String title = getIntent().getStringExtra(Constant.EXTRA_DOUBAN_MOVIE_TITLE);
        return new DoubanMovieMoreReviewViewModel(id, title);
    }
}
