package io.mindjet.litereader.service;

import io.mindjet.litereader.model.item.one.ReviewList;
import retrofit2.http.GET;
import rx.Observable;

/**
 * ONE 接口
 * <p>
 * Created by Mindjet on 3/13/17.
 */

public interface OneService {

    @GET("http://v3.wufazhuce.com:8000/api/channel/movie/more/0?channel=wdj&version=4.0.2&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android")
    Observable<ReviewList> getReviewList();

}
