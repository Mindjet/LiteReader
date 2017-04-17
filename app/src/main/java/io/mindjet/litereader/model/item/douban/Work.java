package io.mindjet.litereader.model.item.douban;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.mindjet.litereader.model.item.DoubanMovieItem;

/**
 * 演职员作品
 * <p>
 * Created by Mindjet on 2017/4/17.
 */

public class Work {

    @SerializedName("roles")
    public List<String> roles;
    @SerializedName("subject")
    public DoubanMovieItem subject;

}
