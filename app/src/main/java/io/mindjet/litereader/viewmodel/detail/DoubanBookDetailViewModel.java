package io.mindjet.litereader.viewmodel.detail;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.ViewGroup;

import io.mindjet.jetgear.databinding.IncludeCoordinatorCollapseLayoutBinding;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityCompatInterface;
import io.mindjet.jetgear.mvvm.viewmodel.coordinator.CoordinatorCollapseLayoutViewModel;
import io.mindjet.jetwidget.JToolBar;
import io.mindjet.litereader.viewmodel.ICollection;

/**
 * 豆瓣图书 详情 view model
 * <p>
 * Created by Mindjet on 5/5/17.
 */

public class DoubanBookDetailViewModel extends CoordinatorCollapseLayoutViewModel<ActivityCompatInterface<IncludeCoordinatorCollapseLayoutBinding>> implements ICollection {

    private String id;
    private String image;

    @Override
    protected void afterViewAttached() {

    }

    @Override
    protected void initCollapsingToolbar(CollapsingToolbarLayout layout) {

    }

    @Override
    protected void setActionBar(JToolBar toolBar) {

    }

    @Override
    protected void initCollapsingContent(ViewGroup container) {

    }

    @Override
    protected void initContent(ViewGroup container) {

    }

    @Override
    protected void initToolbar(JToolBar toolbar) {

    }

    @Override
    protected void initFab(FloatingActionButton fab) {

    }

    @Override
    protected void onNavigationIconClick() {

    }

    @Override
    protected void onFabClick() {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public void initCollect() {

    }

    @Override
    public void updateCollectIcon(boolean isCollect) {

    }

    @Override
    public void manipulateCollect() {

    }

    @Override
    public void disCollect() {

    }

    @Override
    public void collect() {

    }
}
