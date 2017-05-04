package io.mindjet.litereader.model.item.one;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 影评实体
 * <p>
 * Created by Jet on 5/4/17.
 */

public class Review implements Serializable {

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
    public String subtitle;     //影片名
    @SerializedName("share_url")
    public String shareUrl;
    @SerializedName("share_info")
    public ShareInfo shareInfo;

}
