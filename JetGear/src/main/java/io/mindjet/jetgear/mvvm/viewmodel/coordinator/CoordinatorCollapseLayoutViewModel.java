package io.mindjet.jetgear.mvvm.viewmodel.coordinator;

import android.content.res.ColorStateList;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.IncludeCoordinatorCollapseLayoutBinding;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.jetwidget.JToolBar;

/**
 * Created by Jet on 3/2/17.
 */

public abstract class CoordinatorCollapseLayoutViewModel<V extends ViewInterface<IncludeCoordinatorCollapseLayoutBinding>> extends BaseViewModel<V> implements AppBarLayout.OnOffsetChangedListener, Toolbar.OnMenuItemClickListener {

    @Override
    public void onViewAttached(View view) {
        getSelfView().getBinding().appBarLayout.addOnOffsetChangedListener(this);
        afterViewAttached();
        setActionBar(getSelfView().getBinding().toolbar);
        initListener();
        initCollapsingToolbar(getSelfView().getBinding().collapsingToolBar);
        initCollapsingContent(getSelfView().getBinding().collapsingContent);
        initToolbar(getSelfView().getBinding().toolbar);
        initContent(getSelfView().getBinding().llyContainer);
        initFab(getSelfView().getBinding().fab);
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_coordinator_collapse_layout;
    }

    /**
     * Here, you can :
     * <ul>
     * <li>
     * set the title. ({@link CollapsingToolbarLayout#setTitle(CharSequence)})
     * </li>
     * <li>
     * set collapsed background({@link CollapsingToolbarLayout#setContentScrimResource(int)})
     * </li>
     * <li>
     * set the expanded title margins. ({@link CollapsingToolbarLayout#setExpandedTitleMargin(int, int, int, int)})
     * </li>
     * <li>
     * set collapsed/expanded title appearance({@link CollapsingToolbarLayout#setCollapsedTitleTextAppearance(int)}, {@link CollapsingToolbarLayout#setExpandedTitleTextAppearance(int)})
     * </li>
     * </ul>
     */
    protected abstract void initCollapsingToolbar(CollapsingToolbarLayout layout);

    /**
     * You are supposed to set the toolbar as the activity's actionbar. {@link android.support.v7.app.AppCompatActivity#setSupportActionBar(Toolbar)}
     */
    protected abstract void setActionBar(JToolBar toolBar);

    protected abstract void initCollapsingContent(ViewGroup container);

    protected abstract void initContent(ViewGroup container);

    /**
     * Here, you can to set Navigation icon. ({@link JToolBar#setNavIcon(int)})
     */
    protected abstract void initToolbar(JToolBar toolbar);

    /**
     * Here, you can:
     * <ul>
     * <li>
     * change the fab size ({@link FloatingActionButton#setSize(int)})
     * </li>
     * <li>
     * change the fab icon ({@link FloatingActionButton#setImageResource(int)})
     * </li>
     * </li>
     * <li>
     * change the fab background color ({@link FloatingActionButton#setBackgroundTintList(ColorStateList)} (int)})
     * </li>
     * </ul>
     */
    protected abstract void initFab(FloatingActionButton fab);

    protected abstract void afterViewAttached();

    protected abstract void onNavigationIconClick();

    protected abstract void onFabClick();

    private void initListener() {
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNavigationIconClick();
            }
        });
        getToolbar().setOnMenuItemClickListener(this);
        getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFabClick();
            }
        });
    }

    protected Toolbar getToolbar() {
        return getSelfView().getBinding().toolbar;
    }

    protected FloatingActionButton getFab() {
        return getSelfView().getBinding().fab;
    }

    protected CollapsingToolbarLayout getCollapsingToolbarLayout() {
        return getSelfView().getBinding().collapsingToolBar;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//        jLogger.e(verticalOffset);
    }

}
