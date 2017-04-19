package io.mindjet.jetgear.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;

import io.mindjet.jetgear.BR;
import io.mindjet.jetgear.R;
import io.mindjet.jetutil.logger.JLogger;

/**
 * Base bottom sheet dialog, easy apis to custom dialog are exposed.
 * <p>
 * Created by Jet on 3/9/17.
 */

public abstract class BaseBottomSheetDialog<V extends ViewDataBinding> extends BottomSheetDialog {

    protected JLogger jLogger = JLogger.get(getClass().getSimpleName());
    protected V binding;

    public BaseBottomSheetDialog(@NonNull Context context) {
        super(context);
        binding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), null, false);
        setContentView(binding.getRoot());
        binding.setVariable(BR.data, this);
        initView();
        initListener();
        initData();
    }


    public V getBinding() {
        return binding;
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected void initView() {
        View view = findViewById(R.id.design_bottom_sheet);
        view.setBackgroundColor(getContext().getResources().getColor(R.color.transparent));
    }

    protected void initListener() {

    }

    protected void initData() {

    }

}
