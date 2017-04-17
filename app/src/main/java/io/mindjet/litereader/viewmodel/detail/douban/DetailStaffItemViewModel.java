package io.mindjet.litereader.viewmodel.detail.douban;

import android.view.View;
import android.view.ViewGroup;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.AdapterInterface;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemDoubanDetailStaffBinding;
import io.mindjet.litereader.model.item.douban.Staff;
import rx.functions.Action1;

/**
 * 演职员 item view model
 * <p>
 * Created by Jet on 3/21/17.
 */

public class DetailStaffItemViewModel extends BaseViewModel<ViewInterface<ItemDoubanDetailStaffBinding>> {

    private Staff staff;
    private String type;

    private Action1<Staff> onClick;

    public DetailStaffItemViewModel(Staff staff, String type, Action1<Staff> onClick) {
        this.staff = staff;
        this.type = type;
        this.onClick = onClick;
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

    public void onClick() {
        if (onClick != null) onClick.call(staff);
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
