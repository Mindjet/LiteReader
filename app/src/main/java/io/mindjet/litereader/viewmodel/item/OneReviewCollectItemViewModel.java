package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.AdapterInterface;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemOneReviewCollectBinding;
import io.mindjet.litereader.model.item.one.Review;
import io.mindjet.litereader.ui.activity.OneReviewDetailActivity;
import io.mindjet.litereader.util.CollectionManager;
import rx.functions.Action3;

/**
 * 收藏列表中 ONE影评 item view model
 * <p>
 * Created by Mindjet on 5/5/17.
 */

public class OneReviewCollectItemViewModel extends BaseViewModel<ViewInterface<ItemOneReviewCollectBinding>> {

    private Review review;
    private Action3<String, String, Integer> onUnCollect;

    public OneReviewCollectItemViewModel(Review review, Action3<String, String, Integer> onUnCollect) {
        this.review = review;
        this.onUnCollect = onUnCollect;
    }

    public Review getReview() {
        return review;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_one_review_collect;
    }

    public void onClick() {
        getContext().startActivity(OneReviewDetailActivity.intentFor(getContext(), review));
    }

    public void onUnCollect() {
        if (onUnCollect != null) {
            int pos = ((AdapterInterface) getSelfView()).getViewHolder().getLayoutPosition();
            onUnCollect.call(review.id, CollectionManager.COLLECTION_TYPE_ONE_REVIEW, pos);
        }
    }

    @Override
    public void onViewAttached(View view) {

    }
}
