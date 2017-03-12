package io.mindjet.jetgear.mvvm.viewmodel.drawer;

import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.IncludeNativeDrawerLayoutBinding;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetutil.hint.Toaster;

/**
 * Created by Jet on 2/28/17.
 */

public abstract class NativeDrawerLayoutViewModel<V extends ViewInterface<IncludeNativeDrawerLayoutBinding>> extends BaseViewModel<V> implements DrawerLayout.DrawerListener, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BaseViewModel drawerHeaderViewModel;

    @MenuRes
    private int menu = R.menu.menu_drawer;
    @LayoutRes
    private int drawerHeader = R.layout.item_drawer_header;

    public NativeDrawerLayoutViewModel(@MenuRes int menu, @LayoutRes int drawerHeader) {
        this.menu = menu;
        this.drawerHeader = drawerHeader;
    }

    public NativeDrawerLayoutViewModel(@MenuRes int menu, BaseViewModel drawerHeaderViewModel) {
        this.menu = menu;
        this.drawerHeaderViewModel = drawerHeaderViewModel;
    }

    public NativeDrawerLayoutViewModel() {

    }

    public int getMenu() {
        return menu;
    }

    public View getDrawerHeader() {
        if (drawerHeaderViewModel != null) {
            ViewModelBinder.bind(navigationView, drawerHeaderViewModel, false);
            return drawerHeaderViewModel.getSelfView().getBinding().getRoot();
        }
        return LayoutInflater.from(getContext()).inflate(drawerHeader, navigationView, false);
    }

    @Override
    public void onViewAttached(View view) {
        drawerLayout = getSelfView().getBinding().drawerLayout;
        drawerLayout.addDrawerListener(this);
        navigationView = getSelfView().getBinding().navigationView;
        navigationView.setNavigationItemSelectedListener(this);
        initHeader(getSelfView().getBinding().llyContainer);
        initContent(getSelfView().getBinding().llyContainer);
    }

    public abstract void initHeader(ViewGroup container);

    public abstract void initContent(ViewGroup container);

    public void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START, true);
    }

    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START, true);
    }

    public boolean isDrawerOpen() {
        return drawerLayout.isDrawerOpen(getSelfView().getBinding().navigationView);
    }

    public void toggleDrawer() {
        if (isDrawerOpen()) {
            closeDrawer();
        } else {
            openDrawer();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_native_drawer_layout;
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

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        Toaster.toast(getContext(), item.getTitle().toString());
        return false;
    }
}
