package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.jetpack.R;
import io.mindjet.jetpack.databinding.ItemZhihuBannerBinding;
import io.mindjet.litereader.model.item.ZhihuTopStoryItem;

/**
 * Created by Jet on 3/14/17.
 */

public class ZhihuBannerItemViewModel extends BaseViewModel<ViewInterface<ItemZhihuBannerBinding>> {

    private ZhihuTopStoryItem item;

    public ZhihuBannerItemViewModel(ZhihuTopStoryItem item) {
        this.item = item;
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
}
