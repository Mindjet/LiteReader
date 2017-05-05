package io.mindjet.litereader.service;

import io.mindjet.litereader.model.item.one.Article;
import io.mindjet.litereader.model.item.one.ArticleDetail;
import io.mindjet.litereader.model.item.one.Review;
import io.mindjet.litereader.model.list.OneData;
import io.mindjet.litereader.model.list.OneDataList;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * ONE 接口
 * <p>
 * Created by Mindjet on 3/13/17.
 */

public interface OneService {

    @GET("http://v3.wufazhuce.com:8000/api/channel/movie/more/0")
    Observable<OneDataList<Review>> getReviewList();

    @GET("http://v3.wufazhuce.com:8000/api/channel/reading/more/0")
    Observable<OneDataList<Article>> getArticleList();

    @GET("http://v3.wufazhuce.com:8000/api/essay/{id}")
    Observable<OneData<ArticleDetail>> getArticleDetail(@Path("id") String articleId);

}
