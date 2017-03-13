package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.AdapterInterface;
import io.mindjet.jetpack.R;
import io.mindjet.jetpack.databinding.ItemZhihuStoryBinding;
import io.mindjet.litereader.model.item.ZhihuStoryItem;

/**
 * Created by Jet on 3/13/17.
 */

public class ZhihuStoryItemViewModel extends BaseViewModel<AdapterInterface<ItemZhihuStoryBinding>> {

    private String imageUrl;
    private String title;

    public ZhihuStoryItemViewModel(ZhihuStoryItem item) {
        this.imageUrl = item.images.get(0);
        this.title = item.title;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_zhihu_story;
    }

    @Override
    public void onViewAttached(View view) {

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }
}


