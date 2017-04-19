package io.mindjet.litereader.viewmodel.list;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.mindjet.jetgear.mvvm.viewmodel.BannerViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.list.RecyclerViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.list.SwipeRecyclerViewModel;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.jetutil.hint.Toaster;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemZhihuSectionBinding;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.http.SimpleHttpSubscriber;
import io.mindjet.litereader.http.ThreadDispatcher;
import io.mindjet.litereader.model.item.ZhihuSectionItem;
import io.mindjet.litereader.model.item.ZhihuStoryItem;
import io.mindjet.litereader.model.item.ZhihuTopStoryItem;
import io.mindjet.litereader.model.list.ZhihuDailyList;
import io.mindjet.litereader.model.list.ZhihuSectionList;
import io.mindjet.litereader.reactivex.ActionHttpError;
import io.mindjet.litereader.service.ZhihuDailyService;
import io.mindjet.litereader.ui.activity.ZhihuSectionDetailActivity;
import io.mindjet.litereader.ui.activity.ZhihuStoryDetailActivity;
import io.mindjet.litereader.util.DateUtil;
import io.mindjet.litereader.viewmodel.item.ZhihuStoryItemViewModel;
import io.mindjet.litereader.viewmodel.item.ZhihuBannerItemViewModel;
import io.mindjet.litereader.viewmodel.item.ZhihuDateItemViewModel;
import io.mindjet.litereader.viewmodel.item.ZhihuSectionItemViewModel;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Action3;

/**
 * 知乎日报 首页 view model
 * <p>
 * Created by Jet on 3/13/17.
 */

public class ZhihuDailyListViewModel extends SwipeRecyclerViewModel {

    private ZhihuDailyService service;

    private BannerViewModel banner;
    private RecyclerViewModel<ItemZhihuSectionBinding> section = new RecyclerViewModel<>(false);
    private Date date = new Date();

    private Action1<ZhihuDailyList> onLoadLatestNews;
    private Action1<ZhihuDailyList> onRefreshLatestNews;
    private Action3<String, Integer, Integer> onDailyItemClick;
    private Action2<String, String> onSectionItemClick;

    @Override
    protected void afterViewAttached() {
        service = ServiceGen.create(ZhihuDailyService.class);
        initActions();
    }

    @Override
    protected void afterComponentsBound() {
        getSwipeLayout().setBackgroundColor(getContext().getResources().getColor(R.color.gray_light_translucent));
        getSwipeLayout().setDistanceToTriggerSync(500);
        changePbColor(R.color.colorPrimary);
    }

    private void initActions() {
        onLoadLatestNews = new Action1<ZhihuDailyList>() {
            @Override
            public void call(ZhihuDailyList list) {
                initBanner(list.topStories);
                getAdapter().add(new ZhihuDateItemViewModel(R.string.special_section));
                getAdapter().add(section);
                getAdapter().add(new ZhihuDateItemViewModel(R.string.latest_daily));
                initNews(list.stories);
                hideRefreshing();
                loadSections();
                setIsLoadingMore(false);
                enableLoadMore();
            }
        };
        onRefreshLatestNews = new Action1<ZhihuDailyList>() {
            @Override
            public void call(ZhihuDailyList list) {
                getAdapter().clear();
                getAdapter().add(banner);
                getAdapter().add(new ZhihuDateItemViewModel(R.string.special_section));
                getAdapter().add(section);
                updateBanner(list.topStories);
                getAdapter().add(new ZhihuDateItemViewModel(R.string.latest_daily));
                initNews(list.stories);
                hideRefreshing();
                loadSections();
                setIsLoadingMore(false);
                enableLoadMore();
                date = new Date();          //重置日期
            }
        };
        onDailyItemClick = new Action3<String, Integer, Integer>() {
            @Override
            public void call(String id, Integer touchX, Integer touchY) {
                Intent intent = ZhihuStoryDetailActivity.intentFor(getContext());
                intent.putExtra(Constant.EXTRA_ZHIHU_STORY_ID, id);
                intent.putExtra(Constant.EXTRA_TOUCH_X, touchX);
                intent.putExtra(Constant.EXTRA_TOUCH_Y, touchY);
                getContext().startActivity(intent);
            }
        };
        onSectionItemClick = new Action2<String, String>() {
            @Override
            public void call(String id, String name) {
                Intent intent = ZhihuSectionDetailActivity.intentFor(getContext());
                intent.putExtra(Constant.EXTRA_ZHIHU_SECTION_ID, id);
                intent.putExtra(Constant.EXTRA_ZHIHU_SECTION_NAME, name);
                getContext().startActivity(intent);
            }
        };
    }

    @Override
    public void onRefresh() {
        // 这里存在两种情况:
        // 一种是一开始断网没有banner然后再刷新，这种情况要处理的操作跟第一次加载一样；
        // 另一种情况是已经有了banner，这时候只要再拿到新数据在填充到banner就可以。
        if (banner != null)
            fetchLatestNews(onRefreshLatestNews);
        else
            fetchLatestNews(onLoadLatestNews);
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
                .compose(new ThreadDispatcher<ZhihuDailyList>())
                .subscribe(new SimpleHttpSubscriber<ZhihuDailyList>() {
                    @Override
                    public void onNext(ZhihuDailyList zhihuDailyList) {
                        date = DateUtil.yesterday(date);
                        setIsLoadingMore(false);
                        getAdapter().add(new ZhihuDateItemViewModel(date));
                        initNews(zhihuDailyList.stories);
                    }

                    @Override
                    protected void onFailed() {
                        setIsLoadingMore(false);
                    }
                });
    }

    private void fetchLatestNews(Action1<ZhihuDailyList> onNext) {
        service.getLatest()
                .compose(new ThreadDispatcher<ZhihuDailyList>())
                .subscribe(onNext, new ActionHttpError() {
                    @Override
                    protected void onError() {
                        hideRefreshing();
                        setIsLoadingMore(false);
                    }
                });
    }

    private void loadSections() {
        service.getSections()
                .compose(new ThreadDispatcher<ZhihuSectionList>())
                .subscribe(new SimpleHttpSubscriber<ZhihuSectionList>() {
                    @Override
                    public void onNext(ZhihuSectionList sections) {
                        initSection(sections.data);
                    }
                });
    }

    private void initSection(List<ZhihuSectionItem> sections) {
        section.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        section.disableLoadMore();
        section.getAdapter().clear();
        for (ZhihuSectionItem item : sections)
            section.getAdapter().add(new ZhihuSectionItemViewModel(item).onAction(onSectionItemClick));
        section.getAdapter().notifyDataSetChanged();
    }

    private void initNews(List<ZhihuStoryItem> newsList) {
        List<ZhihuStoryItemViewModel> vmList = new ArrayList<>(newsList.size());
        for (ZhihuStoryItem news : newsList)
            vmList.add(new ZhihuStoryItemViewModel(news).onAction(onDailyItemClick));
        getAdapter().addAll(vmList);
        getAdapter().notifyDataSetChanged();
    }

    @SuppressWarnings("unchecked")
    private void initBanner(List<ZhihuTopStoryItem> topStories) {
        List<ZhihuBannerItemViewModel> vmList = new ArrayList<>(topStories.size());
        for (ZhihuTopStoryItem topStory : topStories)
            vmList.add(new ZhihuBannerItemViewModel(topStory).onAction(onDailyItemClick));
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
            vmList.add(new ZhihuBannerItemViewModel(topStory).onAction(onDailyItemClick));
        banner.updateViewModelList(vmList);
    }

}
