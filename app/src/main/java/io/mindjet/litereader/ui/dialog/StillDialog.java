package io.mindjet.litereader.ui.dialog;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.view.Window;

import io.mindjet.jetgear.base.BaseDialog;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.DialogStillBinding;
import io.mindjet.litereader.model.item.douban.DetailStill;
import io.mindjet.litereader.model.item.douban.Still;

/**
 * 剧照大图弹窗
 * <p>
 * Created by Mindjet on 2017/5/3.
 */

public class StillDialog extends BaseDialog {

    private DialogStillBinding binding;
    private DetailStill detailStill;
    private Still still;

    public StillDialog(@NonNull Context context, DetailStill detailStill) {
        super(context);
        this.detailStill = detailStill;
    }

    public StillDialog(Context context, Still still) {
        super(context);
        this.still = still;
    }

    public boolean showAuthor() {
        return detailStill != null;
    }

    public String getImage() {
        return detailStill == null ? still.image : detailStill.image;
    }

    public String getName() {
        return detailStill == null ? "" : detailStill.author.name;
    }

    public String getTime() {
        return detailStill == null ? "" : detailStill.createdAt;
    }

    public String getAvatar() {
        return detailStill == null ? "" : detailStill.author.avatar;
    }

    public String getSignature() {
        return detailStill == null ? "" : detailStill.author.signature;
    }

    @Override
    protected void beforeInitView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_still, null, false);
        setContentView(binding.getRoot());
        binding.setData(this);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
