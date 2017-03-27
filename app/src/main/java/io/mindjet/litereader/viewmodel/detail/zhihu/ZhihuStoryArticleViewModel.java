package io.mindjet.litereader.viewmodel.detail.zhihu;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemZhihuStoryArticleBinding;

/**
 * Created by Jet on 3/14/17.
 */

public class ZhihuStoryArticleViewModel extends BaseViewModel<ViewInterface<ItemZhihuStoryArticleBinding>> {

    private String content;
    private String title;

    public ZhihuStoryArticleViewModel(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_zhihu_story_article;
    }

    @Override
    public void onViewAttached(View view) {
        getSelfView().getBinding().title.setText(title);
        getSelfView().getBinding().content.setRichText(content);
    }
}
