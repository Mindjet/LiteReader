package io.mindjet.litereader.viewmodel.detail.one;

import android.databinding.Bindable;
import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.BR;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemOneReviewImageBinding;

/**
 * ONE 影评/文章详情 顶部图片 view model
 * <p>
 * Created by Mindjet on 5/4/17.
 */

public class OneCommonImageViewModel extends BaseViewModel<ViewInterface<ItemOneReviewImageBinding>> {

    private String content;
    private String imgUrl;

    public OneCommonImageViewModel(String content, String imgUrl) {
        this.content = content;
        this.imgUrl = imgUrl;
    }

    @Bindable
    public String getContent() {
        return content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void update(String content) {
        this.content = content;
        notifyPropertyChanged(BR.content);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_one_review_image;
    }

    @Override
    public void onViewAttached(View view) {

    }
}
