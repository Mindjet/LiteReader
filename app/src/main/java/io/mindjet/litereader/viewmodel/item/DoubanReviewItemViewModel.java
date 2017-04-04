package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemDoubanReviewBinding;
import io.mindjet.litereader.model.item.douban.Review;
import io.mindjet.litereader.ui.dialog.MovieReviewDialog;

/**
 * Created by Jet on 3/22/17.
 */

public class DoubanReviewItemViewModel extends BaseViewModel<ViewInterface<ItemDoubanReviewBinding>> {

    private Review review;

    public DoubanReviewItemViewModel(Review review) {
        this.review = review;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_douban_review;
    }

    public Review getReview() {
        return review;
    }

    public View.OnClickListener getClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                new MovieReviewDialog(getContext(), review.content).show();
            }
        };
    }

    @Override
    public void onViewAttached(View view) {

    }
}
