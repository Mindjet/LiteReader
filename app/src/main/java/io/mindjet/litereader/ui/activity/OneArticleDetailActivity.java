package io.mindjet.litereader.ui.activity;

import android.content.Context;
import android.content.Intent;

import io.mindjet.jetgear.mvvm.viewmodel.activity.ViewModelCompatActivity;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.viewmodel.detail.OneArticleDetailViewModel;

/**
 * ONE 文章详情 activity
 * <p>
 * Created by Mindjet on 5/5/17.
 */

public class OneArticleDetailActivity extends ViewModelCompatActivity<OneArticleDetailViewModel> {

    public static Intent intentFor(Context context, String articleId, String image) {
        Intent intent = new Intent(context, OneArticleDetailActivity.class);
        intent.putExtra(Constant.EXTRA_ONE_ARTICLE_ID, articleId);
        intent.putExtra(Constant.EXTRA_ONE_ARTICLE_IMAGE, image);
        return intent;
    }

    @Override
    public void onViewAttached(OneArticleDetailViewModel viewModel) {

    }

    @Override
    public OneArticleDetailViewModel giveMeViewModel() {
        String articleId = getIntent().getStringExtra(Constant.EXTRA_ONE_ARTICLE_ID);
        String image = getIntent().getStringExtra(Constant.EXTRA_ONE_ARTICLE_IMAGE);
        return new OneArticleDetailViewModel(articleId, image);
    }
}
