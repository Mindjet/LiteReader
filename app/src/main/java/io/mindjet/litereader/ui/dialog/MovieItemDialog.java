package io.mindjet.litereader.ui.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import io.mindjet.jetgear.base.BaseDialog;
import io.mindjet.jetgear.mvvm.viewmodel.ViewModelBinder;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.DialogMovieItemBinding;
import io.mindjet.litereader.model.item.DoubanMovieItem;
import io.mindjet.litereader.viewmodel.item.MovieTypeViewModel;
import rx.functions.Action1;

/**
 * Created by Jet on 3/20/17.
 */

public class MovieItemDialog extends BaseDialog {

    private DialogMovieItemBinding binding;
    private DoubanMovieItem item;
    private Action1<DoubanMovieItem> onAction;

    public MovieItemDialog(@NonNull Context context, DoubanMovieItem item) {
        super(context);
        this.item = item;
    }

    public MovieItemDialog onAction(Action1<DoubanMovieItem> onAction) {
        this.onAction = onAction;
        return this;
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

    public String getPubdate() {
        return "上映时间：" + item.mainlandPubdate;
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

    public View.OnClickListener getClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAction != null) {
                    onAction.call(item);
                }
            }
        };
    }

}
