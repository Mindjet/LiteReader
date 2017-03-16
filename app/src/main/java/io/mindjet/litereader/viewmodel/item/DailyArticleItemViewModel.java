package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemDailyArticleBinding;
import io.mindjet.litereader.model.list.DailyArticle;
import rx.functions.Action0;

/**
 * Created by Jet on 3/16/17.
 */

public class DailyArticleItemViewModel extends BaseViewModel<ViewInterface<ItemDailyArticleBinding>> {

    private DailyArticle article;
    private Action0 onAction;

    public DailyArticleItemViewModel(DailyArticle article) {
        this.article = article;
    }

    public DailyArticleItemViewModel onAction(Action0 onAction) {
        this.onAction = onAction;
        return this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_daily_article;
    }

    @Override
    public void onViewAttached(View view) {
        getSelfView().getBinding().title.setText(article.data.title);
        getSelfView().getBinding().author.setText("作者：" + article.data.author);
        getSelfView().getBinding().content.setRichText(article.data.content);
        getSelfView().getBinding().ivRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAction != null) onAction.call();
            }
        });
    }
}
