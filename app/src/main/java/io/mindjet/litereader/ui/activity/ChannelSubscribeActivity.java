package io.mindjet.litereader.ui.activity;

import android.content.Context;
import android.content.Intent;

import io.mindjet.jetgear.mvvm.viewmodel.activity.ViewModelCompatActivity;
import io.mindjet.litereader.viewmodel.list.ChannelSubscribeViewModel;

/**
 * 订阅频道 activity
 * <p>
 * Created by Mindjet on 2017/4/13.
 */

public class ChannelSubscribeActivity extends ViewModelCompatActivity<ChannelSubscribeViewModel> {

    public static Intent intentFor(Context context) {
        return new Intent(context, ChannelSubscribeActivity.class);
    }

    @Override
    public void onViewAttached(ChannelSubscribeViewModel viewModel) {

    }

    @Override
    public ChannelSubscribeViewModel giveMeViewModel() {
        return new ChannelSubscribeViewModel();
    }
}
