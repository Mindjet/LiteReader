package io.mindjet.jetgear.mvvm.viewmodel.integrated;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.IncludeDrawerCoordinatorLayoutBinding;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.jetutil.version.VersionUtil;

/**
 * Created by Jet on 3/9/17.
 */

public abstract class DrawerCoordinatorLayoutViewModel<V extends ViewInterface<IncludeDrawerCoordinatorLayoutBinding>> extends BaseViewModel<V> implements AppBarLayout.OnOffsetChangedListener, DrawerLayout.DrawerListener {

    @Override
    public int getLayoutId() {
        return R.layout.include_drawer_coordinator_layout;
    }

    @Override
    public void onViewAttached(View view) {
        initListener();
        afterViewAttached(getSelfView().getBinding());
        initDummyStatusBar(getSelfView().getBinding().dummyStatusBar);
        initAppBarLayout(getSelfView().getBinding().appBarLayout);
        initHeader(getSelfView().getBinding().flyHeader);
        initTabLayout(getSelfView().getBinding().tabLayout);
        initDrawer(getSelfView().getBinding().llyDrawer);
        initFab(getSelfView().getBinding().fab);
        initViewPager(getSelfView().getBinding().viewPager);
        getTabLayout().setupWithViewPager(getViewPager());
    }

    private void initListener() {
        getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFabClick();
            }
        });
    }

    //We need a dummy status bar if the version is later than Lollipop.
    public boolean getDummyStatusBarVisibility() {
        return VersionUtil.afterLollipop();
    }

    protected FloatingActionButton getFab() {
        return getSelfView().getBinding().fab;
    }

    protected TabLayout getTabLayout() {
        return getSelfView().getBinding().tabLayout;
    }

    protected ViewPager getViewPager() {
        return getSelfView().getBinding().viewPager;
    }

    protected DrawerLayout getDrawerLayout() {
        return getSelfView().getBinding().drawerLayout;
    }

    protected abstract void afterViewAttached(IncludeDrawerCoordinatorLayoutBinding binding);

    /**
     * To have a better user interface, we adopt the Theme called AppTheme.NoTitle, and if your version is before API 21, this no-title feature won't take effect, which means that you still have your original status bar.
     * When the version is API 21 or later, however, this status bar exists and you need to set its background tuned with the style of the header.
     *
     * @param dummyStatusBar the dummy status bar.
     */
    protected abstract void initDummyStatusBar(View dummyStatusBar);

    protected abstract void initAppBarLayout(AppBarLayout appBarLayout);

    protected abstract void initHeader(ViewGroup container);

    protected abstract void initTabLayout(TabLayout tabLayout);

    protected abstract void initDrawer(ViewGroup container);

    protected abstract void initFab(FloatingActionButton fab);

    protected abstract void initViewPager(ViewPager viewPager);

    protected abstract void onFabClick();

    protected void openDrawer() {
        getDrawerLayout().openDrawer(GravityCompat.START, true);
    }

    protected void closeDrawer() {
        getDrawerLayout().closeDrawer(GravityCompat.START, true);
    }

    protected boolean isDrawerOpen() {
        return getDrawerLayout().isDrawerOpen(getSelfView().getBinding().llyDrawer);
    }

    protected void toggleDrawer() {
        if (isDrawerOpen()) {
            closeDrawer();
        } else {
            openDrawer();
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}

