package io.mindjet.litereader.ui.activity;

import android.content.Context;
import android.content.Intent;

import io.mindjet.jetgear.mvvm.viewmodel.activity.ViewModelCompatActivity;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.model.item.one.Review;
import io.mindjet.litereader.viewmodel.detail.OneReviewDetailViewModel;

/**
 * ONE 影评详情 activity
 * <p>
 * Created by Jet on 5/4/17.
 */

public class OneReviewDetailActivity extends ViewModelCompatActivity<OneReviewDetailViewModel> {

    public static Intent intentFor(Context context, Review review) {
        Intent intent = new Intent(context, OneReviewDetailActivity.class);
        intent.putExtra(Constant.EXTRA_ONE_REVIEW, review);
        return intent;
    }

    @Override
    public void onViewAttached(OneReviewDetailViewModel viewModel) {

    }

    @Override
    public OneReviewDetailViewModel giveMeViewModel() {
        Review review = (Review) getIntent().getSerializableExtra(Constant.EXTRA_ONE_REVIEW);
        return new OneReviewDetailViewModel(review);
    }
}
