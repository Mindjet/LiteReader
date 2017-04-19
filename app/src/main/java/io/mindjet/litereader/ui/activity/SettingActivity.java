package io.mindjet.litereader.ui.activity;

import android.content.Context;
import android.content.Intent;

import io.mindjet.jetgear.mvvm.viewmodel.activity.ViewModelCompatActivity;
import io.mindjet.litereader.viewmodel.list.SettingViewModel;

/**
 * 设置 activity
 * <p>
 * Created by Mindjet on 2017/4/19.
 */

public class SettingActivity extends ViewModelCompatActivity<SettingViewModel> {

    public static Intent intentFor(Context context) {
        return new Intent(context, SettingActivity.class);
    }

    @Override
    public void onViewAttached(SettingViewModel viewModel) {

    }

    @Override
    public SettingViewModel giveMeViewModel() {
        return new SettingViewModel();
    }
}
