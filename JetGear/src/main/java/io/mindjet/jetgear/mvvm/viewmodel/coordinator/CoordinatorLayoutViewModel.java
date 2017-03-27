package io.mindjet.jetgear.mvvm.viewmodel.coordinator;

import android.support.annotation.ColorRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.IncludeCoordinatorLayoutBinding;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.jetutil.version.VersionUtil;

/**
 * Created by Jet on 3/1/17.
 */

public abstract class CoordinatorLayoutViewModel<V extends ViewInterface<IncludeCoordinatorLayoutBinding>> extends BaseViewModel<V> implements TabLayout.OnTabSelectedListener {

    private FloatingActionButton fab;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void onViewAttached(View view) {
        fab = getSelfView().getBinding().fab;
        viewPager = getSelfView().getBinding().viewPager;
        tabLayout = getSelfView().getBinding().tabLayout;
        tabLayout.addOnTabSelectedListener(this);
        initDummyStatusbar(getSelfView().getBinding().dummyStatusBar);
        initHeader(getSelfView().getBinding().flyHeader);
        initTab(tabLayout);
        initViewPager(viewPager);
        initFab(fab);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_coordinator_layout;
    }

    protected abstract void initDummyStatusbar(View view);

    public abstract void initHeader(ViewGroup container);

    public abstract void initTab(TabLayout tabLayout);

    public abstract void initViewPager(ViewPager viewPager);

    public ViewPager getViewPager() {
        return viewPager;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }

    public boolean getDummyStatusbarVisibility() {
        return VersionUtil.afterLollipop();
    }

    protected abstract void initFab(FloatingActionButton fab);

    public void hideFab() {
        fab.hide();
    }

    public void showFab() {
        fab.show();
    }

    protected void setTabTextColors(@ColorRes int normalColor, @ColorRes int selectedColor) {
        tabLayout.setTabTextColors(getContext().getResources().getColor(normalColor), getContext().getResources().getColor(selectedColor));
    }

    protected void setTabBackground(@ColorRes int background) {
        tabLayout.setBackgroundColor(getContext().getResources().getColor(background));
    }

    protected void setIndicatorColor(@ColorRes int indicatorColor) {
        tabLayout.setSelectedTabIndicatorColor(getContext().getResources().getColor(indicatorColor));
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    /**
     * Callback of the floating action button.
     */
    public View.OnClickListener getFabClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFabClick();
            }
        };
    }

    protected abstract void onFabClick();

}
