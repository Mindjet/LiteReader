package io.mindjet.litereader.viewmodel.detail;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ActivityDoubanDetailBinding;

/**
 * Created by Jet on 3/17/17.
 */

public class DoubanMovieDetailViewModel extends BaseViewModel<ActivityInterface<ActivityDoubanDetailBinding>> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_douban_detail;
    }

    @Override
    public void onViewAttached(View view) {

    }

}
