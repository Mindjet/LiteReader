package io.mindjet.litereader.viewmodel.detail.one;

import android.databinding.ObservableField;
import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.jetgear.reactivex.RxTask;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemOneReviewContentBinding;
import io.mindjet.litereader.model.item.one.Review;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * ONE 影评详情 正文 view model
 * <p>
 * Created by Mindjet on 5/4/17.
 */

public class OneReviewContentViewModel extends BaseViewModel<ViewInterface<ItemOneReviewContentBinding>> {

    private Review review;
    public ObservableField<String> content;

    public OneReviewContentViewModel(Review review) {
        this.review = review;
    }

    public Review getReview() {
        return review;
    }

    public boolean getVisible() {
        return !review.author.signature.equals("");
    }

    public boolean getAuthorVisible() {
        return review.author != null;
    }

    public boolean getLikeVisible() {
        return review.likeCount != null;
    }

    public String getLikeCount() {
        return review.likeCount == null ? "" : review.likeCount;
    }

    public String getAvatar() {
        return review.author == null ? "" : review.author.avatar;
    }

    public String getSignature() {
        return review.author == null ? "" : review.author.signature;
    }

    public String getName() {
        return review.author == null ? "" : review.author.name;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_one_review_content;
    }

    @Override
    public void onViewAttached(View view) {
        this.content = new ObservableField<>(getString(R.string.loading));
        RxTask.asyncMap(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return extractContent(review.shareUrl);
            }
        }, new Action1<String>() {
            @Override
            public void call(String html) {
                content.set(html);
            }
        });

    }

    private String extractContent(String url) {
        String content = "";
        try {
            Document doc = Jsoup.connect(url).get();
            Element element = doc.getElementsByClass("text-content").get(0);
            content = element.text();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }


}
