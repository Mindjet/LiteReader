package io.mindjet.litereader.service;

import io.mindjet.litereader.model.detail.ZhihuStoryDetail;
import io.mindjet.litereader.model.list.ZhihuDailyList;
import io.mindjet.litereader.model.list.ZhihuSectionList;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Jet on 3/13/17.
 */

public interface ZhihuDailyService {

    @GET("http://news-at.zhihu.com/api/4/news/latest")
    Observable<ZhihuDailyList> getLatest();

    /**
     * 获取某一天的前一天的日报，比如要获取9.20的日报，则应传入9.21
     *
     * @param date 日期，格式为yyyyMMdd
     */
    @GET("http://news-at.zhihu.com/api/4/news/before/{date}")
    Observable<ZhihuDailyList> getBefore(@Path("date") String date);

    @GET("http://news-at.zhihu.com/api/4/news/{id}")
    Observable<ZhihuStoryDetail> getStoryDetail(@Path("id") String id);

    @GET("http://news-at.zhihu.com/api/4/sections")
    Observable<ZhihuSectionList> getSections();

    @GET("http://news-at.zhihu.com/api/4/section/{id}")
    Observable<ZhihuDailyList> getSectionDetail(@Path("id") String id);


}
