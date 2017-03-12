package io.mindjet.jetgear.mvvm.listener;

import android.databinding.ViewDataBinding;

/**
 * Created by Jet on 2/17/17.
 */

public interface RcvItemClickListener {
    void onItemClick(ViewDataBinding binding, int position);

    void onItemLongClick(ViewDataBinding binding, int position);
}
