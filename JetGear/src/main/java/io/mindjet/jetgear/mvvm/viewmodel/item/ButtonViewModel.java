package io.mindjet.jetgear.mvvm.viewmodel.item;

import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.mindjet.jetgear.R;
import io.mindjet.jetgear.databinding.ItemButtonBinding;
import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import rx.functions.Action0;

/**
 * Created by Jet on 3/2/17.
 */

public class ButtonViewModel extends BaseViewModel<ViewInterface<ItemButtonBinding>> {

    private Builder builder;

    private ButtonViewModel(Builder builder) {
        this.builder = builder;
    }

    @Override
    public void onViewAttached(View view) {
        styleBackground();
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_button;
    }

    private void styleBackground() {
        TextView textView = getSelfView().getBinding().textView;
        if (builder.backgroundDrawable != 0) {
            textView.setBackground(getContext().getResources().getDrawable(builder.backgroundDrawable));
        } else if (builder.backgroundColor != 0) {
            textView.setBackgroundResource(builder.backgroundColor);
        }
    }

    public int getWidth() {
        if (builder.width == 0 || builder.width == -1)
            return builder.width;
        return (int) getContext().getResources().getDimension(builder.width);
    }

    public int getHeight() {
        if (builder.height == 0 || builder.height == -1)
            return builder.height;
        return (int) getContext().getResources().getDimension(builder.height);
    }

    public String getText() {
        if (builder.textRes == 0) {
            return builder.text;
        } else {
            return getContext().getResources().getString(builder.textRes);
        }
    }

    public boolean getWithElevation() {
        return builder.withElevation;
    }

    public List<Integer> getMargin() {
        if (builder.margin.size() == 0) {
            List<Integer> temp = new ArrayList<>();
            temp.add(0);
            temp.add(0);
            temp.add(0);
            temp.add(0);
            return temp;
        }
        List<Integer> marginList = new ArrayList<>();
        for (Integer integer : builder.margin)
            marginList.add(getContext().getResources().getDimensionPixelSize(integer));
        return marginList;
    }

    public float getPadding() {
        return getContext().getResources().getDimension(builder.padding);
    }

    public View.OnClickListener getAction() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (builder.action != null) builder.action.call();
            }
        };
    }

    public int getTextColor() {
        return getContext().getResources().getColor(builder.textColor);
    }

    public float getTextSize() {
        return getContext().getResources().getDimension(builder.textSize);
    }

    public static class Builder {
        @DimenRes
        private int padding;
        @DimenRes
        private int textSize;
        @DimenRes
        private int width;
        @DimenRes
        private int height;
        @ColorRes
        private int backgroundColor;
        @DrawableRes
        private int backgroundDrawable;
        @ColorRes
        private int textColor;
        @StringRes
        private int textRes;
        private boolean withElevation;
        private Action0 action;
        private String text = "";
        private List<Integer> margin = new ArrayList<>(4);

        public Builder simpleButton(String text, Action0 action) {
            return text(text)
                    .action(action)
                    .simpleAttr();
        }

        public Builder simpleButton(@StringRes int textRes, Action0 action) {
            return text(textRes)
                    .action(action)
                    .simpleAttr();
        }

        public Builder simpleAttr() {
            return width(0)
                    .height(-1)
                    .margin(R.dimen.common_gap_small)
                    .textColor(R.color.black)
                    .withElevation(true)
                    .textSize(R.dimen.common_text_size)
                    .padding(R.dimen.common_gap_medium);
        }

        public Builder margin(@DimenRes int left, @DimenRes int top, @DimenRes int right, @DimenRes int bottom) {
            margin.add(left);
            margin.add(top);
            margin.add(right);
            margin.add(bottom);
            return this;
        }

        public Builder margin(@DimenRes int commonMargin) {
            margin.add(commonMargin);
            margin.add(commonMargin);
            margin.add(commonMargin);
            margin.add(commonMargin);
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder text(@StringRes int textRes) {
            this.textRes = textRes;
            return this;
        }

        public Builder withElevation(boolean withElevation) {
            this.withElevation = withElevation;
            return this;
        }

        public Builder padding(@DimenRes int padding) {
            this.padding = padding;
            return this;
        }

        public Builder textSize(@DimenRes int textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder textColor(@ColorRes int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder width(@DimenRes int width) {
            this.width = width;
            return this;
        }

        public Builder height(@DimenRes int height) {
            this.height = height;
            return this;
        }

        public Builder backgroundColor(@ColorRes int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder backgroundDrawable(@DrawableRes int backgroundDrawable) {
            this.backgroundDrawable = backgroundDrawable;
            return this;
        }

        public Builder action(Action0 action) {
            this.action = action;
            return this;
        }

        public ButtonViewModel build() {
            return new ButtonViewModel(this);
        }

    }

}
