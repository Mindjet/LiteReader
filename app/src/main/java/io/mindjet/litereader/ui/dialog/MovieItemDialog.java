package io.mindjet.litereader.ui.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.ViewGroup;
import android.view.Window;

import io.mindjet.jetgear.base.BaseDialog;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.DialogMovieItemBinding;
import io.mindjet.litereader.model.item.DoubanMovieItem;
import io.mindjet.litereader.viewmodel.item.MovieTypeViewModel;

/**
 * Created by Jet on 3/20/17.
 */

public class MovieItemDialog extends BaseDialog {

    private DialogMovieItemBinding binding;
    private DoubanMovieItem item;

    public MovieItemDialog(@NonNull Context context, DoubanMovieItem item) {
        super(context);
        this.item = item;
    }

    public String getPoster() {
        return item.images.large;
    }

    public String getTitle() {
        return item.title;
    }

    public String getOriginalTitle() {
        return item.originalTitle;
    }

    public Spanned getRating() {
        String text = item.rating.average + "/" + item.rating.max;
        Spannable builder = new SpannableStringBuilder(text);
        int start = text.indexOf('/');
        builder.setSpan(new AbsoluteSizeSpan(60), 0, start, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.colorPrimary)), 0, start, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return builder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInitView();
        initView();
        initData();
    }

    @Override
    protected void beforeInitView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_movie_item, null, false);
        setContentView(binding.getRoot());
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        binding.setData(this);
    }

    @Override
    protected void initView() {
        for (String genre : item.genres) {
            ViewModelBinder.bind(binding.llyGenres, new MovieTypeViewModel(genre));
        }
    }

    @Override
    protected void initData() {

    }


}
