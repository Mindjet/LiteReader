package io.mindjet.jetgear.mvvm.viewmodel.item;

import android.view.View;

import java.util.UUID;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.ItemImageTextCardviewBinding;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import rx.functions.Action0;

/**
 * Created by Mindjet on 2017/3/6.
 */

public class ImageTextCardViewModel extends BaseViewModel<ViewInterface<ItemImageTextCardviewBinding>> {

    private String text = UUID.randomUUID().toString();
    private String imageUrl = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2077732093,2100397646&fm=21&gp=0.jpg";
    private Action0 action;

    public String getText() {
        return text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ImageTextCardViewModel action(Action0 action) {
        this.action = action;
        return this;
    }

    @Override
    public void onViewAttached(View view) {

    }

    public View.OnClickListener getClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (action != null) action.call();
            }
        };
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_image_text_cardview;
    }
}
