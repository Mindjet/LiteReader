package io.mindjet.litereader.model.item.douban;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jet on 3/16/17.
 */

public class Rating implements Serializable {

    @SerializedName("max")
    public String max;
    @SerializedName("average")
    public String average;
    @SerializedName("stars")
    public String stars;

}