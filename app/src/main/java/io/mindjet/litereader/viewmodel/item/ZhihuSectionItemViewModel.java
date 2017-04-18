package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemZhihuSectionBinding;
import io.mindjet.litereader.model.item.ZhihuSectionItem;
import rx.functions.Action2;

/**
 * 知乎专栏 item view model
 * <p>
 * Created by Jet on 3/15/17.
 */

public class ZhihuSectionItemViewModel extends BaseViewModel<ViewInterface<ItemZhihuSectionBinding>> {

    private ZhihuSectionItem item;
    private Action2<String, String> onAction;

    public ZhihuSectionItemViewModel(ZhihuSectionItem item) {
        this.item = item;
    }

    public ZhihuSectionItemViewModel onAction(Action2<String, String> onAction) {
        this.onAction = onAction;
        return this;
    }

    public String getImageUrl() {
        return item.thumbnail;
    }

    public String getName() {
        return item.name;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_zhihu_section;
    }

    @Override
    public void onViewAttached(View view) {
        getSelfView().getBinding().flyContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAction != null) onAction.call(item.id, item.name);
            }
        });
    }
}
