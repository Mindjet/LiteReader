package io.mindjet.litereader.ui.activity;

import android.content.Context;
import android.content.Intent;

import io.mindjet.jetgear.mvvm.viewmodel.activity.ViewModelCompatActivity;
import io.mindjet.litereader.viewmodel.MainViewModel;

public class MainActivity extends ViewModelCompatActivity<MainViewModel> {

    public static Intent intentFor(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public MainViewModel giveMeViewModel() {
        return new MainViewModel();
    }

    @Override
    public void onViewAttached(MainViewModel viewModel) {

    }
}
