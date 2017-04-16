package io.mindjet.litereader.viewmodel.detail.douban;

import android.view.View;
import android.view.ViewGroup;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.AdapterInterface;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemDoubanDetailStaffBinding;
import io.mindjet.litereader.model.item.douban.Staff;

/**
 * 演职员 item view model
 * <p>
 * Created by Jet on 3/21/17.
 */

public class DetailStaffItemViewModel extends BaseViewModel<ViewInterface<ItemDoubanDetailStaffBinding>> {

    private Staff staff;
    private String type;

    public DetailStaffItemViewModel(Staff staff, String type) {
        this.staff = staff;
        this.type = type;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_douban_detail_staff;
    }

    public Staff getStaff() {
        return staff;
    }

    public String getType() {
        return type;
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
