package io.mindjet.litereader.model.item.one;

import com.google.gson.annotations.SerializedName;

/**
 * ONE文章 实体
 * <p>
 * Created by Jet on 5/5/17.
 */

public class Article {
    @SerializedName("item_id")
    public String id;
    public String title;
    public String forward;
    @SerializedName("img_url")
    public String imgUrl;
    @SerializedName("like_count")
    public String likeCount;
    @SerializedName("post_date")
    public String postDate;
    public Author author;
    @SerializedName("share_info")
    public ShareInfo shareInfo;
    @SerializedName("share_url")
    public String shareUrl;
}
