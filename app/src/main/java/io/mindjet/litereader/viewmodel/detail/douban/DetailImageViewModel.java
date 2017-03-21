package io.mindjet.litereader.viewmodel.detail.douban;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemDoubanDetailImageBinding;

/**
 * Created by Jet on 3/20/17.
 */

public class DetailImageViewModel extends BaseViewModel<ViewInterface<ItemDoubanDetailImageBinding>> {

    private String poster;
    private String mainlandPubdate;
    private String rating;

    public DetailImageViewModel(String poster, String mainlandPubdate, String rating) {
        this.poster = poster;
        this.mainlandPubdate = mainlandPubdate;
        this.rating = rating;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_douban_detail_image;
    }

    public String getPoster() {
        return poster;
    }

    public String getMainlandPubdate() {
        return "上映时间：" + mainlandPubdate;
    }

    public String getRating() {
        return "评分：" + rating;
    }

    @Override
    public void onViewAttached(View view) {

    }


}
