package io.mindjet.litereader.viewmodel.detail;

import android.content.Intent;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import java.util.List;

import io.mindjet.jetgear.databinding.IncludeHeaderSwipeLayoutBinding;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityCompatInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderItemViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.integrated.HeaderSwipeLayoutViewModel;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.litereader.R;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.model.item.ZhihuStoryItem;
import io.mindjet.litereader.model.list.ZhihuDailyList;
import io.mindjet.litereader.reactivex.RxAction;
import io.mindjet.litereader.service.ZhihuDailyService;
import io.mindjet.litereader.ui.activity.ZhihuStoryDetailActivity;
import io.mindjet.litereader.viewmodel.item.ZhihuStoryItemViewModel;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Action3;
import rx.schedulers.Schedulers;

/**
 * Created by Jet on 3/15/17.
 */

public class ZhihuSectionDetailViewModel extends HeaderSwipeLayoutViewModel<ActivityCompatInterface<IncludeHeaderSwipeLayoutBinding>> {

    private ZhihuDailyService service;
    private String id;
    private String name;

    @Override
    protected void afterViewAttached() {
        id = getSelfView().getCompatActivity().getIntent().getStringExtra(Constant.EXTRA_ZHIHU_SECTION_ID);
        name = getSelfView().getCompatActivity().getIntent().getStringExtra(Constant.EXTRA_ZHIHU_SECTION_NAME);
        service = ServiceGen.create(ZhihuDailyService.class);
    }

    @Override
    protected void afterComponentsBound() {
        getRecyclerView().setItemAnimator(new SlideInUpAnimator(new LinearInterpolator()));
        changePbColor(R.color.colorPrimary);
    }

    @Override
    protected void initHeader(ViewGroup container) {
        HeaderViewModel header = new HeaderViewModel.Builder()
                .sink(true)
                .withElevation(true)
                .leftViewModel(new HeaderItemViewModel.BackItemViewModel(getSelfView().getCompatActivity()).icon(R.drawable.ic_arrow_left))
                .centerViewModel(new HeaderItemViewModel.TitleItemViewModel(name))
                .background(R.color.colorPrimary)
                .build();
        ViewModelBinder.bind(container, header);
    }

    @Override
    protected void onRefresh() {
        getAdapter().clear();
        onLoadMore();
    }

    @Override
    protected void onLoadMore() {
        service.getSectionDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ZhihuDailyList>() {
                    @Override
                    public void call(ZhihuDailyList list) {
                        initArticles(list.stories);
                        hideRefreshing();
                    }
                }, RxAction.onError());
    }

    private void initArticles(List<ZhihuStoryItem> items) {
        for (ZhihuStoryItem item : items)
            getAdapter().add(new ZhihuStoryItemViewModel(item)
                    .onAction(new Action3<String, Integer, Integer>() {
                        @Override
                        public void call(String id, Integer touchX, Integer touchY) {
                            Intent intent = ZhihuStoryDetailActivity.intentFor(getContext());
                            intent.putExtra(Constant.EXTRA_ZHIHU_STORY_ID, id);
                            intent.putExtra(Constant.EXTRA_TOUCH_X, touchX);
                            intent.putExtra(Constant.EXTRA_TOUCH_Y, touchY);
                            getContext().startActivity(intent);
                        }
                    }));
        getAdapter().notifyDataSetChanged();
        getAdapter().disableLoadMore();
    }

}
