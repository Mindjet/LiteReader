package io.mindjet.litereader.viewmodel;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

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
import io.mindjet.jetpack.R;
import io.mindjet.litereader.adapter.ColumnViewPagerAdapter;
import io.mindjet.litereader.viewmodel.column.ZhihuDailyListViewModel;

/**
 * Created by Jet on 3/13/17.
 */

public class MainViewModel extends DrawerCoordinatorLayoutViewModel<ActivityCompatInterface<IncludeDrawerCoordinatorLayoutBinding>> {

    private ColumnViewPagerAdapter columnViewPagerAdapter;

    @Override
    protected void afterViewAttached(IncludeDrawerCoordinatorLayoutBinding binding) {

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
                .width(R.dimen.drawer_width_normal)
                .item(new DrawerHeaderViewModel.Builder()
                        .icon("")
                        .height(R.dimen.drawer_header_height_large)
                        .backgroundColor(R.color.colorPrimary)
                        .content(getContext().getResources().getString(R.string.app_name))
                        .build())
                .item(new DrawerItemViewModel().content("xxx").icon(R.drawable.ic_inbox_gray))
                .item(new DrawerItemViewModel().content("xxx").icon(R.drawable.ic_draft_gray))
                .item(new DrawerItemViewModel().content("xxx").icon(R.drawable.ic_starred_gray))
                .background(R.color.white)
                .build();
        ViewModelBinder.bind(container, drawer);
    }

    @Override
    protected void initFab(FloatingActionButton fab) {
        fab.setBackgroundTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.colorPrimary)));
        fab.setImageResource(R.drawable.ic_drawer);
    }

    @Override
    protected void initViewPager(ViewPager viewPager) {
        //TODO 读取本地缓存获取栏目
        columnViewPagerAdapter = new ColumnViewPagerAdapter();
        columnViewPagerAdapter.addWithTitle(new ZhihuDailyListViewModel(), getContext().getResources().getString(R.string.column_zhihu_daily));
        viewPager.setAdapter(columnViewPagerAdapter);
        viewPager.setOffscreenPageLimit(viewPager.getAdapter().getCount() - 1);
    }

    @Override
    protected void onFabClick() {
        //TODO 需确定Fab的功能，上滚到顶部？
    }

}
