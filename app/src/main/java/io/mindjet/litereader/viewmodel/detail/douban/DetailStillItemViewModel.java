package io.mindjet.litereader.viewmodel.detail.douban;

import android.view.View;
import android.view.ViewGroup;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.AdapterInterface;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemDoubanDetailStillBinding;
import io.mindjet.litereader.model.item.douban.Still;
import rx.functions.Action0;

/**
 * 电影详情中 剧照 item view model
 * <p>
 * Created by Jet on 3/21/17.
 */

public class DetailStillItemViewModel extends BaseViewModel<ViewInterface<ItemDoubanDetailStillBinding>> {

    private Still still;
    private boolean lastOne;
    private Action0 onMore;

    public DetailStillItemViewModel(Still still) {
        this(still, false, null);
    }

    public DetailStillItemViewModel(Still still, boolean lastOne, Action0 onMore) {
        this.still = still;
        this.lastOne = lastOne;
        this.onMore = onMore;
    }

    public void onClick() {
        if (onMore != null) onMore.call();
    }

    public boolean isLastOne() {
        return lastOne;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_douban_detail_still;
    }

    public Still getStill() {
        return still;
    }

    @Override
    public void onViewAttached(View view) {
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(getSelfView().getBinding().cvContainer.getLayoutParams());
        int margin0 = (int) getContext().getResources().getDimension(R.dimen.common_gap);
        int margin1 = (int) getContext().getResources().getDimension(R.dimen.common_gap_medium);
        int margin2 = (int) getContext().getResources().getDimension(R.dimen.common_gap_small);
        int margin3 = (int) getContext().getResources().getDimension(R.dimen.common_gap_xsmall);
        if (getSelfView() instanceof AdapterInterface) {
            AdapterInterface ai = ((AdapterInterface) getSelfView());
            if (ai.getViewHolder().getLayoutPosition() == 0) {
                lp.setMargins(margin1, margin0, 0, margin3);
            } else if (ai.getViewHolder().getLayoutPosition() == ai.getAdapter().size() - 1) {
                lp.setMargins(margin2, margin0, margin1, margin3);
            } else {
                lp.setMargins(margin2, margin0, 0, margin3);
            }
        }
        getSelfView().getBinding().cvContainer.setLayoutParams(lp);
    }
}
