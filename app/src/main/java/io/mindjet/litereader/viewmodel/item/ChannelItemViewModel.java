package io.mindjet.litereader.viewmodel.item;

import android.databinding.Bindable;
import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.BR;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemChannelBinding;
import io.mindjet.litereader.entity.ChannelCode;

/**
 * 频道item view model
 * <p>
 * Created by Mindjet on 2017/4/13.
 */

public class ChannelItemViewModel extends BaseViewModel<ViewInterface<ItemChannelBinding>> {

    private String channelCode;
    private boolean isChecked = false;

    public ChannelItemViewModel(String channelCode) {
        this.channelCode = channelCode;
    }

    //TODO 后期会添加更多频道，注意更新
    public String getName() {
        switch (channelCode) {
            case ChannelCode.ZHIHU:
                return getString(R.string.column_zhihu_daily);
            case ChannelCode.DAILY:
                return getString(R.string.column_daily_article);
            case ChannelCode.DOUBAN:
                return getString(R.string.column_douban_movie);
            case ChannelCode.ONE_REVIEW:
                return getString(R.string.column_one_review);
            default:
                return getString(R.string.column_zhihu_daily);
        }
    }

    public String getChannelCode() {
        return channelCode;
    }

    @Bindable
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        notifyPropertyChanged(BR.checked);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_channel;
    }

    @Override
    public void onViewAttached(View view) {

    }
}
