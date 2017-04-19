package io.mindjet.litereader.ui.dialog;

import android.content.Context;
import android.support.annotation.NonNull;

import io.mindjet.jetgear.base.BaseBottomSheetDialog;
import io.mindjet.jetutil.manager.ShareManager;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.DialogShareBinding;

/**
 * 分享 dialog
 * <p>
 * Created by Jet on 4/19/17.
 */

public class ShareDialog extends BaseBottomSheetDialog<DialogShareBinding> {

    private String content;

    private boolean isImage;

    public ShareDialog(@NonNull Context context, String content, boolean isImage) {
        super(context);
        this.content = content;
        this.isImage = isImage;

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_share;
    }

    public void onWechat() {
        ShareManager.with(getContext())
                .shareWechat(content);
        onCancel();
    }

    public void onQQ() {
        ShareManager.with(getContext())
                .shareQQ(content);
        onCancel();
    }

    public void onQzone() {
        ShareManager.with(getContext())
                .shareQzone(content);
        onCancel();
    }

    public void onWeibo() {
        ShareManager.with(getContext())
                .shareWeibo(content);
        onCancel();
    }

    public void onCancel() {
        dismiss();
    }

}
