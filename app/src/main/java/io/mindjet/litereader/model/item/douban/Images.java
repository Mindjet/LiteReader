package io.mindjet.litereader.model.item.douban;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jet on 3/16/17.
 */

// 演职员头像、电影海报实体
public class Images {
    @SerializedName("small")
    public String small;
    @SerializedName("large")
    public String large;
    @SerializedName("medium")
    public String medium;
}