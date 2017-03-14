package io.mindjet.litereader.viewmodel.column;

import android.text.format.DateFormat;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.mindjet.jetgear.mvvm.viewmodel.BannerViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.list.SwipeRecyclerViewModel;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.jetpack.R;
import io.mindjet.jetutil.hint.Toaster;
import io.mindjet.litereader.model.item.ZhihuStoryItem;
import io.mindjet.litereader.model.item.ZhihuTopStoryItem;
import io.mindjet.litereader.model.list.ZhihuDailyList;
import io.mindjet.litereader.service.ZhihuDailyService;
import io.mindjet.litereader.util.DateUtil;
import io.mindjet.litereader.viewmodel.item.ZhihuBannerItemViewModel;
import io.mindjet.litereader.viewmodel.item.ZhihuDateItemViewModel;
import io.mindjet.litereader.viewmodel.item.ZhihuStoryItemViewModel;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Jet on 3/13/17.
 */

public class ZhihuDailyListViewModel extends SwipeRecyclerViewModel {

    private ZhihuDailyService service;

    private BannerViewModel banner;
    private Date date = new Date();

    private Action1<ZhihuDailyList> onLoadLatestNews;
    private Action1<ZhihuDailyList> onRefreshLatestNews;

    @Override
    protected void afterViewAttached() {
        service = ServiceGen.create(ZhihuDailyService.class);
        getSwipeLayout().setBackgroundColor(getContext().getResources().getColor(R.color.gray_light_translucent));
        changePbColor(R.color.colorPrimary);
        initActions();
    }

    @Override
    public void initRecyclerView() {
        super.initRecyclerView();
        getRecyclerView().setItemAnimator(new SlideInDownAnimator(new AccelerateDecelerateInterpolator()));
    }

    private void initActions() {
        onLoadLatestNews = new Action1<ZhihuDailyList>() {
            @Override
            public void call(ZhihuDailyList list) {
                initBanner(list.topStories);
                getAdapter().add(new ZhihuDateItemViewModel(R.string.latest_daily));
                initNews(list.stories);
            }
        };
        onRefreshLatestNews = new Action1<ZhihuDailyList>() {
            @Override
            public void call(ZhihuDailyList list) {
                getAdapter().clear();
                getAdapter().add(banner);
                updateBanner(list.topStories);
                getAdapter().add(new ZhihuDateItemViewModel(R.string.latest_daily));
                initNews(list.stories);
                hideRefreshing();
            }
        };
    }

    @Override
    public void onRefresh() {
        fetchLatestNews(onRefreshLatestNews);
    }

    @Override
    public void onLoadMore() {
        if (banner != null) {
            loadBeforeNews();
        } else {
            fetchLatestNews(onLoadLatestNews);
        }
    }

    private void loadBeforeNews() {
        service.getBefore(DateFormat.format("yyyyMMdd", date).toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ZhihuDailyList>() {
                    @Override
                    public void call(ZhihuDailyList zhihuDailyList) {
                        date = DateUtil.yesterday(date);
                        getAdapter().add(new ZhihuDateItemViewModel(date));
                        initNews(zhihuDailyList.stories);
                        getAdapter().finishLoadMore(false);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toaster.toast(getContext(), throwable.toString());
                        getAdapter().finishLoadMore(false);
                    }
                });
    }

    private void fetchLatestNews(Action1<ZhihuDailyList> onNext) {
        service.getLatest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toaster.toast(getContext(), throwable.toString());
                    }
                });
    }

    private void initNews(List<ZhihuStoryItem> newsList) {
        List<ZhihuStoryItemViewModel> vmList = new ArrayList<>(newsList.size());
        for (ZhihuStoryItem news : newsList)
            vmList.add(new ZhihuStoryItemViewModel(news));
        getAdapter().addAll(vmList);
        getAdapter().notifyDataSetChanged();
    }

    @SuppressWarnings("unchecked")
    private void initBanner(List<ZhihuTopStoryItem> topStories) {
        List<ZhihuBannerItemViewModel> vmList = new ArrayList<>(topStories.size());
        for (ZhihuTopStoryItem topStory : topStories)
            vmList.add(new ZhihuBannerItemViewModel(topStory));
        banner = new BannerViewModel.Builder()
                .dotSpace(R.dimen.view_pager_dot_space_small)
                .dotAreaHeight(R.dimen.view_pager_dot_area_height_small)
                .height(R.dimen.view_pager_small_height)
                .vmList(vmList)
                .interval(4000)
                .build();
        getAdapter().add(banner);
    }

    private void updateBanner(List<ZhihuTopStoryItem> topStories) {
        List<ZhihuBannerItemViewModel> vmList = new ArrayList<>(topStories.size());
        for (ZhihuTopStoryItem topStory : topStories)
            vmList.add(new ZhihuBannerItemViewModel(topStory));
        banner.updateViewModelList(vmList);
    }

}
