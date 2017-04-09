package io.mindjet.jetgear.mvvm.viewmodel.header;

import android.databinding.BindingAdapter;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.IncludeHeaderBinding;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetutil.version.VersionUtil;

/**
 * Created by Jet on 2/21/17.
 */

public class HeaderViewModel extends BaseViewModel<ViewInterface<IncludeHeaderBinding>> {

    private Builder builder;

    public HeaderViewModel(Builder builder) {
        this.builder = builder;
    }

    @BindingAdapter("vmList")
    public static void attachViewModels(ViewGroup container, List<HeaderItemViewModel> viewModels) {
        for (HeaderItemViewModel vm : viewModels) {
            if (vm != null)
                ViewModelBinder.bind(container, vm);
        }
    }

    @Override
    public void onViewAttached(View view) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.include_header;
    }

    public float getElevation() {
        return builder.withElevation ? getContext().getResources().getInteger(R.integer.common_elevation) : 0f;
    }

    public List<HeaderItemViewModel> getLeftViewModels() {
        return builder.leftViewModels;
    }

    public List<HeaderItemViewModel> getCenterViewModels() {
        return builder.centerViewModels;
    }

    public List<HeaderItemViewModel> getRightViewModels() {
        return builder.rightViewModels;
    }

    public int getBackground() {
        return getContext().getResources().getColor(builder.background);
    }

    public boolean isSink() {
        //the sink feature is only available after KitKat.
        return VersionUtil.afterKitKat() && builder.sink;
    }

    public static class Builder {
        @ColorRes
        private int background = R.color.colorPrimary;
        private boolean withElevation = true;
        private boolean sink = false;

        private List<HeaderItemViewModel> leftViewModels = new ArrayList<>();
        private List<HeaderItemViewModel> centerViewModels = new ArrayList<>();
        private List<HeaderItemViewModel> rightViewModels = new ArrayList<>();

        public Builder background(@ColorRes int background) {
            this.background = background;
            return this;
        }

        public Builder leftViewModel(HeaderItemViewModel leftViewModel) {
            leftViewModels.add(leftViewModel);
            return this;
        }

        public Builder centerViewModel(HeaderItemViewModel centerViewModel) {
            centerViewModels.add(centerViewModel);
            return this;
        }

        public Builder rightViewModel(HeaderItemViewModel rightViewModel) {
            rightViewModels.add(rightViewModel);
            return this;
        }

        public Builder withElevation(boolean withElevation) {
            this.withElevation = withElevation;
            return this;
        }

        /**
         * If you want to expand the whole view to the status bar, you need to set it true.
         * Meanwhile you are supposed to use a theme called <i>AppTheme.NoTitle</i> to style the activity.
         *
         * @param sink whether to expend the view to the status bar or not.
         * @return Builder
         */
        public Builder sink(boolean sink) {
            this.sink = sink;
            return this;
        }

        public HeaderViewModel build() {
            return new HeaderViewModel(this);
        }

    }

}
