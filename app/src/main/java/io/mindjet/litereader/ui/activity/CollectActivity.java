package io.mindjet.litereader.ui.activity;

import android.content.Context;
import android.content.Intent;

import io.mindjet.jetgear.mvvm.viewmodel.activity.ViewModelCompatActivity;
import io.mindjet.litereader.viewmodel.list.CollectViewModel;

/**
 * Created by Mindjet on 2017/4/26.
 */

public class CollectActivity extends ViewModelCompatActivity<CollectViewModel> {

    public static Intent intentFor(Context context){
        return new Intent(context, CollectActivity.class);
    }

    @Override
    public void onViewAttached(CollectViewModel viewModel) {

    }

    @Override
    public CollectViewModel giveMeViewModel() {
        return new CollectViewModel();
    }
}
