package io.mindjet.litereader.viewmodel.detail.one;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemOneReviewContentBinding;
import io.mindjet.litereader.model.item.one.Review;

/**
 * ONE 影评详情 正文 view model
 * <p>
 * Created by Mindjet on 5/4/17.
 */

// TODO: 5/4/17 正文部分解析源代码
public class OneReviewContentViewModel extends BaseViewModel<ViewInterface<ItemOneReviewContentBinding>> {

    private Review review;

    public OneReviewContentViewModel(Review review) {
        this.review = review;
    }

    public Review getReview() {
        return review;
    }

    public boolean getVisible() {
        return !review.author.signature.equals("");
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_one_review_content;
    }

    @Override
    public void onViewAttached(View view) {

    }
}
