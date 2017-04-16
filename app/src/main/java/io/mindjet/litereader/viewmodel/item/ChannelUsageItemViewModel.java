package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemChannelUsageBinding;

/**
 * 频道订阅说明 item view movel
 * <p>
 * Created by Mindjet on 2017/4/16.
 */

public class ChannelUsageItemViewModel extends BaseViewModel<ViewInterface<ItemChannelUsageBinding>> {
    @Override
    public int getLayoutId() {
        return R.layout.item_channel_usage;
    }

    @Override
    public void onViewAttached(View view) {

    }
}
