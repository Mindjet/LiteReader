package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemOneArticleBinding;
import io.mindjet.litereader.model.item.one.Article;
import io.mindjet.litereader.ui.activity.OneArticleDetailActivity;

/**
 * ONE文章 item view model
 * <p>
 * Created by Mindjet on 5/5/17.
 */

public class OneArticleItemViewModel extends BaseViewModel<ViewInterface<ItemOneArticleBinding>> {

    private Article article;

    public OneArticleItemViewModel(Article article) {
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_one_article;
    }

    @Override
    public void onViewAttached(View view) {

    }

    public void onClick() {
        getContext().startActivity(OneArticleDetailActivity.intentFor(getContext(), article.id, article.imgUrl));
    }

}
