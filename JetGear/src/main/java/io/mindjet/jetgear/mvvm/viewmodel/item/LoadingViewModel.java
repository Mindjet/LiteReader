package io.mindjet.jetgear.mvvm.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.ItemLoadingBinding;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;

/**
 * Common loading view model.
 * <p>
 * Created by Mindjet on 2017/4/16.
 */

public class LoadingViewModel extends BaseViewModel<ViewInterface<ItemLoadingBinding>> {

    @Override
    public int getLayoutId() {
        return R.layout.item_loading;
    }

    @Override
    public void onViewAttached(View view) {

    }
}
