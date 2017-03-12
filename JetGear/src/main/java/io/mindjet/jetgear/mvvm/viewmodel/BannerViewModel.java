package io.mindjet.jetgear.mvvm.viewmodel;

import android.animation.ObjectAnimator;
import android.databinding.DataBindingUtil;
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

public class BannerViewModel extends BaseViewModel<ViewInterface<IncludeBannerBinding>> {

    private final Builder builder;
    private BannerPagerAdapter adapter;
    private BannerPagerChangeListener listener;
    private Subscription tiktokSub;

    private BannerViewModel(Builder builder) {
        this.builder = builder;
    }

    @Override
    public void onViewAttached(View view) {
        initPagerAdapter();
        initPagerListener();
        initCursors();
        initTikTok();
    }

    private void initPagerAdapter() {
        if (builder.urlList == null) {
            jLogger.w("Url list is null, use fake list with 3 blank items.");
            builder.urlList = new ArrayList<>();
            builder.urlList.add("");
            builder.urlList.add("");
            builder.urlList.add("");
        }
        adapter = new BannerPagerAdapter(builder.urlList);
        getSelfView().getBinding().viewPager.setAdapter(adapter);
    }

    private void initPagerListener() {
        listener = new BannerPagerChangeListener();
        getSelfView().getBinding().viewPager.addOnPageChangeListener(listener);
    }

    /**
     * Render or re-render the cursors.
     */
    private void initCursors() {
        getSelfView().getBinding().llyDots.removeAllViews();
        for (int i = 0; i < builder.urlList.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(builder.normalCursor);
            imageView.setPadding(
                    i == 0 ? 0 : ((int) getContext().getResources().getDimension(builder.dotSpace)), 0,
                    i == builder.urlList.size() - 1 ? 0 : ((int) getContext().getResources().getDimension(builder.dotSpace)), 0
            );
            getSelfView().getBinding().llyDots.addView(imageView, i);
        }
    }

    /**
     * Let the page changes automatically.
     */
    private void initTikTok() {
        if (builder.interval != 0)
            tiktokSub = Observable.interval(builder.interval, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            int page = listener.currentPosition;
                            page = page + 1 == builder.urlList.size() ? 0 : page + 1;
                            getSelfView().getBinding().viewPager.setCurrentItem(page, true);
                        }
                    });
    }

    public int getHeight() {
        return (int) getContext().getResources().getDimension(builder.height);
    }

    public int getHighlightCursor() {
        return builder.highlightCursor;
    }

    /**
     * When you need to update the data set, you are supposed to call this method.
     *
     * @param urlList the new data set.
     */
    public void updateUrlList(List<String> urlList) {
        if (tiktokSub != null) tiktokSub.unsubscribe();
        builder.urlList = urlList;
        initCursors();
        adapter.updateUrlList(urlList); //notify adapter.
        listener.onDataChanged();       //notify listener.
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

    public static class Builder {
        private List<String> urlList;
        @DimenRes
        private int height = R.dimen.view_pager_height;
        @DimenRes
        private int dotSpace = R.dimen.view_pager_dot_space;
        @DrawableRes
        private int normalCursor = R.drawable.shape_cursor_normal, highlightCursor = R.drawable.shape_cursor_selected;
        private int interval = 0;

        public Builder urlList(List<String> urlList) {
            this.urlList = urlList;
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

        /**
         * Set the interval the page changes. If you don't want the viewpager changes page itself, just let interval be 0(default).
         *
         * @param interval the page changing interval in millisecond.
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
        private LayoutInflater inflater;

        private BannerPagerAdapter(List<String> urlList) {
            this.urlList = urlList;
            this.inflater = LayoutInflater.from(getContext());
        }

        void updateUrlList(List<String> urlList) {
            this.urlList = urlList;
            notifyDataSetChanged();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ItemBannerBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_banner, container, false);
            binding.setUrl(urlList.get(position));
            binding.executePendingBindings();
            container.addView(binding.getRoot());
            return binding.getRoot();
        }

        @Override
        public int getCount() {
            return urlList.size();
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
                offsetWidth = (totalWidth - dotWidth) / (builder.urlList.size() - 1);
                hasMeasuredWidths = true;
            }
        }

        void onDataChanged() {
            currentPosition = 0;
            cursorOffset = 0;
        }

    }

}
