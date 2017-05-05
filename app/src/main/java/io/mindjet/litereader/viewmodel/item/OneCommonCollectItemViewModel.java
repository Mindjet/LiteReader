package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.AdapterInterface;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemOneReviewCollectBinding;
import io.mindjet.litereader.model.item.one.Review;
import io.mindjet.litereader.ui.activity.OneArticleDetailActivity;
import io.mindjet.litereader.ui.activity.OneReviewDetailActivity;
import io.mindjet.litereader.util.CollectionManager;
import rx.functions.Action3;

/**
 * 收藏列表中 ONE影评 item view model
 * <p>
 * Created by Mindjet on 5/5/17.
 */

public class OneCommonCollectItemViewModel extends BaseViewModel<ViewInterface<ItemOneReviewCollectBinding>> {

    private Review review;
    private Action3<String, String, Integer> onUnCollect;

    private String id;
    private String imgUrl;
    private String title;

    //适用于OneArticle
    public OneCommonCollectItemViewModel(String id, String imgUrl, String title, Action3<String, String, Integer> onUnCollect) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.title = title;
        this.onUnCollect = onUnCollect;
    }

    //适用于OneReview
    public OneCommonCollectItemViewModel(Review review, Action3<String, String, Integer> onUnCollect) {
        this.review = review;
        this.onUnCollect = onUnCollect;
    }

    public String getImgUrl() {
        return imgUrl == null ? review.imgUrl : imgUrl;
    }

    public String getTitle() {
        return title == null ? review.title : title;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_one_review_collect;
    }

    public void onClick() {
        if (review != null) {
            getContext().startActivity(OneReviewDetailActivity.intentFor(getContext(), review));
        } else {
            getContext().startActivity(OneArticleDetailActivity.intentFor(getContext(), id, imgUrl));
        }
    }

    public void onUnCollect() {
        if (onUnCollect != null) {
            int pos = ((AdapterInterface) getSelfView()).getViewHolder().getLayoutPosition();
            onUnCollect.call(review != null ? review.id : id, review != null ? CollectionManager.COLLECTION_TYPE_ONE_REVIEW : CollectionManager.COLLECTION_TYPE_ONE_ARTICLE, pos);
        }
    }

    @Override
    public void onViewAttached(View view) {

    }
}
