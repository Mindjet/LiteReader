package io.mindjet.litereader.viewmodel.detail.douban;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemDoubanDetailSummaryBinding;

/**
 * Created by Jet on 3/21/17.
 */

public class DetailSummaryViewModel extends BaseViewModel<ViewInterface<ItemDoubanDetailSummaryBinding>> {

    private String summary;
    private int maxLine = 2;
    private int fullHeight;
    private boolean folded = true;
    private boolean hasAttached = false;

    public DetailSummaryViewModel(String summary) {
        this.summary = summary;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_douban_detail_summary;
    }

    @Override
    public void onViewAttached(View view) {
        if (!hasAttached) {
            final TextView tvSummary = getSelfView().getBinding().tvSummary;
            final ImageView ivToggle = getSelfView().getBinding().ivToggle;
            tvSummary.setText(summary);
            tvSummary.post(new Runnable() {
                @Override
                public void run() {
                    fullHeight = tvSummary.getHeight();
                    tvSummary.setHeight(tvSummary.getLineHeight() * maxLine);
                    ivToggle.setVisibility(tvSummary.getLineCount() > maxLine ? View.VISIBLE : View.GONE);
                    folded = tvSummary.getLineCount() > maxLine;
                }
            });
            ivToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playAnimation(tvSummary, ivToggle);
                }
            });
            hasAttached = true;
        }
    }

    private void playAnimation(final TextView tv, ImageView iv) {
        int duration = 500;
        final int collapsedHeight = tv.getLineHeight() * maxLine;
        ObjectAnimator animator;
        Animation animation;
        if (folded) {
            animator = ObjectAnimator.ofFloat(iv, "rotation", 0f, 180f);
            animation = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    tv.setHeight((int) (collapsedHeight + (fullHeight - collapsedHeight) * interpolatedTime));
                }
            };
        } else {
            animation = new Animation() {
                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    tv.setHeight((int) (fullHeight - (fullHeight - collapsedHeight) * interpolatedTime));
                }
            };
            animator = ObjectAnimator.ofFloat(iv, "rotation", 180f, 0f);
        }
        animator.setDuration(duration);
        animator.start();
        animation.setDuration(duration);
        tv.startAnimation(animation);
        folded = !folded;
    }

}
