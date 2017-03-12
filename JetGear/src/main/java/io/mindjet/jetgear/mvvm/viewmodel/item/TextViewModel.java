package io.mindjet.jetgear.mvvm.viewmodel.item;

import android.view.View;
import android.view.ViewGroup;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.ItemTextBinding;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;

/**
 * Created by Mindjet on 2017/2/15.
 */

public class TextViewModel extends BaseViewModel<ViewInterface<ItemTextBinding>> {

    private String text;

    public TextViewModel(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_text;
    }

    @Override
    public void onViewAttached(View view) {

    }

}
