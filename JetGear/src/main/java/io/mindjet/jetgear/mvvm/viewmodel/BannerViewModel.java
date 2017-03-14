package io.mindjet.jetgear.mvvm.viewmodel;

import android.animation.ObjectAnimator;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.IncludeBannerBinding;
import io.mindjet.jetgear.databinding.ItemBannerBinding;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Jet on 2/23/17.
 */

public class BannerViewModel<T extends BaseViewModel> extends BaseViewModel<ViewInterface<IncludeBannerBinding>> {

    private final Builder builder;
    private BannerPagerAdapter adapter;
    private BannerPagerChangeListener listener;
    private Subscription tiktokSub;
    private boolean isAttached = false;

    private BannerViewModel(Builder builder) {
        this.builder = builder;
    }

    @Override
    public void onViewAttached(View view) {
        //avoid doing the initialization work when it is rebound by the RecyclerView.
        if (!isAttached) {
            initPagerAdapter();
            initPagerListener();
            initCursors();
            initTikTok();
            isAttached = true;
        }
    }

    private void initPagerAdapter() {
        if (builder.vmList != null) {
            adapter = new BannerPagerAdapter();
            adapter.updateViewModelList(builder.vmList);
        } else if (builder.urlList != null) {
            adapter = new BannerPagerAdapter();
            adapter.updateUrlList(builder.urlList);
        } else {
            jLogger.w("If both ViewModel list and url list are null, use dummy list with 3 blank items.");
            builder.urlList = new ArrayList<>();
            builder.urlList.add("");
            builder.urlList.add("");
            builder.urlList.add("");
            adapter = new BannerPagerAdapter();
            adapter.updateUrlList(builder.urlList);
        }
        getSelfView().getBinding().viewPager.setAdapter(adapter);
    }

    private void initPagerListener() {
        listener = new BannerPagerChangeListener();
        getSelfView().getBinding().viewPager.addOnPageChangeListener(listener);
    }

    private void initCursors() {
        getSelfView().getBinding().llyDots.removeAllViews();
        for (int i = 0; i < adapter.getCount(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(builder.normalCursor);
            imageView.setPadding(
                    i == 0 ? 0 : ((int) getContext().getResources().getDimension(builder.dotSpace)), 0,
                    i == adapter.getCount() - 1 ? 0 : ((int) getContext().getResources().getDimension(builder.dotSpace)), 0
            );
            getSelfView().getBinding().llyDots.addView(imageView, i);
        }
    }

    private void initTikTok() {
        if (builder.interval != 0)
            tiktokSub = Observable.interval(builder.interval, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            int page = listener.currentPosition;
                            page = page + 1 == adapter.getCount() ? 0 : page + 1;
                            getSelfView().getBinding().viewPager.setCurrentItem(page, true);
                        }
                    });
    }

    public int getHeight() {
        return (int) getContext().getResources().getDimension(builder.height);
    }

    public int getDotAreaHeight() {
        return (int) getContext().getResources().getDimension(builder.dotAreaHeight);
    }

    public int getHighlightCursor() {
        return builder.highlightCursor;
    }

    public void updateUrlList(List<String> urlList) {
        if (tiktokSub != null) tiktokSub.unsubscribe();
        builder.urlList = urlList;
        initCursors();
        adapter.updateUrlList(urlList); //notify adapter.
        listener.onDataChanged();       //notify listener.
        getSelfView().getBinding().viewPager.setCurrentItem(0, true);
        initTikTok();
    }

    public void updateViewModelList(List<T> vmList) {
        if (tiktokSub != null) tiktokSub.unsubscribe();
        builder.vmList = vmList;
        initCursors();
        adapter.updateViewModelList(vmList);
        listener.onDataChanged();
        getSelfView().getBinding().viewPager.setCurrentItem(0, true);
        initTikTok();
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_banner;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (tiktokSub != null && tiktokSub.isUnsubscribed()) tiktokSub.unsubscribe();
    }

    public static class Builder<T extends BaseViewModel> {
        private List<String> urlList;
        private List<T> vmList;
        @DimenRes
        private int height = R.dimen.view_pager_height;
        @DimenRes
        private int dotSpace = R.dimen.view_pager_dot_space;
        @DrawableRes
        private int normalCursor = R.drawable.shape_cursor_normal, highlightCursor = R.drawable.shape_cursor_selected;
        private int interval = 0;
        @DimenRes
        private int dotAreaHeight = R.dimen.view_pager_dot_area_height;

        public Builder urlList(List<String> urlList) {
            this.urlList = urlList;
            return this;
        }

        public Builder vmList(List<T> vmList) {
            this.vmList = vmList;
            return this;
        }

        public Builder height(@DimenRes int height) {
            this.height = height;
            return this;
        }

        public Builder normalCursor(@DrawableRes int normalCursor) {
            this.normalCursor = normalCursor;
            return this;
        }

        public Builder highlightCursor(@DrawableRes int highlightCursor) {
            this.highlightCursor = highlightCursor;
            return this;
        }

        public Builder dotSpace(@DimenRes int dotSpace) {
            this.dotSpace = dotSpace;
            return this;
        }

        public Builder dotAreaHeight(@DimenRes int dotAreaHeight) {
            this.dotAreaHeight = dotAreaHeight;
            return this;
        }

        /**
         * Set the page-changing interval. If you don't want the viewpager changes page itself, just let interval be 0(default).
         *
         * @param interval the page-changing interval in millisecond.
         * @return builder
         */
        public Builder interval(int interval) {
            this.interval = interval;
            return this;
        }

        public BannerViewModel build() {
            return new BannerViewModel(this);
        }

    }

    private class BannerPagerAdapter extends PagerAdapter {

        private List<String> urlList;
        private List<T> vmList;
        private LayoutInflater inflater;

        private BannerPagerAdapter() {
            this.inflater = LayoutInflater.from(getContext());
        }

        void updateUrlList(List<String> urlList) {
            this.urlList = urlList;
            notifyDataSetChanged();
        }

        void updateViewModelList(List<T> vmList) {
            this.vmList = vmList;
            notifyDataSetChanged();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (vmList == null) {
                ItemBannerBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_banner, container, false);
                binding.setUrl(urlList.get(position));
                binding.executePendingBindings();
                container.addView(binding.getRoot());
                return binding.getRoot();
            } else {
                ViewDataBinding binding = ViewModelBinder.bind(container, vmList.get(position), false);
                binding.executePendingBindings();
                container.addView(binding.getRoot());
                return binding.getRoot();
            }
        }

        @Override
        public int getCount() {
            return vmList == null ? urlList == null ? 0 : urlList.size() : vmList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private class BannerPagerChangeListener implements ViewPager.OnPageChangeListener {

        private int currentPosition = 0;
        private float cursorOffset = 0;
        private boolean hasMeasuredWidths = false;

        private float offsetWidth = 0;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            measureWidths();
            ObjectAnimator.ofFloat(getSelfView().getBinding().cursor, "translationX", cursorOffset + (position - currentPosition) * (offsetWidth)).start();
            cursorOffset += (position - currentPosition) * offsetWidth;
            currentPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        /**
         * Measure the the offset that the highlight cursor should go with.
         */
        void measureWidths() {
            if (!hasMeasuredWidths) {
                getSelfView().getBinding().llyDots.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                float totalWidth = getSelfView().getBinding().llyDots.getMeasuredWidth();
                float dotWidth = getSelfView().getBinding().llyDots.getChildAt(0).getWidth() - getContext().getResources().getDimensionPixelSize(builder.dotSpace);
                offsetWidth = (totalWidth - dotWidth) / (adapter.getCount() - 1);
                hasMeasuredWidths = true;
            }
        }

        void onDataChanged() {
            currentPosition = 0;
            cursorOffset = 0;
        }

    }

}
