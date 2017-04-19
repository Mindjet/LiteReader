package io.mindjet.litereader.viewmodel.detail.douban;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.jetgear.reactivex.rxbus.RxBus;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemDoubanStaffDetailSummaryBinding;
import io.mindjet.litereader.entity.Constant;
import io.mindjet.litereader.http.SimpleHttpHandler;
import io.mindjet.litereader.reactivex.RxAction;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 影人简介 view model
 * <p>
 * Created by Mindjet on 2017/4/17.
 */

public class StaffDetailSummaryViewModel extends BaseViewModel<ViewInterface<ItemDoubanStaffDetailSummaryBinding>> {

    private boolean hasAttach = false;
    private int maxLine = 2;
    private int fullHeight;
    private boolean folded = true;

    private String id;
    private String prefix = "https://movie.douban.com/celebrity/";

    public StaffDetailSummaryViewModel(String id) {
        this.id = id;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_douban_staff_detail_summary;
    }

    @Override
    public void onViewAttached(View view) {
        if (!hasAttach) {
            hasAttach = true;
            Observable.just("")
                    .compose(new SimpleHttpHandler<String>())
                    .observeOn(Schedulers.io())
                    .map(new Func1<String, String>() {
                        @Override
                        public String call(String s) {
                            return extractContent(prefix + id);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String summary) {
                            if (summary.length() == 2) {
                                getSelfView().getBinding().tvSummary.setText("暂无本影人简介");
                                getSelfView().getBinding().ivToggle.setVisibility(View.GONE);
                            } else {
                                initText(summary);
                            }
                            RxBus.getInstance().send(true, Constant.LOADING_COMPLETE_SIGNAL);       //通知已经加载完毕
                        }
                    }, RxAction.onError());
        }
    }

    private void initText(String summary) {
        final TextView tvSummary = getSelfView().getBinding().tvSummary;
        final ImageView ivToggle = getSelfView().getBinding().ivToggle;
        tvSummary.setVisibility(View.INVISIBLE);
        tvSummary.setText(summary);
        tvSummary.post(new Runnable() {
            @Override
            public void run() {
                fullHeight = tvSummary.getHeight();
                tvSummary.setHeight(tvSummary.getLineHeight() * maxLine);
                ivToggle.setVisibility(tvSummary.getLineCount() > maxLine ? View.VISIBLE : View.GONE);
                folded = tvSummary.getLineCount() > maxLine;
                tvSummary.setVisibility(View.VISIBLE);
            }
        });
        ivToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAnimation(tvSummary, ivToggle);
            }
        });
    }

    private String extractContent(String url) {
        String summary = "";
        try {
            Document doc = Jsoup.connect(url).get();
            Element element = doc.getElementById("intro").child(1);
            if (element.childNodeSize() == 2) {
                Element target = element.getElementsByClass("hidden").get(0);
                summary = target.text();
            } else {
                summary = element.text();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return summary;
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
