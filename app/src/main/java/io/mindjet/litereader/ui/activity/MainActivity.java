package io.mindjet.litereader.ui.activity;

import io.mindjet.jetgear.mvvm.viewmodel.activity.ViewModelCompatActivity;
import io.mindjet.litereader.viewmodel.MainViewModel;

public class MainActivity extends ViewModelCompatActivity<MainViewModel> {

    @Override
    public MainViewModel giveMeViewModel() {
        return new MainViewModel();
    }

    @Override
    public void onViewAttached(MainViewModel viewModel) {

    }
}
