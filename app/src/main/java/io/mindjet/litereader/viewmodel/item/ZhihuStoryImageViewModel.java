package io.mindjet.litereader.viewmodel.item;

import android.databinding.ObservableField;
import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemZhihuStoryImageBinding;

/**
 * Created by Jet on 3/14/17.
 */

public class ZhihuStoryImageViewModel extends BaseViewModel<ViewInterface<ItemZhihuStoryImageBinding>> {

    public ObservableField<String> imageSource;
    public ObservableField<String> imageUrl;

    public ZhihuStoryImageViewModel(String imageSource, String imageUrl) {
        this.imageSource = new ObservableField<>(imageSource);
        this.imageUrl = new ObservableField<>(imageUrl);
    }

    public void setImageSource(String imageSource) {
        this.imageSource.set(imageSource);
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl.set(imageUrl);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_zhihu_story_image;
    }

    @Override
    public void onViewAttached(View view) {

    }
}
