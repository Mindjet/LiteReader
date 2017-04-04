package io.mindjet.litereader.ui.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;

import io.mindjet.jetgear.base.BaseDialog;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.DialogMovieReviewBinding;

/**
 * Created by Mindjet on 2017/4/4.
 */

public class MovieReviewDialog extends BaseDialog {

    private DialogMovieReviewBinding binding;
    private String content;

    public MovieReviewDialog(@NonNull Context context, String content) {
        super(context);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    protected void beforeInitView() {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_movie_review, null, false);
        setContentView(binding.getRoot());
        binding.setData(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
