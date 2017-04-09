package io.mindjet.litereader.viewmodel.detail;

import android.view.ViewGroup;

import io.mindjet.jetgear.databinding.IncludeHeaderRecyclerBinding;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityCompatInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderItemViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.integrated.HeaderRecyclerViewModel;
import io.mindjet.litereader.R;
import io.mindjet.litereader.model.item.douban.Review;
import io.mindjet.litereader.viewmodel.item.IncludeDoubanMovieReviewViewModel;

/**
 * Created by Mindjet on 2017/4/6.
 */

public class DoubanMovieReviewViewModel extends HeaderRecyclerViewModel<ActivityCompatInterface<IncludeHeaderRecyclerBinding>> {

    private Review review;

    public DoubanMovieReviewViewModel(Review review) {
        this.review = review;
    }

    @Override
    protected void initHeader(ViewGroup container) {
        HeaderViewModel header = new HeaderViewModel.Builder()
                .sink(true)
                .leftViewModel(new HeaderItemViewModel.BackItemViewModel(getSelfView().getCompatActivity()).icon(R.drawable.ic_arrow_left))
                .leftViewModel(new HeaderItemViewModel.TitleItemViewModel(getString(R.string.douban_review_list)))
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
