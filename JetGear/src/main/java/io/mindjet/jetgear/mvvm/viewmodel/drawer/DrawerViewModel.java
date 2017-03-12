package io.mindjet.jetgear.mvvm.viewmodel.drawer;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.IncludeDrawerBinding;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.jetgear.mvvm.viewmodel.list.RecyclerViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;

/**
 * Created by Jet on 2/24/17.
 */

public class DrawerViewModel extends BaseViewModel<ViewInterface<IncludeDrawerBinding>> {

    private Builder builder;
    private RecyclerViewModel recyclerViewModel;

    private DrawerViewModel(Builder builder) {
        this.builder = builder;
    }

    @Override
    public void onViewAttached(View view) {
        recyclerViewModel = new RecyclerViewModel();
        ViewModelBinder.bind(getSelfView().getBinding().llyContent, recyclerViewModel);
        recyclerViewModel.getAdapter().disableLoadMore();
        recyclerViewModel.getAdapter().addAll(builder.vmList);
    }

    @Override
    public int getLayoutId() {
        return R.layout.include_drawer;
    }

    public int getWidth() {
        return (int) getContext().getResources().getDimension(builder.width);
    }

    public Drawable getBackground() {
        return getContext().getResources().getDrawable(builder.background);
    }

    public static class Builder {
        @DimenRes
        private int width = R.dimen.drawer_width_normal;
        @ColorRes
        private int background = R.color.white;
        private List<BaseViewModel> vmList = new ArrayList<>();

        public Builder width(@DimenRes int width) {
            this.width = width;
            return this;
        }

        public Builder background(@ColorRes int background) {
            this.background = background;
            return this;
        }

        public Builder item(BaseViewModel vm) {
            this.vmList.add(vm);
            return this;
        }

        public Builder items(List<BaseViewModel> vms) {
            this.vmList.addAll(vms);
            return this;
        }

        public DrawerViewModel build() {
            return new DrawerViewModel(this);
        }

    }

}
