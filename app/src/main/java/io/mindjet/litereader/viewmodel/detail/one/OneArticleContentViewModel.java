package io.mindjet.litereader.viewmodel.detail.one;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemOneArticleContentBinding;
import io.mindjet.litereader.model.item.one.ArticleDetail;

/**
 * ONE 文章详情 正文 view model
 * Created by Mindjet on 5/5/17.
 */

public class OneArticleContentViewModel extends BaseViewModel<ViewInterface<ItemOneArticleContentBinding>> {

    private ArticleDetail detail;

    public String getTitle() {
        return detail == null ? "" : detail.title;
    }

    public String getGuideWord() {
        return detail == null ? "" : detail.guideWord;
    }

    public String getAvatar() {
        return detail == null || detail.author == null ? "" : detail.author.get(0).avatar;
    }

    public String getName() {
        return detail == null || detail.author == null ? "" : detail.author.get(0).name;
    }

    public String getSignature() {
        return detail == null || detail.author == null ? "" : detail.author.get(0).signature;
    }

    public String getContent() {
        return detail == null ? "" : detail.content;
    }

    public String getPraiseNum() {
        return detail == null ? "" : detail.praiseNum;
    }

    public String getTime() {
        return detail == null ? "" : detail.updateDate;
    }

    public boolean getSignatureVisible() {
        return detail != null && detail.author != null && !detail.author.get(0).signature.equals("");
    }

    public boolean getAuthorVisible() {
        return detail != null && detail.author != null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_one_article_content;
    }

    public void update(ArticleDetail detail) {
        this.detail = detail;
        notifyChange();
    }

    @Override
    public void onViewAttached(View view) {

    }
}
