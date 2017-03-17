package io.mindjet.litereader.model.list;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.mindjet.litereader.model.item.douban.Review;

/**
 * Created by Jet on 3/16/17.
 */

public class DoubanReviewList {

    @SerializedName("count")
    public String count;
    @SerializedName("reviews")
    public List<Review> reviews;
    @SerializedName("total")
    public String total;
    @SerializedName("next_start")
    public String nextStart;

}
