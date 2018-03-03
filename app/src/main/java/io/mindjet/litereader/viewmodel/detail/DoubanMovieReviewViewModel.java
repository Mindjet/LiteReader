package io.mindjet.litereader.viewmodel.detail;

import android.view.ViewGroup;

import io.mindjet.jetgear.databinding.IncludeHeaderRecyclerBinding;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityCompatInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderItemViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.header.IHeaderItemCallback;
import io.mindjet.jetgear.mvvm.viewmodel.integrated.HeaderRecyclerViewModel;
import io.mindjet.litereader.R;
import io.mindjet.litereader.model.item.douban.Review;
import io.mindjet.litereader.ui.dialog.ShareDialog;
import io.mindjet.litereader.viewmodel.item.IncludeDoubanMovieReviewViewModel;

/**
 * 长影评 view model
 * <p>
 * Created by Mindjet on 2017/4/6.
 */

public class DoubanMovieReviewViewModel extends HeaderRecyclerViewModel<ActivityCompatInterface<IncludeHeaderRecyclerBinding>> {

    private final String PREFIX = "https://movie.douban.com/review/";

    private Review review;
    private String title;

    public DoubanMovieReviewViewModel(Review review, String title) {
        this.review = review;
        this.title = title;
    }

    @Override
    protected void initHeader(ViewGroup container) {
        HeaderViewModel header = new HeaderViewModel.Builder()
                .sink(true)
                .leftViewModel(new HeaderItemViewModel.BackItemViewModel(getSelfView().getCompatActivity()).icon(R.drawable.ic_arrow_left))
                .leftViewModel(new HeaderItemViewModel.TitleItemViewModel(title == null ? getString(R.string.douban_review_list) : title))
                .rightViewModel(new HeaderItemViewModel()
                        .icon(R.drawable.ic_share)
                        .clickable(true)
                        .callback(new IHeaderItemCallback() {
                            @Override
                            public void call() {
                                new ShareDialog(getContext(), review.title + " " + PREFIX + review.id, false).show();
                            }
                        }))
                .background(R.color.colorPrimary)
                .build();
        ViewModelBinder.bind(container, header);
    }

    @Override
    protected void afterComponentBound() {
        getAdapter().disableLoadMore();
        getRecyclerViewModel().getRecyclerView().setBackgroundResource(R.color.gray_light_translucent);
        getAdapter().add(new IncludeDoubanMovieReviewViewModel(review));
    }
}
