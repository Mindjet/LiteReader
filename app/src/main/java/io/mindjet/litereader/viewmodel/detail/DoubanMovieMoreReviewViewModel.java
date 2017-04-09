package io.mindjet.litereader.viewmodel.detail;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import io.mindjet.jetgear.databinding.IncludeCoordinatorLayoutBinding;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityCompatInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetgear.mvvm.viewmodel.coordinator.CoordinatorLayoutViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderItemViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderViewModel;
import io.mindjet.litereader.R;
import io.mindjet.litereader.adapter.ReviewViewPagerAdapter;
import io.mindjet.litereader.viewmodel.list.DoubanCommentListViewModel;
import io.mindjet.litereader.viewmodel.list.DoubanReviewListViewModel;

/**
 * Created by Jet on 3/21/17.
 */

public class DoubanMovieMoreReviewViewModel extends CoordinatorLayoutViewModel<ActivityCompatInterface<IncludeCoordinatorLayoutBinding>> {

    private String id;
    private String title;
    private ReviewViewPagerAdapter adapter;

    public DoubanMovieMoreReviewViewModel(String id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    protected void initDummyStatusbar(View view) {
        view.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void initHeader(ViewGroup container) {
        HeaderViewModel header = new HeaderViewModel.Builder()
                .sink(false)
                .leftViewModel(new HeaderItemViewModel.BackItemViewModel(getSelfView().getCompatActivity()).icon(R.drawable.ic_arrow_left))
                .leftViewModel(new HeaderItemViewModel.TitleItemViewModel(title))
                .background(R.color.colorPrimary)
                .build();
        ViewModelBinder.bind(container, header);
    }

    @Override
    public void initTab(TabLayout tabLayout) {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
        tabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
    }

    @Override
    public void initViewPager(ViewPager viewPager) {
        adapter = new ReviewViewPagerAdapter();
        viewPager.setAdapter(adapter);
        adapter.addWithTitle(new DoubanReviewListViewModel(id), getContext().getResources().getString(R.string.douban_movie_review));
        adapter.addWithTitle(new DoubanCommentListViewModel(id), getContext().getResources().getString(R.string.douban_movie_comment));
    }

    @Override
    protected void initFab(FloatingActionButton fab) {
        fab.setSize(FloatingActionButton.SIZE_MINI);
        fab.setImageResource(R.drawable.ic_up);
        fab.setBackgroundTintList(ColorStateList.valueOf(getContext().getResources().getColor(R.color.colorPrimary)));
    }

    @Override
    protected void onFabClick() {
        adapter.getItem(getViewPager().getCurrentItem()).getRecyclerView().smoothScrollToPosition(0);
    }

}
