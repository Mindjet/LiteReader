package io.mindjet.jetgear.mvvm.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import io.mindjet.jetutil.logger.JLogger;

/**
 * Created by Jet on 2/10/17.
 */

public abstract class BaseAdapter<T, V extends ViewDataBinding> extends RecyclerView.Adapter<BaseViewHolder<V>> implements List<T> {

    protected JLogger jLogger = JLogger.get(getClass().getSimpleName());
    private LayoutInflater inflater;

    public BaseAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public BaseViewHolder<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        V binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        return new BaseViewHolder<V>(binding);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<V> holder, int position) {
        onBindVH(holder, position);
        holder.itemView.setTag(holder);
        holder.bind(get(position));
    }

    public abstract void onBindVH(BaseViewHolder<V> holder, int position);

    @Override
    public int getItemViewType(int position) {
        return getItemLayoutId(position);
    }

    public abstract int getItemLayoutId(int position);

    public LayoutInflater getInflater() {
        return inflater;
    }

}
