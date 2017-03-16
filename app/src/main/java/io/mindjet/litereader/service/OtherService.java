package io.mindjet.litereader.service;

import io.mindjet.litereader.model.list.DailyArticle;
import io.mindjet.litereader.model.other.DailyWallpaper;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Jet on 3/15/17.
 */

public interface OtherService {

    @GET("http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1")
    Observable<DailyWallpaper> getDailyWallpaper();

    @GET("https://interface.meiriyiwen.com/article/today?dev=1")
    Observable<DailyArticle> getDailyArticle();

    @GET("https://interface.meiriyiwen.com/article/random?dev=1")
    Observable<DailyArticle> getRandomArticle();

}
