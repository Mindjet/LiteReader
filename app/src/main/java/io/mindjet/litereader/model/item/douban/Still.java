package io.mindjet.litereader.model.item.douban;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jet on 3/16/17.
 */

/**
 * 剧照实体，在获取影片详情时一并获得。
 * <p>
 * 专门获得的剧照实体是 {@link DetailStill}
 */
public class Still {
    @SerializedName("thumb")        //中图
    public String thumb;
    @SerializedName("image")        //大图
    public String image;
    @SerializedName("cover")        //方形
    public String cover;
    @SerializedName("alt")
    public String alt;
    @SerializedName("id")
    public String id;
    @SerializedName("icon")         //小图
    public String icon;
}
