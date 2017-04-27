package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemStoryCollectBinding;
import io.mindjet.litereader.model.item.ZhihuStoryItem;
import rx.functions.Action1;

/**
 * 收藏列表中知乎日报 item view model
 * <p>
 * Created by Mindjet on 2017/4/27.
 */

public class StoryCollectItemViewModel extends BaseViewModel<ViewInterface<ItemStoryCollectBinding>> {

    private ZhihuStoryItem item;
    private Action1<ZhihuStoryItem> onClick;

    public StoryCollectItemViewModel(ZhihuStoryItem item, Action1<ZhihuStoryItem> onClick) {
        this.item = item;
        this.onClick = onClick;
    }

    public ZhihuStoryItem getItem() {
        return item;
    }

    public String getCover() {
        return item.images.get(0);
    }

    public void onClick() {
        if (onClick != null) onClick.call(item);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_story_collect;
    }

    @Override
    public void onViewAttached(View view) {

    }
}
