package io.mindjet.litereader.service;

import io.mindjet.litereader.model.list.DailyArticle;
import io.mindjet.litereader.model.other.DailyWallpaper;
import io.mindjet.litereader.model.other.Imdb;
import io.mindjet.litereader.model.other.Me;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Jet on 3/15/17.
 */

public interface OtherService {

    @GET("https://raw.githubusercontent.com/Mindjet/LiteReader/master/app/src/main/assets/me.json")
    Observable<Me> getMe();

    @GET("http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1")
    Observable<DailyWallpaper> getDailyWallpaper();

    @GET("http://interface.meiriyiwen.com/article/today?dev=1")
    Observable<DailyArticle> getDailyArticle();

    @GET("https://interface.meiriyiwen.com/article/random?dev=1")
    Observable<DailyArticle> getRandomArticle();

    /**
     * 获得影片的IMDB评分和IMDB_ID。
     * <p>
     * 例如：http://www.omdbapi.com?t=Beauty and the Beast&y=2017
     *
     * @param originalTitle 影片名，必须是英文名
     * @param year          年份，方便精确查找
     */
    @GET("http://www.omdbapi.com")
    Observable<Imdb> getImdbData(@Query("t") String originalTitle, @Query("y") String year);

    /**
     * 获取影片的烂番茄评分数据。
     *
     * @param name 影片名，必须是英文名且单词用下划线隔开，可能加上"_年份"后缀
     */
    @GET("https://www.rottentomatoes.com/m/{name}")
    Observable<String> getRottenTomatoesData(@Path("name") String name);

}
