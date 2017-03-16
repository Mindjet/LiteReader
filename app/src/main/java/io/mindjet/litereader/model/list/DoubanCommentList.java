package io.mindjet.litereader.model.list;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.mindjet.litereader.model.item.douban.Comment;

/**
 * Created by Jet on 3/16/17.
 */

public class DoubanCommentList {

    @SerializedName("count")
    public String count;
    @SerializedName("comments")
    public List<Comment> comments;
    @SerializedName("total")
    public String total;

}
