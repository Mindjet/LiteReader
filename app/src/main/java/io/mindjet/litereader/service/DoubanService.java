package io.mindjet.litereader.service;

import io.mindjet.litereader.model.detail.DoubanMovieDetail;
import io.mindjet.litereader.model.item.book.BookList;
import io.mindjet.litereader.model.item.douban.StaffDetail;
import io.mindjet.litereader.model.list.DoubanCommentList;
import io.mindjet.litereader.model.list.DoubanMovieList;
import io.mindjet.litereader.model.list.DoubanReviewList;
import io.mindjet.litereader.model.list.DoubanStillList;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 豆瓣接口
 * <p>
 * Created by Jet on 3/13/17.
 */

public interface DoubanService {

    //https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b&city=广州&start=0&count=10
    @GET("https://api.douban.com/v2/movie/in_theaters?apikey=0b2bdeda43b5688921839c8ecb20399b&city=广州")
    Observable<DoubanMovieList> getMovieList(@Query("start") int start, @Query("count") int perPage);

    @GET("http://api.douban.com/v2/movie/subject/{id}?apikey=0b2bdeda43b5688921839c8ecb20399b")
    Observable<DoubanMovieDetail> getMovieDetail(@Path("id") String id);

    @GET("https://api.douban.com/v2/movie/subject/{id}/photos?apikey=0b2bdeda43b5688921839c8ecb20399b")
    Observable<DoubanStillList> getStillList(@Path("id") String id, @Query("start") int start);

    @GET("https://api.douban.com/v2/movie/subject/{id}/comments?apikey=0b2bdeda43b5688921839c8ecb20399b")
    Observable<DoubanCommentList> getCommentList(@Path("id") String id, @Query("start") int start, @Query("count") int perPage);

    @GET("https://api.douban.com/v2/movie/subject/{id}/reviews?apikey=0b2bdeda43b5688921839c8ecb20399b")
    Observable<DoubanReviewList> getReviewList(@Path("id") String id, @Query("start") int start, @Query("count") int perPage);

    @GET("http://api.douban.com/v2/movie/celebrity/{id}")
    Observable<StaffDetail> getStaffDetail(@Path("id") String id);

    @GET("https://api.douban.com/v2/book/search")
    Observable<BookList> getBookList(@Query("tag") String tag, @Query("start") int start, @Query("count") int count);

}
