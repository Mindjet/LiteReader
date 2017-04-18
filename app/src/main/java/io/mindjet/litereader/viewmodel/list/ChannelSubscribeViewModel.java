package io.mindjet.litereader.viewmodel.list;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.mindjet.jetgear.databinding.IncludeHeaderRecyclerBinding;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityCompatInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderItemViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.header.IHeaderItemCallback;
import io.mindjet.jetgear.mvvm.viewmodel.integrated.HeaderRecyclerViewModel;
import io.mindjet.jetgear.reactivex.rxbus.RxBus;
import io.mindjet.jetutil.hint.Toaster;
import io.mindjet.litereader.R;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.util.ChannelUtil;
import io.mindjet.litereader.viewmodel.item.ChannelItemViewModel;
import io.mindjet.litereader.viewmodel.item.ChannelUsageItemViewModel;

/**
 * 频道列表 view model
 * <p>
 * Created by Mindjet on 2017/4/13.
 */

public class ChannelSubscribeViewModel extends HeaderRecyclerViewModel<ActivityCompatInterface<IncludeHeaderRecyclerBinding>> {

    private List<String> subscribedChannels;            //一开始的被订阅的频道列表

    @Override
    protected void initHeader(ViewGroup container) {
        HeaderViewModel headerViewModel = new HeaderViewModel.Builder()
                .sink(true)
                .background(R.color.colorPrimary)
                .leftViewModel(new HeaderItemViewModel.BackItemViewModel(getSelfView().getCompatActivity()).icon(R.drawable.ic_arrow_left))
                .centerViewModel(new HeaderItemViewModel.TitleItemViewModel(getString(R.string.subscribe_channel)))
                .rightViewModel(new HeaderItemViewModel()
                        .icon(R.drawable.ic_check)
                        .clickable(true)
                        .callback(new IHeaderItemCallback() {
                            @Override
                            public void call() {
                                saveSubscribedChannel();
                            }
                        })
                )
                .build();
        ViewModelBinder.bind(container, headerViewModel);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void afterComponentBound() {
        getRecyclerViewModel().disableLoadMore();
        List<String> channels = ChannelUtil.getSortedChannels(getContext());        //获取排序好的所有频道
        subscribedChannels = ChannelUtil.getChannels(getContext());                 //获得订阅的频道
        for (String channel : channels) {
            ChannelItemViewModel item = new ChannelItemViewModel(channel);
            item.setChecked(subscribedChannels.contains(channel));              //如果是订阅的频道则checkbox为选中状态
            getAdapter().add(item);
        }
        getAdapter().add(new ChannelUsageItemViewModel());
        getAdapter().notifyItemRangeInserted(0, channels.size() + 1);
        addItemTouchHelper();
    }

    /**
     * 保存订阅
     */
    private void saveSubscribedChannel() {
        List<String> selected = new ArrayList<>();
        for (int i = 0; i < getAdapter().size() - 1; i++) {
            ChannelItemViewModel item = (ChannelItemViewModel) getAdapter().get(i);
            if (item.isChecked())
                selected.add(item.getChannelCode());
        }
        if (selected.size() == 0) {
            Toaster.toast(getContext(), getString(R.string.please_select_at_least_one_channel));
        } else {
            if (!ChannelUtil.isSame(selected, subscribedChannels)) {          //如果订阅的频道和顺序没有改变，那么不做任何事情
                ChannelUtil.saveChannels(getContext(), selected);
                //发送频道订阅改变事件，通知主页面更新频道
                RxBus.getInstance().send(true, Constant.CHANNEL_SUBSCRIPTION_SIGNAL);
            }
            getSelfView().getCompatActivity().finish();
        }
    }

    /**
     * 为 RecyclerView 添加拖拽的特性，可以调整频道的顺序
     */
    private void addItemTouchHelper() {
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                if (viewHolder.getItemViewType() == target.getItemViewType()) {
                    int from = viewHolder.getAdapterPosition();
                    int to = target.getAdapterPosition();
                    Collections.swap(getAdapter().getList(), from, to);
                    getAdapter().notifyItemMoved(from, to);
                    return true;
                }
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

        });
        itemTouchHelper.attachToRecyclerView(getRecyclerViewModel().getRecyclerView());
    }

}
