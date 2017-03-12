package io.mindjet.jetgear.mvvm.viewmodel.drawer;

import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.IncludeDrawerHeaderBinding;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.jetimage.loader.ImageLoader;

/**
 * Created by Jet on 2/28/17.
 */

public class DrawerHeaderViewModel extends BaseViewModel<ViewInterface<IncludeDrawerHeaderBinding>> {

    private Builder builder;

    private DrawerHeaderViewModel(Builder builder) {
        this.builder = builder;
    }

    @Override
    public void onViewAttached(View view) {
        styleBackground();
    }

    private void styleBackground() {
        ImageView imageView = getSelfView().getBinding().ivBackground;
        if (getBackgroundDrawable() != 0) {
            imageView.setImageResource(builder.backgroundDrawable);
        } else if (!getBackground().equals("")) {
            ImageLoader.load(imageView, builder.background);
        } else {
            imageView.setBackgroundColor(getContext().getResources().getColor(builder.backgroundColor));
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_drawer_header;
    }

    public int getHeight() {
        return (int) getContext().getResources().getDimension(builder.height);
    }

    public String getContent() {
        return builder.content;
    }

    public String getIcon() {
        return builder.icon;
    }

    public String getBackground() {
        return builder.background;
    }

    public int getBackgroundColor() {
        return builder.backgroundColor;
    }

    public int getBackgroundDrawable() {
        return builder.backgroundDrawable;
    }

    public int getTextColor() {
        return getContext().getResources().getColor(builder.textColor);
    }

    public float getTextSize() {
        return getContext().getResources().getDimension(builder.textSize);
    }

    public static class Builder {
        @DimenRes
        private int height = R.dimen.drawer_header_height;
        @ColorRes
        private int textColor = R.color.white;
        @DimenRes
        private int textSize = R.dimen.common_text_size;
        @DrawableRes
        private int backgroundDrawable;
        @ColorRes
        private int backgroundColor = R.color.colorAccent;
        private String background = "";
        private String icon = "";
        private String content = "";

        public Builder height(@DimenRes int height) {
            this.height = height;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder icon(String icon) {
            this.icon = icon;
            return this;
        }

        public Builder background(String background) {
            this.background = background;
            return this;
        }

        public Builder textColor(@ColorRes int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder backgroundColor(@ColorRes int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder textSize(@DimenRes int textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder backgroundDrawable(@DrawableRes int backgroundDrawable) {
            this.backgroundDrawable = backgroundDrawable;
            return this;
        }

        public DrawerHeaderViewModel build() {
            return new DrawerHeaderViewModel(this);
        }

    }

}
