package io.mindjet.jetgear.mvvm.viewmodel.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;

import io.mindjet.jetgear.mvvm.base.BaseCompatActivity;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.listener.ViewAttachedListener;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;

/**
 * Created by Jet on 2/28/17.
 */

public abstract class ViewModelCompatActivity<T extends BaseViewModel> extends BaseCompatActivity implements ViewAttachedListener<T> {

    private T viewModel;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = giveMeViewModel();
        viewModel.setViewAttachedListener(this);
        ViewModelBinder.bindCompat(this, viewModel);
    }

    public abstract T giveMeViewModel();

    @Override
    protected void onResume() {
        super.onResume();
        if (viewModel != null)
            viewModel.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (viewModel != null)
            viewModel.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewModel != null)
            viewModel.onDestroy();
    }

    @Override
    public void onBackPressed() {
        boolean consumed = false;
        if (viewModel != null) consumed = viewModel.onBackPressed();
        if (!consumed) super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return viewModel != null && viewModel.onCreateOptionMenu(menu);
    }
}
