package io.mindjet.litereader.model.item.douban;

import com.google.gson.annotations.SerializedName;

/**
 * 详细剧照 实体
 * <p>
 * Created by Jet on 3/16/17.
 */

public class DetailStill {

    @SerializedName("thumb")        //中图
    public String thumb;
    @SerializedName("icon")         //小图
    public String icon;
    @SerializedName("author")
    public Author author;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("album_id")
    public String albumId;
    @SerializedName("cover")
    public String cover;            //方图
    @SerializedName("id")
    public String id;
    @SerializedName("comments_count")
    public String commentsCount;
    @SerializedName("image")        //大图
    public String image;
    @SerializedName("alt")
    public String alt;
    @SerializedName("album_title")
    public String albumTitle;
    @SerializedName("subject_id")
    public String subjectId;
}
