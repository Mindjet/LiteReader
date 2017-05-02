package io.mindjet.litereader.util;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import io.mindjet.litereader.database.OpenDBHelper;
import io.mindjet.litereader.model.detail.DoubanMovieDetail;
import io.mindjet.litereader.model.detail.ZhihuStoryDetail;
import io.mindjet.litereader.model.item.DoubanMovieItem;
import io.mindjet.litereader.model.item.ZhihuStoryItem;
import io.mindjet.litereader.model.item.douban.Images;
import io.mindjet.litereader.model.item.douban.Rating;
import rx.functions.Func1;

/**
 * 收藏工具类
 * <p>
 * Created by Mindjet on 2017/4/25.
 */

public class CollectionManager {

    public final static String COLLECTION_TYPE_DOUBAN_MOVIE = "collection_type_douban_movie";
    public final static String COLLECTION_TYPE_ZHIHU_STORY = "collection_type_zhihu_story";
    private static CollectionManager manager;
    private OpenDBHelper dbHelper;

    private CollectionManager(Context context) {
        dbHelper = new OpenDBHelper(context);
    }

    public static CollectionManager getInstance(Context context) {
        if (manager == null) {
            synchronized (CollectionManager.class) {
                if (manager == null) {
                    manager = new CollectionManager(context);
                }
            }
        }
        return manager;
    }

    public void collect(ZhihuStoryDetail detail) {
        dbHelper.add(COLLECTION_TYPE_ZHIHU_STORY, detail.id, detail.title, detail.image, " ", " ");
    }

    public void collect(DoubanMovieDetail detail) {
        dbHelper.add(COLLECTION_TYPE_DOUBAN_MOVIE, detail.id, detail.title, detail.images.large, detail.pubdate, detail.rating.average);
    }

    public void remove(String id, @Source String type) {
        dbHelper.remove(type, id);
    }

    public boolean contain(String id, @Source String type) {
        return dbHelper.contain(type, id);
    }

    public List<DoubanMovieItem> getDoubanMovieList() {
        return dbHelper.getList(COLLECTION_TYPE_DOUBAN_MOVIE, new Func1<Cursor, DoubanMovieItem>() {
            @Override
            public DoubanMovieItem call(Cursor cursor) {
                DoubanMovieItem item = new DoubanMovieItem();
                item.id = cursor.getString(1);
                item.title = cursor.getString(2);
                item.images = new Images();
                item.images.large = cursor.getString(3);
                item.mainlandPubdate = cursor.getString(4);
                item.rating = new Rating();
                item.rating.average = cursor.getString(5);
                return item;
            }
        });
    }

    public List<ZhihuStoryItem> getZhihuStoryList() {
        return dbHelper.getList(COLLECTION_TYPE_ZHIHU_STORY, new Func1<Cursor, ZhihuStoryItem>() {
            @Override
            public ZhihuStoryItem call(Cursor cursor) {
                ZhihuStoryItem item = new ZhihuStoryItem();
                item.id = cursor.getString(1);
                item.title = cursor.getString(2);
                item.images = new ArrayList<>();
                item.images.add(cursor.getString(3));
                return item;
            }
        });
    }


    @Retention(RetentionPolicy.SOURCE)
    @StringDef({COLLECTION_TYPE_DOUBAN_MOVIE, COLLECTION_TYPE_ZHIHU_STORY})
    public @interface Source {

    }

}
