package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemZhihuBannerBinding;
import io.mindjet.litereader.model.item.ZhihuTopStoryItem;
import rx.functions.Action3;

/**
 * 知乎日报首页 banner item view model
 * <p>
 * Created by Jet on 3/14/17.
 */

public class ZhihuBannerItemViewModel extends BaseViewModel<ViewInterface<ItemZhihuBannerBinding>> {

    private ZhihuTopStoryItem item;
    private Action3<String, Integer, Integer> onAction;

    public ZhihuBannerItemViewModel(ZhihuTopStoryItem item) {
        this.item = item;
    }

    public ZhihuBannerItemViewModel onAction(Action3<String, Integer, Integer> onAction) {
        this.onAction = onAction;
        return this;
    }

    public String getImageUrl() {
        return item.image;
    }

    public String getTitle() {
        return item.title;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_zhihu_banner;
    }

    @Override
    public void onViewAttached(View view) {

    }

    public void onClick(){
        if (onAction != null)
            onAction.call(item.id, 0, 0);
    }
}
