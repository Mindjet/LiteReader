package io.mindjet.litereader.viewmodel.item;

import android.databinding.Bindable;
import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.BR;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemSettingBinding;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * 设置 item view model
 * <p>
 * Created by Mindjet on 2017/4/19.
 */

public class SettingItemViewModel extends BaseViewModel<ViewInterface<ItemSettingBinding>> {

    private String text;

    private Action0 onClick;
    private Action1<Boolean> onSwitch;
    private boolean checked;

    private boolean isSwitch;

    public SettingItemViewModel(String text, Action0 onClick) {
        this.text = text;
        this.onClick = onClick;
        isSwitch = false;
    }

    public SettingItemViewModel(String text, Action1<Boolean> onSwitch, boolean checked) {
        this.text = text;
        this.onSwitch = onSwitch;
        this.checked = checked;
        isSwitch = true;
    }

    public String getText() {
        return text;
    }

    @Bindable
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        notifyPropertyChanged(BR.checked);
        if (onSwitch != null) onSwitch.call(checked);
    }

    public boolean isSwitch() {
        return isSwitch;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_setting;
    }

    @Override
    public void onViewAttached(View view) {

    }


}
