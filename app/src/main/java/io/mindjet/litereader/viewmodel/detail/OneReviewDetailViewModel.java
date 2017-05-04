package io.mindjet.litereader.viewmodel.detail;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import io.mindjet.jetgear.databinding.IncludeCoordinatorCollapseLayoutBinding;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityCompatInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetgear.mvvm.viewmodel.coordinator.CoordinatorCollapseLayoutViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.list.RecyclerViewModel;
import io.mindjet.jetutil.manager.ShareManager;
import io.mindjet.jetwidget.JToolBar;
import io.mindjet.litereader.R;
import io.mindjet.litereader.model.item.one.Review;
import io.mindjet.litereader.ui.dialog.ShareDialog;
import io.mindjet.litereader.viewmodel.detail.one.OneReviewContentViewModel;
import io.mindjet.litereader.viewmodel.detail.one.OneReviewImageViewModel;

/**
 * ONE 影评详情 view model
 * <p>
 * Created by Mindjet on 5/4/17.
 */

public class OneReviewDetailViewModel extends CoordinatorCollapseLayoutViewModel<ActivityCompatInterface<IncludeCoordinatorCollapseLayoutBinding>> {

    private Review review;

    private Menu menu;
    private boolean isCollect;

    public OneReviewDetailViewModel(Review review) {
        this.review = review;
    }

    @Override
    protected void afterViewAttached() {

    }

    @Override
    protected void initCollapsingToolbar(CollapsingToolbarLayout layout) {
        layout.setTitle(getString(R.string.column_one_review));
        layout.setContentScrimResource(R.color.colorPrimary);
        layout.setExpandedTitleColor(getContext().getResources().getColor(R.color.transparent));
    }

    @Override
    protected void setActionBar(JToolBar toolBar) {
        getSelfView().getCompatActivity().setSupportActionBar(getSelfView().getBinding().toolbar);
    }

    @Override
    protected void initCollapsingContent(ViewGroup container) {
        ViewModelBinder.bind(container, new OneReviewImageViewModel(review));
    }

    @Override
    protected void initContent(ViewGroup container) {
        RecyclerViewModel recyclerViewModel = new RecyclerViewModel(false);
        ViewModelBinder.bind(container, recyclerViewModel);
        recyclerViewModel.disableLoadMore();
        recyclerViewModel.getAdapter().add(new OneReviewContentViewModel(review));
        recyclerViewModel.getAdapter().notifyItemInserted(0);
    }

    @Override
    protected void initToolbar(JToolBar toolbar) {
        toolbar.setNavIcon(R.drawable.ic_arrow_left);
    }

    @Override
    protected void initFab(FloatingActionButton fab) {
        fab.setVisibility(View.GONE);
    }

    @Override
    protected void onNavigationIconClick() {
        getSelfView().getCompatActivity().finish();
    }

    @Override
    protected void onFabClick() {

    }

    @Override
    public boolean onCreateOptionMenu(Menu menu) {
        this.menu = menu;
        getSelfView().getCompatActivity().getMenuInflater().inflate(R.menu.menu_common, menu);
        return true;
    }

    private void initCollect() {

    }

    private void updateCollectIcon(boolean isCollect) {
        this.isCollect = isCollect;
        menu.getItem(1).setIcon(isCollect ? R.drawable.ic_star : R.drawable.ic_star_empty);
    }

    private void manipulateCollect() {
        if (isCollect)
            disCollect();
        else
            collect();
    }

    private void disCollect() {

    }

    private void collect() {

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_share:
                new ShareDialog(getContext(), review.shareInfo.title + " " + review.shareUrl, false).show();
                break;
            case R.id.item_collect:
                manipulateCollect();
                break;
            case R.id.item_more:
                ShareManager.with(getContext()).shareAll(review.shareInfo.title + " " + review.shareUrl);
                break;
        }
        return true;
    }
}
