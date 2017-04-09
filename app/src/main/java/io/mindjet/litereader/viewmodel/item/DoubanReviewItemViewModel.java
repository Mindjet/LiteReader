package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemDoubanReviewBinding;
import io.mindjet.litereader.model.item.douban.Review;
import rx.functions.Action1;

/**
 * Created by Jet on 3/22/17.
 */

public class DoubanReviewItemViewModel extends BaseViewModel<ViewInterface<ItemDoubanReviewBinding>> {

    private Review review;
    private Action1<Review> onItemClick;

    public DoubanReviewItemViewModel(Review review, Action1<Review> onItemClick) {
        this.review = review;
        this.onItemClick = onItemClick;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_douban_review;
    }

    public Review getReview() {
        return review;
    }

    public void onClick() {
        if (onItemClick != null)
            onItemClick.call(review);
    }

    @Override
    public void onViewAttached(View view) {

    }
}
