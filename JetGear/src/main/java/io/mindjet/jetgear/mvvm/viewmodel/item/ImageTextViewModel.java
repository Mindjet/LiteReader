package io.mindjet.jetgear.mvvm.viewmodel.item;

import android.view.View;

import java.util.UUID;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.ItemImageTextBinding;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;

/**
 * Created by Mindjet on 2017/2/15.
 */

public class ImageTextViewModel extends BaseViewModel<ViewInterface<ItemImageTextBinding>> {

    private String imageUrl = "https://imgsa.baidu.com/forum/w%3D580/sign=9a8f6a0f9545d688a302b2ac94c27dab/ca67d5a20cf431ad929de0054c36acaf2fdd988b.jpg";
    private String title = "Mindjet";
    private String content = UUID.randomUUID().toString();

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_image_text;
    }

    @Override
    public void onViewAttached(View view) {

    }
}
