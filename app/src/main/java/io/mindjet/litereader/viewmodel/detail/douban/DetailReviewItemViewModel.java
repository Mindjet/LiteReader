package io.mindjet.litereader.viewmodel.detail.douban;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemDoubanDetailReviewBinding;
import io.mindjet.litereader.model.item.douban.Review;

/**
 * Created by Jet on 3/21/17.
 */

public class DetailReviewItemViewModel extends BaseViewModel<ViewInterface<ItemDoubanDetailReviewBinding>> {

    private Review review;
    private boolean lastOne;

    public DetailReviewItemViewModel(Review review) {
        this(review, false);
    }

    public DetailReviewItemViewModel(Review review, boolean lastOne) {
        this.review = review;
        this.lastOne = lastOne;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_douban_detail_review;
    }

    public Review getReview() {
        return review;
    }

    public boolean isLastOne() {
        return lastOne;
    }

    @Override
    public void onViewAttached(View view) {

    }
}
