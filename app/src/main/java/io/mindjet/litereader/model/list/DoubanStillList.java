package io.mindjet.litereader.model.list;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.mindjet.litereader.model.item.douban.DetailStill;

/**
 * Created by Jet on 3/16/17.
 */

public class DoubanStillList {
    @SerializedName("count")
    public String count;
    @SerializedName("photos")
    public List<DetailStill> photos;
}
