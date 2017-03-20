package io.mindjet.jetgear.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;

import io.mindjet.jetutil.logger.JLogger;

/**
 * Created by Jet on 3/9/17.
 */

public abstract class BaseBottomSheetDialog extends BottomSheetDialog {

    protected JLogger jLogger = JLogger.get(getClass().getSimpleName());

    public BaseBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        beforeInitView();
        initView();
        initListener();
        initData();
        super.onCreate(savedInstanceState);
    }

    protected abstract void beforeInitView();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

}
