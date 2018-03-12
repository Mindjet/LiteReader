package io.mindjet.litereader.viewmodel.detail;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityCompatInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ActivityDoubanMovieSearchBinding;

/**
 * Created by Mindjet on 2018/3/12.
 */

public class DoubanMovieSearchViewModel extends BaseViewModel<ActivityCompatInterface<ActivityDoubanMovieSearchBinding>> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_douban_movie_search;
    }

    @Override
    public void onViewAttached(View view) {

    }
}
