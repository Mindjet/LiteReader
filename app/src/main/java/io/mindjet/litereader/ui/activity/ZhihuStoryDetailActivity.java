package io.mindjet.litereader.ui.activity;

import android.content.Context;
import android.content.Intent;

import io.mindjet.jetgear.mvvm.viewmodel.activity.ViewModelCompatActivity;
import io.mindjet.litereader.viewmodel.detail.ZhihuStoryDetailViewModel;

/**
 * Created by Jet on 3/14/17.
 */

public class ZhihuStoryDetailActivity extends ViewModelCompatActivity<ZhihuStoryDetailViewModel> {

    public static Intent intentFor(Context context) {
        return new Intent(context, ZhihuStoryDetailActivity.class);
    }

    @Override
    public void onViewAttached(ZhihuStoryDetailViewModel viewModel) {

    }

    @Override
    public ZhihuStoryDetailViewModel giveMeViewModel() {
        return new ZhihuStoryDetailViewModel();
    }
}
