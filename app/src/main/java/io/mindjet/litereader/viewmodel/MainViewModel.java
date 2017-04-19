package io.mindjet.litereader.viewmodel;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.mindjet.jetgear.databinding.IncludeDrawerCoordinatorLayoutBinding;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityCompatInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetgear.mvvm.viewmodel.drawer.DrawerHeaderViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.drawer.DrawerItemViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.drawer.DrawerViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderItemViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.header.IHeaderItemCallback;
import io.mindjet.jetgear.mvvm.viewmodel.integrated.DrawerCoordinatorLayoutViewModel;
import io.mindjet.jetgear.reactivex.rxbus.RxBus;
import io.mindjet.litereader.R;
import io.mindjet.litereader.adapter.ColumnViewPagerAdapter;
import io.mindjet.litereader.entity.ChannelCode;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.ui.activity.ChannelSubscribeActivity;
import io.mindjet.litereader.ui.activity.SettingActivity;
import io.mindjet.litereader.util.ChannelUtil;
import io.mindjet.litereader.viewmodel.list.DailyArticleListViewModel;
import io.mindjet.litereader.viewmodel.list.DoubanMovieListViewModel;
import io.mindjet.litereader.viewmodel.list.ZhihuDailyListViewModel;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * 主界面 view model
 * <p>
 * Created by Jet on 3/13/17.
 */

public class MainViewModel extends DrawerCoordinatorLayoutViewModel<ActivityCompatInterface<IncludeDrawerCoordinatorLayoutBinding>> {

    private ColumnViewPagerAdapter columnViewPagerAdapter;

    @Override
    protected void afterViewAttached(IncludeDrawerCoordinatorLayoutBinding binding) {
        RxBus.getInstance()
                .receive(Boolean.class, Constant.CHANNEL_SUBSCRIPTION_SIGNAL)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        closeDrawer();
                        columnViewPagerAdapter.clear();
                        addChannel(ChannelUtil.getChannels(getContext()));
                        getViewPager().setAdapter(columnViewPagerAdapter);
                        getViewPager().setOffscreenPageLimit(columnViewPagerAdapter.getCount());
                    }
                });
    }

    @Override
    protected void initDummyStatusBar(View dummyStatusBar) {
        dummyStatusBar.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected void initAppBarLayout(AppBarLayout appBarLayout) {

    }

    @Override
    protected void initHeader(ViewGroup container) {
        HeaderViewModel header = new HeaderViewModel.Builder()
                .leftViewModel(new HeaderItemViewModel()
                        .icon(R.drawable.ic_drawer)
                        .clickable(true)
                        .callback(new IHeaderItemCallback() {
                            @Override
                            public void call() {
                                toggleDrawer();
                            }
                        }))
                .centerViewModel(new HeaderItemViewModel.TitleItemViewModel(getContext().getResources().getString(R.string.app_name)))
                .build();
        ViewModelBinder.bind(container, header);
    }

    @Override
    protected void initTabLayout(TabLayout tabLayout) {
        tabLayout.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
    }

    @Override
    protected void initDrawer(ViewGroup container) {
        //TODO 渲染Drawer，item详情需再确定
        DrawerViewModel drawer = new DrawerViewModel.Builder()
                .width(R.dimen.drawer_width_large)
                .item(new DrawerHeaderViewModel.Builder()
                        .icon("")
                        .height(R.dimen.drawer_header_height_medium)
                        .backgroundColor(R.color.colorPrimary)
                        .content(getContext().getResources().getString(R.string.app_name))
                        .build())
                .item(new DrawerItemViewModel()
                        .content(getString(R.string.subscribe_channel))
                        .icon(R.drawable.ic_track_gray)
                        .onClick(onSubscribeChannel()))
                .item(new DrawerItemViewModel()
                        .content(getString(R.string.my_collection))
                        .icon(R.drawable.ic_favorite_gray)
                        .onClick(onMyCollection()))
                .item(new DrawerItemViewModel()
                        .content(getString(R.string.setting))
                        .icon(R.drawable.ic_setting_gray)
                        .onClick(onSetting()))
                .item(new DrawerItemViewModel()
                        .content(getString(R.string.about_me))
                        .icon(R.drawable.ic_face_gray)
                        .onClick(onAboutMe()))
                .background(R.color.white)
                .build();
        ViewModelBinder.bind(container, drawer);
    }

    @Override
    protected void initFab(FloatingActionButton fab) {
        fab.setSize(FloatingActionButton.SIZE_MINI);
        fab.setBackgroundTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.colorPrimary)));
        fab.setImageResource(R.drawable.ic_up);
    }

    @Override
    protected void initViewPager(ViewPager viewPager) {
        columnViewPagerAdapter = new ColumnViewPagerAdapter();
        //如果本地读取到的频道列表为null，则显示默认的频道
        List<String> channelList = ChannelUtil.getChannels(getContext());
        channelList = channelList == null ? ChannelUtil.getDefaultChannels() : channelList;
        addChannel(channelList);
        viewPager.setAdapter(columnViewPagerAdapter);
        viewPager.setOffscreenPageLimit(viewPager.getAdapter().getCount() - 1);
    }

    /**
     * 根据channelSet来添加频道 TODO 后续添加更多的频道，注意更新
     *
     * @param channelList 频道列表
     */
    private void addChannel(List<String> channelList) {
        for (String channel : channelList) {
            if (channel.equals(ChannelCode.ZHIHU))
                columnViewPagerAdapter.addWithTitle(new ZhihuDailyListViewModel(), getString(R.string.column_zhihu_daily));
            else if (channel.equals(ChannelCode.DAILY))
                columnViewPagerAdapter.addWithTitle(new DailyArticleListViewModel(), getString(R.string.column_daily_article));
            else if (channel.contentEquals(ChannelCode.DOUBAN))
                columnViewPagerAdapter.addWithTitle(new DoubanMovieListViewModel(), getString(R.string.column_douban_movie));
        }
    }

    @Override
    protected void onFabClick() {
        columnViewPagerAdapter.getItem(getViewPager().getCurrentItem()).getRecyclerView().smoothScrollToPosition(0);
    }

    private Action0 onSubscribeChannel() {
        return new Action0() {
            @Override
            public void call() {
                getContext().startActivity(ChannelSubscribeActivity.intentFor(getContext()));
            }
        };
    }

    private Action0 onMyCollection() {
        return new Action0() {
            @Override
            public void call() {

            }
        };
    }

    private Action0 onSetting() {
        return new Action0() {
            @Override
            public void call() {
                getContext().startActivity(SettingActivity.intentFor(getContext()));
            }
        };
    }

    private Action0 onAboutMe() {
        return new Action0() {
            @Override
            public void call() {

            }
        };
    }

}
