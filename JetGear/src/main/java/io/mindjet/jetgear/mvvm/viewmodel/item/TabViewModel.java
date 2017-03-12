package io.mindjet.jetgear.mvvm.viewmodel.item;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.ItemTabBinding;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetimage.loader.ImageLoader;

/**
 * Created by Jet on 3/1/17.
 */

public class TabViewModel extends BaseViewModel<ViewInterface<ItemTabBinding>> {

    private String iconUrl = "";
    @DrawableRes
    private int iconRes;
    private String text = "";
    @ColorRes
    private int background = R.color.colorPrimary;

    public TabViewModel iconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        return this;
    }

    public TabViewModel iconRes(@DrawableRes int iconRes) {
        this.iconRes = iconRes;
        return this;
    }

    public TabViewModel text(String text) {
        this.text = text;
        return this;
    }

    public TabViewModel background(@ColorRes int background) {
        this.background = background;
        return this;
    }

    public String getText() {
        return text;
    }

    public int getBackground() {
        return getContext().getResources().getColor(background);
    }

    @Override
    public void onViewAttached(View view) {
        initIcon();
    }

    private void initIcon() {
        if (!iconUrl.equals("")) {
            ImageLoader.load(getSelfView().getBinding().image, iconUrl);
        } else if (iconRes != 0) {
            getSelfView().getBinding().image.setImageResource(iconRes);
        } else {
            getSelfView().getBinding().image.setVisibility(View.GONE);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_tab;
    }

    public View getTabView(ViewGroup container) {
        ViewModelBinder.bind(container, this, false);
        return getSelfView().getBinding().getRoot();
    }

}
