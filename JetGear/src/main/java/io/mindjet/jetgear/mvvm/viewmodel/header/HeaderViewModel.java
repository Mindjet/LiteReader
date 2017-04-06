package io.mindjet.jetgear.mvvm.viewmodel.header;

import android.databinding.BindingAdapter;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.ViewGroup;

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

    @BindingAdapter("vm")
    public static void attachViewModel(ViewGroup container, HeaderItemViewModel viewModel) {
        if (viewModel != null) ViewModelBinder.bind(container, viewModel);
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

    public HeaderItemViewModel getLeftViewModel() {
        return builder.leftViewModel;
    }

    public HeaderItemViewModel getCenterViewModel() {
        return builder.centerViewModel;
    }

    public HeaderItemViewModel getRightViewModel() {
        return builder.rightViewModel;
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
        private HeaderItemViewModel leftViewModel, centerViewModel, rightViewModel;
        private boolean withElevation = true;
        private boolean sink = false;

        public Builder background(@ColorRes int background) {
            this.background = background;
            return this;
        }

        public Builder leftViewModel(HeaderItemViewModel leftViewModel) {
            this.leftViewModel = leftViewModel;
            return this;
        }

        public Builder centerViewModel(HeaderItemViewModel centerViewModel) {
            this.centerViewModel = centerViewModel;
            return this;
        }

        public Builder rightViewModel(HeaderItemViewModel rightViewModel) {
            this.rightViewModel = rightViewModel;
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
