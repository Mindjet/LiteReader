package io.mindjet.litereader.model.item.douban;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jet on 3/16/17.
 */

/**
 * 长影评实体，短影评实体为 {@link Comment}
 */

public class Review implements Serializable{

    @SerializedName("rating")
    public Rating rating;
    @SerializedName("useful_count")
    public String usefulCount;
    @SerializedName("author")
    public Author author;
    @SerializedName("subject_id")       //被评论电影的id
    public String subjectId;
    @SerializedName("content")
    public String content;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("title")
    public String title;
    @SerializedName("updated_at")
    public String updatedAt;
    @SerializedName("summary")
    public String summary;
    @SerializedName("id")
    public String id;
    @SerializedName("useless_count")
    public String uselessCount;
    @SerializedName("share_url")
    public String shareUrl;
    @SerializedName("alt")
    public String alt;

    public class Rating implements Serializable{
        @SerializedName("max")
        public String max;
        @SerializedName("value")
        public String value;
        @SerializedName("min")
        public String min;
    }

}
