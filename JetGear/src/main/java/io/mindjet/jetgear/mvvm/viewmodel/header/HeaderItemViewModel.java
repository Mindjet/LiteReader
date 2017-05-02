package io.mindjet.jetgear.mvvm.viewmodel.header;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.view.View;

import java.lang.ref.WeakReference;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.ItemHeaderBinding;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;

/**
 * Created by Jet on 2/21/17.
 */

public class HeaderItemViewModel extends BaseViewModel<ViewInterface<ItemHeaderBinding>> {

    @DrawableRes
    private int icon = 0;
    @ColorRes
    private int textColor = R.color.colorPrimary;
    @DimenRes
    private int textSize = R.dimen.header_text_size;
    private String text;
    private IHeaderItemCallback callback;
    private boolean clickable = true;
    private String font;

    @Override
    public void onViewAttached(View view) {

    }

    public int getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }

    public float getTextSize() {
        return getContext().getResources().getDimension(textSize);
    }

    public int getTextColor() {
        return getContext().getResources().getColor(textColor);
    }

    public boolean isClickable() {
        return clickable;
    }

    public View.OnClickListener getClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) callback.call();
            }
        };
    }

    public Typeface getTypeFace() {
        return font == null ? Typeface.DEFAULT : Typeface.createFromAsset(getContext().getAssets(), "fonts/" + this.font);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_header;
    }

    public HeaderItemViewModel icon(@DrawableRes int icon) {
        this.icon = icon;
        return this;
    }

    public HeaderItemViewModel font(String font) {
        this.font = font;
        return this;
    }

    public HeaderItemViewModel text(String text) {
        this.text = text;
        return this;
    }

    public HeaderItemViewModel textColor(@ColorRes int textColor) {
        this.textColor = textColor;
        return this;
    }

    public HeaderItemViewModel textSize(@DimenRes int testSize) {
        this.textSize = testSize;
        return this;
    }

    public HeaderItemViewModel callback(IHeaderItemCallback callback) {
        this.callback = callback;
        return this;
    }

    public HeaderItemViewModel clickable(boolean clickable) {
        this.clickable = clickable;
        return this;
    }

    public static class TitleItemViewModel extends HeaderItemViewModel {

        public TitleItemViewModel(String title) {
            text(title);
            textColor(R.color.white);
            textSize(R.dimen.header_text_size_large);
            clickable(false);
        }

    }

    public static class BackItemViewModel extends HeaderItemViewModel {

        private WeakReference<Activity> activity;

        public BackItemViewModel(Activity activity) {
            this.activity = new WeakReference<>(activity);
            init();
        }

        private void init() {
            icon(R.drawable.ic_back);
            callback(new IHeaderItemCallback() {
                @Override
                public void call() {
                    activity.get().finish();
                }
            });
        }

    }

}
