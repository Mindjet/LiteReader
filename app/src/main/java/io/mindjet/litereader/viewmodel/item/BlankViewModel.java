package io.mindjet.litereader.viewmodel.item;

import android.support.annotation.DimenRes;
import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemBlankBinding;

/**
 * 占位 view model
 * <p>
 * Created by Jet on 3/14/17.
 */

public class BlankViewModel extends BaseViewModel<ViewInterface<ItemBlankBinding>> {

    @DimenRes
    private int height;

    public BlankViewModel(@DimenRes int height) {
        this.height = height;
    }

    public int getHeight() {
        return (int) getContext().getResources().getDimension(height);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_blank;
    }

    @Override
    public void onViewAttached(View view) {

    }
}
