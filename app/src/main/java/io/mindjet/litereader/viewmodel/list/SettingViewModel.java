package io.mindjet.litereader.viewmodel.list;

import android.view.ViewGroup;

import io.mindjet.jetgear.databinding.IncludeHeaderRecyclerBinding;
import io.mindjet.jetgear.mvvm.viewinterface.ActivityCompatInterface;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderItemViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.header.HeaderViewModel;
import io.mindjet.jetgear.mvvm.viewmodel.integrated.HeaderRecyclerViewModel;
import io.mindjet.litereader.R;
import io.mindjet.litereader.viewmodel.detail.IncludeSettingViewModel;

/**
 * 设置 view model
 * <p>
 * Created by Mindjet on 2017/4/19.
 */

public class SettingViewModel extends HeaderRecyclerViewModel<ActivityCompatInterface<IncludeHeaderRecyclerBinding>> {

    @Override
    protected void initHeader(ViewGroup container) {
        HeaderViewModel headerViewModel = new HeaderViewModel.Builder()
                .sink(true)
                .background(R.color.colorPrimary)
                .leftViewModel(new HeaderItemViewModel.BackItemViewModel(getSelfView().getCompatActivity()).icon(R.drawable.ic_arrow_left))
                .centerViewModel(new HeaderItemViewModel.TitleItemViewModel(getString(R.string.setting)))
                .build();
        ViewModelBinder.bind(container, headerViewModel);
    }

    @Override
    protected void afterViewAttached() {

    }

    @Override
    protected void afterComponentBound() {
        getRecyclerViewModel().disableLoadMore();
        getRecyclerViewModel().getRecyclerView().setBackgroundResource(R.color.gray_translucent);
        getAdapter().add(new IncludeSettingViewModel());
    }


}
