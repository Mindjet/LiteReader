package io.mindjet.litereader.ui.activity;

import android.content.Context;
import android.content.Intent;

import io.mindjet.jetgear.mvvm.viewmodel.activity.ViewModelCompatActivity;
import io.mindjet.litereader.viewmodel.AboutMeViewModel;

public class AboutMeActivity extends ViewModelCompatActivity<AboutMeViewModel> {

    public static Intent intentFor(Context from) {
        return new Intent(from, AboutMeActivity.class);
    }

    @Override
    public AboutMeViewModel giveMeViewModel() {
        return new AboutMeViewModel();
    }

    @Override
    public void onViewAttached(AboutMeViewModel viewModel) {

    }
}
