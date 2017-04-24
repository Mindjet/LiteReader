package io.mindjet.litereader.viewmodel.detail;

import android.databinding.Bindable;
import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.jetutil.file.SPUtil;
import io.mindjet.litereader.BR;
import io.mindjet.litereader.BuildConfig;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.IncludeSettingBinding;
import io.mindjet.litereader.entity.Constant;

/**
 * 设置 view model
 * <p>
 * Created by Mindjet on 2017/4/23.
 */

public class IncludeSettingViewModel extends BaseViewModel<ViewInterface<IncludeSettingBinding>> {

    private boolean showDailyWallpaper = false;

    @Override
    public int getLayoutId() {
        return R.layout.include_setting;
    }

    @Override
    public void onViewAttached(View view) {
        setShowDailyWallpaper(SPUtil.getBoolean(getContext(), Constant.KEY_SETTING_SHOW_WALLPAPER));
    }

    @Bindable
    public boolean isShowDailyWallpaper() {
        return showDailyWallpaper;
    }

    public void setShowDailyWallpaper(boolean showDailyWallpaper) {
        this.showDailyWallpaper = showDailyWallpaper;
        notifyPropertyChanged(BR.showDailyWallpaper);
        SPUtil.save(getContext(), Constant.KEY_SETTING_SHOW_WALLPAPER, showDailyWallpaper);
    }

    public String getCurrentVersion() {
        return getContext().getResources().getString(R.string.current_version, BuildConfig.VERSION_NAME);
    }

    public void onCheckUpdate() {
        //TODO 检查更新
    }

    public void onClearCache() {
        //TODO 清除缓存
    }

    public void onFeedback() {
        //TODO 跳转到github issue页面
    }

}
