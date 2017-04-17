package io.mindjet.litereader.ui.activity;

import android.content.Context;
import android.content.Intent;

import io.mindjet.jetgear.mvvm.viewmodel.activity.ViewModelCompatActivity;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.viewmodel.detail.DoubanStaffDetailViewModel;

/**
 * Created by Mindjet on 2017/4/17.
 */

public class DoubanStaffDetailActivity extends ViewModelCompatActivity<DoubanStaffDetailViewModel> {

    public static Intent intentFor(Context context) {
        return new Intent(context, DoubanStaffDetailActivity.class);
    }

    @Override
    public void onViewAttached(DoubanStaffDetailViewModel viewModel) {

    }

    @Override
    public DoubanStaffDetailViewModel giveMeViewModel() {
        String id = getIntent().getStringExtra(Constant.EXTRA_DOUBAN_STAFF_ID);
        String title = getIntent().getStringExtra(Constant.EXTRA_DOUBAN_MOVIE_TITLE);
        return new DoubanStaffDetailViewModel(id, title);
    }
}
