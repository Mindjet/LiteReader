package io.mindjet.litereader.viewmodel.list;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.format.DateFormat;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.listener.ViewAttachedListener;
import io.mindjet.jetgear.mvvm.viewmodel.BannerViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.list.RecyclerViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.list.SwipeRecyclerViewModel;
import io.mindjet.jetgear.network.ServiceGen;
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
import io.mindjet.litereader.viewmodel.item.ZhihuBannerItemViewModel;
import io.mindjet.litereader.viewmodel.item.ZhihuDateItemViewModel;
import io.mindjet.litereader.viewmodel.item.ZhihuSectionItemViewModel;
import io.mindjet.litereader.viewmodel.item.ZhihuStoryItemViewModel;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Action3;
import rx.functions.Func2;

/**
 * 知乎日报 首页 view model
 * <p>
 * Created by Jet on 3/13/17.
 */

public class ZhihuDailyListViewModel extends SwipeRecyclerViewModel {

    private ZhihuDailyService service;

    private BannerViewModel bannerVM;
    private RecyclerViewModel<ItemZhihuSectionBinding> sectionVM = new RecyclerViewModel<>(false);
    private Date date = new Date();

    private Action3<String, Integer, Integer> onDailyItemClick;
    private Action2<String, String> onSectionItemClick;

    private Action2<ZhihuDailyList, ZhihuSectionList> onRefreshFinished;

    private int mainIndex = 0;

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

        onRefreshFinished = new Action2<ZhihuDailyList, ZhihuSectionList>() {
            @Override
            public void call(ZhihuDailyList zhihuDailyList, ZhihuSectionList zhihuSectionList) {
                getAdapter().clear();
                mainIndex = 0;
                getAdapter().notifyDataSetChanged();
                addBannerVM(zhihuDailyList.topStories);   //notify inside
                getAdapter().add(new ZhihuDateItemViewModel(R.string.special_section));
                getAdapter().notifyItemInserted(mainIndex++);
                addSectionVM(zhihuSectionList.data);  //notify inside
                getAdapter().add(new ZhihuDateItemViewModel(R.string.latest_daily));
                getAdapter().notifyItemInserted(mainIndex++);
                addNewsVM(zhihuDailyList.stories);    //notify inside
                hideRefreshing();
                getAdapter().onFinishLoadMore(false);
            }
        };
    }

    @Override
    public void onRefresh() {
        fetchLatestNews(onRefreshFinished);
    }

    @Override
    public void onLoadMore() {
        fetchBeforeNews();
    }

    private void fetchBeforeNews() {
        service.getBefore(DateFormat.format("yyyyMMdd", date).toString())
                .delay(500, TimeUnit.MILLISECONDS)
                .compose(new ThreadDispatcher<ZhihuDailyList>())
                .subscribe(new SimpleHttpSubscriber<ZhihuDailyList>() {
                    @Override
                    public void onNext(ZhihuDailyList list) {
                        date = DateUtil.yesterday(date);
                        getAdapter().add(new ZhihuDateItemViewModel(date));
                        getAdapter().notifyItemInserted(mainIndex++);
                        addNewsVM(list.stories);
                        getAdapter().onFinishLoadMore(list.stories.size() == 0);
                    }

                    @Override
                    protected void onFailed() {
                        getAdapter().onFinishLoadMore(false);
                    }
                });
    }

    private void fetchLatestNews(final Action2<ZhihuDailyList, ZhihuSectionList> onRefreshFinished) {
        service.getLatest()
                .zipWith(service.getSections(), new Func2<ZhihuDailyList, ZhihuSectionList, Pair<ZhihuDailyList, ZhihuSectionList>>() {
                    @Override
                    public Pair<ZhihuDailyList, ZhihuSectionList> call(ZhihuDailyList zhihuDailyList, ZhihuSectionList zhihuSectionList) {
                        return new Pair<>(zhihuDailyList, zhihuSectionList);
                    }
                })
                .compose(new ThreadDispatcher<Pair<ZhihuDailyList, ZhihuSectionList>>())
                .subscribe(new Action1<Pair<ZhihuDailyList, ZhihuSectionList>>() {
                    @Override
                    public void call(Pair<ZhihuDailyList, ZhihuSectionList> pair) {
                        onRefreshFinished.call(pair.first, pair.second);
                    }
                }, new ActionHttpError() {
                    @Override
                    protected void onError() {
                        hideRefreshing();
                        getAdapter().onFinishLoadMore(false);
                    }
                });
    }

    private void addSectionVM(final List<ZhihuSectionItem> sections) {
        sectionVM = new RecyclerViewModel<>(false);
        sectionVM.setViewAttachedListener(new ViewAttachedListener() {
            @Override
            public void onViewAttached(BaseViewModel viewModel) {
                sectionVM.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                sectionVM.getAdapter().disableLoadMore();
                for (ZhihuSectionItem item : sections)
                    sectionVM.getAdapter().add(new ZhihuSectionItemViewModel(item).onAction(onSectionItemClick));
                sectionVM.getAdapter().notifyDataSetChanged();
            }
        });
        getAdapter().add(sectionVM);
        getAdapter().notifyItemInserted(mainIndex++);
    }

    private void addNewsVM(List<ZhihuStoryItem> newsList) {
        List<ZhihuStoryItemViewModel> vmList = new ArrayList<>(newsList.size());
        for (ZhihuStoryItem news : newsList)
            vmList.add(new ZhihuStoryItemViewModel(news).onAction(onDailyItemClick));
        getAdapter().addAll(vmList);
        int nowIndex = mainIndex;
        mainIndex += newsList.size();
        getAdapter().notifyItemRangeInserted(nowIndex, newsList.size());
    }

    @SuppressWarnings("unchecked")
    private void addBannerVM(List<ZhihuTopStoryItem> topStories) {
        List<ZhihuBannerItemViewModel> vmList = new ArrayList<>(topStories.size());
        for (ZhihuTopStoryItem topStory : topStories)
            vmList.add(new ZhihuBannerItemViewModel(topStory).onAction(onDailyItemClick));
        bannerVM = new BannerViewModel.Builder()
                .dotSpace(R.dimen.view_pager_dot_space_small)
                .dotAreaHeight(R.dimen.view_pager_dot_area_height_small)
                .height(R.dimen.view_pager_small_height)
                .vmList(vmList)
                .interval(0)
                .build();
        getAdapter().add(bannerVM);
        getAdapter().notifyItemInserted(mainIndex++);
    }
}
