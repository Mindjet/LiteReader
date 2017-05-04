package io.mindjet.litereader.viewmodel.detail;

import android.content.Intent;
import android.databinding.Bindable;
import android.net.Uri;
import android.view.View;

import java.util.concurrent.TimeUnit;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.jetutil.file.SPUtil;
import io.mindjet.jetutil.hint.Toaster;
import io.mindjet.litereader.BR;
import io.mindjet.litereader.BuildConfig;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.IncludeSettingBinding;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.reactivex.RxAction;
import io.mindjet.litereader.reactivex.RxLoadingView;
import io.mindjet.litereader.util.CacheUtil;
import io.mindjet.litereader.util.CollectionManager;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.schedulers.Schedulers;

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
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnSubscribe(RxLoadingView.show(getContext(), R.string.clearing_cache))
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        CacheUtil.clearSharedPreferences(getContext());
                        CollectionManager.getInstance(getContext()).clear();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(RxLoadingView.showAction1(getContext(), R.string.clear_cache_success))
                .delay(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .doOnUnsubscribe(RxLoadingView.dismiss())
                .subscribe(Actions.empty(), RxAction.onError());
    }

    public void onFeedback() {
        Toaster.toast(getContext(), getString(R.string.welcome_to_feedback_on_github_issue), 3000);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse("https://github.com/Mindjet/LiteReader/issues");
        intent.setData(content_url);
        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
        getContext().startActivity(Intent.createChooser(intent, "选择打开网页的应用"));
    }

}
