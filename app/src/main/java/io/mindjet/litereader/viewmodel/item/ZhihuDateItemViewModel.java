package io.mindjet.litereader.viewmodel.item;

import android.support.annotation.StringRes;
import android.view.View;

import java.util.Date;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemZhihuDateBinding;
import io.mindjet.litereader.util.DateUtil;

/**
 * Created by Jet on 3/14/17.
 */

public class ZhihuDateItemViewModel extends BaseViewModel<ViewInterface<ItemZhihuDateBinding>> {

    @StringRes
    private int text;
    private Date date;

    public ZhihuDateItemViewModel(Date date) {
        this.date = date;
    }

    public ZhihuDateItemViewModel(@StringRes int text) {
        this.text = text;
    }

    public String getDate() {
        if (text != 0)
            return getContext().getResources().getString(text);
        return DateUtil.toFriendlyFormat(date);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_zhihu_date;
    }

    @Override
    public void onViewAttached(View view) {

    }
}
