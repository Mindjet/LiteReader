package io.mindjet.litereader.viewmodel.detail.douban;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemDoubanStaffDetailTopInfoBinding;
import io.mindjet.litereader.model.item.douban.StaffDetail;

/**
 * 演职员详情顶部 view model
 * <p>
 * Created by Mindjet on 2017/4/17.
 */

public class StaffDetailTopInfoViewModel extends BaseViewModel<ViewInterface<ItemDoubanStaffDetailTopInfoBinding>> {

    private StaffDetail detail;

    public StaffDetailTopInfoViewModel(StaffDetail detail) {
        this.detail = detail;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_douban_staff_detail_top_info;
    }

    public StaffDetail getDetail() {
        return detail;
    }

    public String getEnglishName() {
        return getContext().getResources().getString(R.string.douban_staff_english_name, detail.nameEn);
    }

    public String getBornPlace() {
        return getContext().getResources().getString(R.string.douban_staff_born_place, detail.bornPlace);
    }

    @Override
    public void onViewAttached(View view) {

    }
}
