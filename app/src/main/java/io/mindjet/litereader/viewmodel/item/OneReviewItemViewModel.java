package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemOneReviewBinding;
import io.mindjet.litereader.model.item.one.Review;
import io.mindjet.litereader.ui.activity.OneReviewDetailActivity;

/**
 * ONE 影评 item view model
 * <p>
 * Created by Jet on 5/4/17.
 */

public class OneReviewItemViewModel extends BaseViewModel<ViewInterface<ItemOneReviewBinding>> {

    private Review review;

    public OneReviewItemViewModel(Review review) {
        this.review = review;
    }

    public Review getReview() {
        return review;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_one_review;
    }

    @Override
    public void onViewAttached(View view) {

    }

    public void onClick() {
        getContext().startActivity(OneReviewDetailActivity.intentFor(getContext(), review));
    }

}
