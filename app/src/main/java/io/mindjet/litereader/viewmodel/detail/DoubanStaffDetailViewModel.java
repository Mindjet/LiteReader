package io.mindjet.litereader.viewmodel.detail;

import android.view.ViewGroup;

import io.mindjet.jetgear.databinding.IncludeHeaderRecyclerBinding;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityCompatInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderItemViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.integrated.HeaderRecyclerViewModel;
import io.mindjet.jetgear.network.ServiceGen;
import io.mindjet.jetgear.reactivex.rxbus.RxBus;
import io.mindjet.litereader.R;
import io.mindjet.litereader.http.SimpleHttpHandler;
import io.mindjet.litereader.model.item.douban.StaffDetail;
import io.mindjet.litereader.reactivex.RxAction;
import io.mindjet.litereader.service.DoubanService;
import io.mindjet.litereader.viewmodel.detail.douban.StaffDetailSummaryViewModel;
import io.mindjet.litereader.viewmodel.detail.douban.StaffDetailTopInfoViewModel;
import io.mindjet.litereader.viewmodel.detail.douban.StaffDetailWorkViewModel;
import rx.Subscription;
import rx.functions.Action1;

/**
 * 演职员详情 view model
 * <p>
 * Created by Mindjet on 2017/4/17.
 */

public class DoubanStaffDetailViewModel extends HeaderRecyclerViewModel<ActivityCompatInterface<IncludeHeaderRecyclerBinding>> {

    private DoubanService service;
    private Subscription subscription;

    private StaffDetailTopInfoViewModel topInfoViewModel;
    private StaffDetailSummaryViewModel summaryViewModel;
    private StaffDetailWorkViewModel workViewModel;

    private int index = 0;
    private String id;
    private String title;

    public DoubanStaffDetailViewModel(String id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    protected void initHeader(ViewGroup container) {
        HeaderViewModel headerViewModel = new HeaderViewModel.Builder()
                .sink(true)
                .background(R.color.colorPrimary)
                .leftViewModel(new HeaderItemViewModel.BackItemViewModel(getSelfView().getCompatActivity()).icon(R.drawable.ic_arrow_left))
                .leftViewModel(new HeaderItemViewModel.TitleItemViewModel(title).textColor(R.color.white))
                .build();
        ViewModelBinder.bind(container, headerViewModel);
    }

    @Override
    protected void afterViewAttached() {
        service = ServiceGen.create(DoubanService.class);
    }

    @Override
    protected void afterComponentBound() {
        getRecyclerViewModel().disableLoadMore();
        subscription = service.getStaffDetail(id)
                .compose(new SimpleHttpHandler<StaffDetail>())
                .subscribe(new Action1<StaffDetail>() {
                    @Override
                    public void call(StaffDetail detail) {
                        addItem(detail);
                    }
                }, RxAction.onError());
    }

    private void addItem(StaffDetail detail) {
        topInfoViewModel = new StaffDetailTopInfoViewModel(detail);
        getAdapter().add(topInfoViewModel);
        getAdapter().notifyItemInserted(index++);

        summaryViewModel = new StaffDetailSummaryViewModel(id);
        getAdapter().add(summaryViewModel);
        getAdapter().notifyItemInserted(index++);

        workViewModel = new StaffDetailWorkViewModel(detail.works);
        getAdapter().add(workViewModel);
        getAdapter().notifyItemInserted(index++);

    }

    @Override
    public void onDestroy() {
        RxBus.unSubscribe(subscription);
    }
}
