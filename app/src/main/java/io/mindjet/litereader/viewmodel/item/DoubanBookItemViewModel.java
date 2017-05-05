package io.mindjet.litereader.viewmodel.item;

import android.view.View;

import io.mindjet.jetgear.mvvm.base.BaseViewModel;
import io.mindjet.jetgear.mvvm.viewinterface.ViewInterface;
import io.mindjet.litereader.R;
import io.mindjet.litereader.databinding.ItemDoubanBookBinding;
import io.mindjet.litereader.model.item.book.Book;

/**
 * 豆瓣图书 item view model
 * <p>
 * Created by Mindjet on 5/5/17.
 */

public class DoubanBookItemViewModel extends BaseViewModel<ViewInterface<ItemDoubanBookBinding>> {

    private Book book;

    public DoubanBookItemViewModel(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_douban_book;
    }

    @Override
    public void onViewAttached(View view) {

    }
}
