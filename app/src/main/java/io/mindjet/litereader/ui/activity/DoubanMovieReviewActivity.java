package io.mindjet.litereader.ui.activity;

import android.content.Context;
import android.content.Intent;

import io.mindjet.jetgear.mvvm.viewmodel.activity.ViewModelCompatActivity;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.model.item.douban.Review;
import io.mindjet.litereader.viewmodel.detail.DoubanMovieReviewViewModel;

/**
 * Created by Mindjet on 2017/4/6.
 */

public class DoubanMovieReviewActivity extends ViewModelCompatActivity<DoubanMovieReviewViewModel> {

    public static Intent intentFor(Context context) {
        return new Intent(context, DoubanMovieReviewActivity.class);
    }

    @Override
    public void onViewAttached(DoubanMovieReviewViewModel viewModel) {

    }

    @Override
    public DoubanMovieReviewViewModel giveMeViewModel() {
        Review review = (Review) getIntent().getExtras().get(Constant.EXTRA_DOUBAN_MOVIE_REVIEW);
        String title = getIntent().getStringExtra(Constant.EXTRA_DOUBAN_MOVIE_TITLE);
        return new DoubanMovieReviewViewModel(review, title);
    }
}
