package io.mindjet.litereader.ui.activity;

import android.content.Context;
import android.content.Intent;

import io.mindjet.jetgear.mvvm.viewmodel.activity.ViewModelCompatActivity;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.viewmodel.list.DoubanStillListViewModel;

/**
 * 豆瓣剧照列表 activity
 * <p>
 * Created by Jet on 5/3/17.
 */

public class DoubanStillListActivity extends ViewModelCompatActivity<DoubanStillListViewModel> {

    public static Intent intentFor(Context context, String movieId, String title) {
        Intent intent = new Intent(context, DoubanStillListActivity.class);
        intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_ID, movieId);
        intent.putExtra(Constant.EXTRA_DOUBAN_MOVIE_TITLE, title);
        return intent;
    }

    @Override
    public void onViewAttached(DoubanStillListViewModel viewModel) {

    }

    @Override
    public DoubanStillListViewModel giveMeViewModel() {
        String movieId = getIntent().getStringExtra(Constant.EXTRA_DOUBAN_MOVIE_ID);
        String title = getIntent().getStringExtra(Constant.EXTRA_DOUBAN_MOVIE_TITLE);
        return new DoubanStillListViewModel(movieId, title);
    }
}
