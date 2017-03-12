package io.mindjet.jetgear.mvvm.viewmodel.coordinator;

import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.IncludeCollapsingImageBinding;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.jetimage.loader.ImageLoader;

/**
 * Created by Jet on 3/6/17.
 */

public class CollapsingImageViewModel extends BaseViewModel<ViewInterface<IncludeCollapsingImageBinding>> {

    @DrawableRes
    private int drawableRes;
    private String imageUrl;

    public CollapsingImageViewModel(@DrawableRes int drawableRes) {
        this.drawableRes = drawableRes;
    }

    public CollapsingImageViewModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public void onViewAttached(View view) {
        initImage(getSelfView().getBinding().image);
    }

    private void initImage(ImageView imageView) {
        if (drawableRes != 0) {
            imageView.setImageResource(drawableRes);
        } else {
            ImageLoader.load(imageView, imageUrl);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_collapsing_image;
    }
}
