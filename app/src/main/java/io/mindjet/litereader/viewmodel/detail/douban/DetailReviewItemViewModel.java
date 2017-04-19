package io.mindjet.litereader.viewmodel.detail.douban;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemDoubanDetailReviewBinding;
import io.mindjet.litereader.model.item.douban.Review;
import rx.functions.Action2;

/**
 * 电影详情中 评论 item view model
 * <p>
 * Created by Jet on 3/21/17.
 */

public class DetailReviewItemViewModel extends BaseViewModel<ViewInterface<ItemDoubanDetailReviewBinding>> {

    private Review review;
    private boolean lastOne;
    private Action2<Boolean, Review> onAction;

    public DetailReviewItemViewModel(Review review) {
        this(review, false);
    }

    public DetailReviewItemViewModel(Review review, boolean lastOne) {
        this.review = review;
        this.lastOne = lastOne;
    }

    public DetailReviewItemViewModel onAction(Action2<Boolean, Review> onAction) {
        this.onAction = onAction;
        return this;
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

    public void onClick() {
        if (onAction != null) {
            onAction.call(lastOne, review);
        }
    }

    @Override
    public void onViewAttached(View view) {

    }
}
