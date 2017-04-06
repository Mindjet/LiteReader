package io.mindjet.litereader.model.item.douban;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jet on 3/16/17.
 */

/**
 * 短影评实体，长影评实体为 {@link Review}
 */

public class Comment implements Serializable{

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
    @SerializedName("id")
    public String id;

    public class Rating implements Serializable {
        @SerializedName("max")
        public String max;
        @SerializedName("value")
        public String value;
        @SerializedName("min")
        public String min;
    }
}
