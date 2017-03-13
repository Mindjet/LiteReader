package io.mindjet.litereader.viewmodel.column;

import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.mindjet.jetgear.mvvm.viewmodel.BannerViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.item.TextViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.list.SwipeRecyclerViewModel;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.jetpack.R;
import io.mindjet.jetutil.hint.Toaster;
import io.mindjet.litereader.model.item.ZhihuStoryItem;
import io.mindjet.litereader.model.item.ZhihuTopStoryItem;
import io.mindjet.litereader.model.list.ZhihuDailyList;
import io.mindjet.litereader.service.ZhihuDailyService;
import io.mindjet.litereader.viewmodel.item.ZhihuStoryItemViewModel;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Jet on 3/13/17.
 */

public class ZhihuDailyListViewModel extends SwipeRecyclerViewModel {

    private ZhihuDailyService service;

    private String date = DateFormat.format("yyMMdd", new Date()).toString();
    private boolean isBannerLoaded = false;

    @Override
    protected void afterViewAttached() {
        service = ServiceGen.create(ZhihuDailyService.class);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        if (isBannerLoaded) {
            loadBeforeNews();
        } else {
            loadLatestNews();
        }
    }

    private void loadBeforeNews() {
        getAdapter().finishLoadMore(false);
    }

    private void loadLatestNews() {
        service.getLatest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ZhihuDailyList>() {
                    @Override
                    public void call(ZhihuDailyList list) {
                        isBannerLoaded = true;
                        initBanner(list.topStories);
                        getAdapter().add(new TextViewModel("最新日报"));
                        initNews(list.stories);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toaster.toast(getContext(), throwable.toString());
                    }
                });
    }

    private void initNews(List<ZhihuStoryItem> newsList) {
        List<ZhihuStoryItemViewModel> vmList = new ArrayList<>(newsList.size());
        for (ZhihuStoryItem news : newsList) {
            vmList.add(new ZhihuStoryItemViewModel(news));
        }
        getAdapter().addAll(vmList);
        getAdapter().notifyDataSetChanged();
    }

    //TODO banner不符合要求，需修改为inflate ViewModel的layout，并且添加点击事件
    private void initBanner(List<ZhihuTopStoryItem> topStories) {
        List<String> bannerUrl = new ArrayList<>(topStories.size());
        for (ZhihuTopStoryItem topStory : topStories)
            bannerUrl.add(topStory.image);
        BannerViewModel banner = new BannerViewModel.Builder()
                .dotSpace(R.dimen.view_pager_dot_space)
                .height(R.dimen.view_pager_small_height)
                .urlList(bannerUrl)
                .interval(4000)
                .build();
        getAdapter().add(banner);
    }
}
