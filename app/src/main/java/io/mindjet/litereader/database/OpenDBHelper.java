package io.mindjet.litereader.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * SQLite3 工具类
 * <p>
 * Created by Mindjet on 2017/4/25.
 */

public class OpenDBHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "db_litereader";

    public OpenDBHelper(Context context) {
        this(context, DB_NAME, null, 1);
    }

    public OpenDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table tb_collection(" +
                "_id integer primary key autoincrement , " +
                "type text," +
                "id text," +
                "title text," +
                "poster text," +
                "pubdate text," +
                "rating text);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void add(String type, String id, String title, String poster, String pubdate, String rating) {
        String sql = "insert into tb_collection (type,id,title,poster,pubdate,rating) values(?,?,?,?,?,?);";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new Object[]{type, id, title, poster, pubdate, rating});
        database.close();
    }

    public boolean contain(String type, String id) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "select type, id from tb_collection where type = ? AND id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{type, id});
        boolean contain = cursor.getCount() != 0;
        cursor.close();
        db.close();
        return contain;
    }

    public void remove(String type, String id) {
        String sql = "delete from tb_collection where type = ? AND id = ?";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new Object[]{type, id});
        database.close();
    }

    public <T> List<T> getList(String type, Func1<Cursor, T> mapper) {
        List<T> list = new ArrayList<>();
        String sql = "select type,id,title,poster,pubdate,rating from tb_collection where type = ?";
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, new String[]{type});
        while (cursor.moveToNext()) {
            list.add(mapper.call(cursor));
        }
        cursor.close();
        database.close();
        return list;
    }

    public void clear() {
        String sql = "delete * from tb_collection";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
        database.close();
    }

}
