package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import com.bumptech.glide.Glide;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.jetutil.hint.Toaster;
import io.mindjet.litereader.BuildConfig;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemAboutInfoBinding;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class AboutInfoViewModel extends BaseViewModel<ViewInterface<ItemAboutInfoBinding>> {

    @Override
    public void onViewAttached(View view) {
        Glide.with(getContext())
                .load(R.drawable.ic_launcher)
                .bitmapTransform(new RoundedCornersTransformation(getContext(), 100, 0))
                .into(getSelfView().getBinding().ivAppIcon);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_about_info;
    }

    public String getVersion() {
        return BuildConfig.VERSION_NAME;
    }

    public void onVersionClick(View v) {
        Toaster.toast(getContext(), "checking version");
    }

}
