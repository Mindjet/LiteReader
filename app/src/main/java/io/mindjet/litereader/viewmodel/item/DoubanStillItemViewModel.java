package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemDoubanStillBinding;
import io.mindjet.litereader.model.item.douban.DetailStill;
import io.mindjet.litereader.ui.dialog.StillDialog;

/**
 * 剧照列表 item view model
 * <p>
 * Created by Jet on 5/3/17.
 */

public class DoubanStillItemViewModel extends BaseViewModel<ViewInterface<ItemDoubanStillBinding>> {

    private DetailStill detailStill;

    public DoubanStillItemViewModel(DetailStill detailStill) {
        this.detailStill = detailStill;
    }

    public DetailStill getStill() {
        return detailStill;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_douban_still;
    }

    @Override
    public void onViewAttached(View view) {

    }

    public void onClick() {
        new StillDialog(getContext(), detailStill).show();
    }

}
