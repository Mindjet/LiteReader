package io.mindjet.litereader.adapter;

import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import io.mindjet.jetgear.adapter.ViewPagerAdapter;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.jetgear.mvvm.viewmodel.list.SwipeRecyclerViewModel;

/**
 * Created by Jet on 3/21/17.
 */

public class ReviewViewPagerAdapter extends ViewPagerAdapter<SwipeRecyclerViewModel> {

    @Override
    protected Object initItem(ViewGroup container, SwipeRecyclerViewModel item, String title, int position) {
        ViewDataBinding binding = ViewModelBinder.bind(container, item);
        return binding.getRoot();
    }
}
