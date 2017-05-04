package io.mindjet.litereader.viewmodel.item;

import android.view.MotionEvent;
import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.AdapterInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemZhihuStoryBinding;
import io.mindjet.litereader.model.item.ZhihuStoryItem;
import rx.functions.Action3;

/**
 * 知乎日报 item view model
 * <p>
 * Created by Mindjet on 3/13/17.
 */

public class ZhihuStoryItemViewModel extends BaseViewModel<AdapterInterface<ItemZhihuStoryBinding>> {

    private ZhihuStoryItem item;
    private Action3<String, Integer, Integer> onAction;

    public ZhihuStoryItemViewModel(ZhihuStoryItem item) {
        this.item = item;
    }

    public ZhihuStoryItemViewModel onAction(Action3<String, Integer, Integer> onAction) {
        this.onAction = onAction;
        return this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_zhihu_story;
    }

    @Override
    public void onViewAttached(View view) {
        getSelfView().getBinding().llyContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && onAction != null)
                    onAction.call(item.id, ((int) event.getRawX()), ((int) event.getRawY()));
                return false;
            }
        });
    }

    public String getImageUrl() {
        return item.images.get(0);
    }

    public String getTitle() {
        return item.title;
    }
}


