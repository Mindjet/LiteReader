package io.mindjet.litereader.viewmodel.detail.one;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemOneReviewImageBinding;
import io.mindjet.litereader.model.item.one.Review;

/**
 * ONE 影评详情 顶部图片 view model
 * <p>
 * Created by Mindjet on 5/4/17.
 */

public class OneReviewImageViewModel extends BaseViewModel<ViewInterface<ItemOneReviewImageBinding>> {

    private Review review;

    public OneReviewImageViewModel(Review review) {
        this.review = review;
    }

    public Review getReview() {
        return review;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_one_review_image;
    }

    @Override
    public void onViewAttached(View view) {

    }
}
