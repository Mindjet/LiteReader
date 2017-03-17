package io.mindjet.litereader.model.list;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.mindjet.litereader.model.item.DoubanMovieItem;

/**
 * Created by Jet on 3/16/17.
 */

public class DoubanMovieList {

    @SerializedName("count")
    public String count;
    @SerializedName("start")
    public String start;
    @SerializedName("total")
    public String total;
    @SerializedName("subjects")
    public List<DoubanMovieItem> movies;
    @SerializedName("title")
    public String title;

}
