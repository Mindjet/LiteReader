package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.AdapterInterface;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemStoryCollectBinding;
import io.mindjet.litereader.model.item.ZhihuStoryItem;
import io.mindjet.litereader.util.CollectionManager;
import rx.functions.Action1;
import rx.functions.Action3;

/**
 * 收藏列表中知乎日报 item view model
 * <p>
 * Created by Mindjet on 2017/4/27.
 */

public class StoryCollectItemViewModel extends BaseViewModel<ViewInterface<ItemStoryCollectBinding>> {

    private ZhihuStoryItem item;
    private Action1<ZhihuStoryItem> onClick;
    private Action3<String, String, Integer> onUncollect;

    public StoryCollectItemViewModel(ZhihuStoryItem item, Action1<ZhihuStoryItem> onClick, Action3<String, String, Integer> onUncollect) {
        this.item = item;
        this.onClick = onClick;
        this.onUncollect = onUncollect;
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

    public void onUncollect() {
        if (onUncollect != null) {
            int pos = ((AdapterInterface) getSelfView()).getViewHolder().getLayoutPosition();
            onUncollect.call(item.id, CollectionManager.COLLECTION_TYPE_ZHIHU_STORY, pos);
        }
    }

    @Override
    public void onViewAttached(View view) {

    }
}
