package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemDoubanCommentBinding;
import io.mindjet.litereader.model.item.douban.Comment;

/**
 * Created by Jet on 3/22/17.
 */

public class DoubanCommentItemViewModel extends BaseViewModel<ViewInterface<ItemDoubanCommentBinding>> {

    private Comment comment;

    public DoubanCommentItemViewModel(Comment comment) {
        this.comment = comment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_douban_comment;
    }

    public Comment getComment() {
        return comment;
    }

    @Override
    public void onViewAttached(View view) {

    }
}
