package io.mindjet.jetgear.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jet on 3/1/17.
 */

public abstract class ViewPagerAdapter<T> extends PagerAdapter {

    List<T> list = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    public void add(T item) {
        addWithTitle(item, "");
    }

    public void addAll(List<T> items) {
        for (T item : items)
            addWithTitle(item, "");
        notifyDataSetChanged();
    }

    public void addWithTitle(T item, String title) {
        list.add(item);
        titles.add(title);
        notifyDataSetChanged();
    }

    public void clear(){
        list.clear();
        titles.clear();
    }

    public void addAllWithTitles(List<T> items, List<String> titles) {
        this.list.addAll(items);
        this.titles.addAll(titles);
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return list.get(position);
    }

    public String getTitle(int position) {
        return titles.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return initItem(container, getItem(position), getTitle(position), position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getTitle(position);
    }

    protected abstract Object initItem(ViewGroup container, T item, String title, int position);

}
