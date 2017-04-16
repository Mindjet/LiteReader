package io.mindjet.litereader.ui.activity;

import android.content.Context;
import android.content.Intent;

import io.mindjet.jetgear.mvvm.viewmodel.activity.ViewModelCompatActivity;
import io.mindjet.litereader.viewmodel.list.ZhihuSectionDetailViewModel;

/**
 * Created by Jet on 3/15/17.
 */

public class ZhihuSectionDetailActivity extends ViewModelCompatActivity<ZhihuSectionDetailViewModel> {

    public static Intent intentFor(Context context) {
        return new Intent(context, ZhihuSectionDetailActivity.class);
    }

    @Override
    public void onViewAttached(ZhihuSectionDetailViewModel viewModel) {

    }

    @Override
    public ZhihuSectionDetailViewModel giveMeViewModel() {
        return new ZhihuSectionDetailViewModel();
    }

}
