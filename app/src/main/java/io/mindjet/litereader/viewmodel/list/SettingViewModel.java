package io.mindjet.litereader.viewmodel.list;

import android.view.ViewGroup;

import io.mindjet.jetgear.databinding.IncludeHeaderRecyclerBinding;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityCompatInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderItemViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.integrated.HeaderRecyclerViewModel;
import io.mindjet.litereader.R;
import io.mindjet.litereader.viewmodel.item.SettingItemViewModel;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * 设置 view model
 * <p>
 * Created by Mindjet on 2017/4/19.
 */

public class SettingViewModel extends HeaderRecyclerViewModel<ActivityCompatInterface<IncludeHeaderRecyclerBinding>> {

    private Action1<Boolean> onDailyWallpaper;
    private Action1<Boolean> onAutoCheckUpdate;
    private Action0 onClearCache;

    @Override
    protected void initHeader(ViewGroup container) {
        HeaderViewModel headerViewModel = new HeaderViewModel.Builder()
                .sink(true)
                .background(R.color.colorPrimary)
                .leftViewModel(new HeaderItemViewModel.BackItemViewModel(getSelfView().getCompatActivity()).icon(R.drawable.ic_arrow_left))
                .centerViewModel(new HeaderItemViewModel.TitleItemViewModel(getString(R.string.setting)))
                .build();
        ViewModelBinder.bind(container, headerViewModel);
    }

    @Override
    protected void afterViewAttached() {
        onDailyWallpaper = new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {

            }
        };
        onAutoCheckUpdate = new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {

            }
        };
        onClearCache = new Action0() {
            @Override
            public void call() {

            }
        };
    }

    @Override
    protected void afterComponentBound() {
        getRecyclerViewModel().disableLoadMore();
        getRecyclerViewModel().getRecyclerView().setBackgroundResource(R.color.white);
        getAdapter().add(new SettingItemViewModel(getString(R.string.show_daily_wallpaper_every_time), onDailyWallpaper, false));
        getAdapter().add(new SettingItemViewModel(getString(R.string.auto_check_for_update), onAutoCheckUpdate, false));
        getAdapter().add(new SettingItemViewModel(getString(R.string.clear_cache), onClearCache));
        getAdapter().notifyDataSetChanged();

    }


}
