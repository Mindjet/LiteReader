package io.mindjet.litereader.model.item.douban;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jet on 3/16/17.
 */

//预告片或者花絮实体
public class Trailer {

    @SerializedName("medium")
    public String medium;
    @SerializedName("title")
    public String title;
    @SerializedName("subject_id")
    public String subjectId;
    @SerializedName("alt")
    public String alt;
    @SerializedName("small")
    public String small;
    @SerializedName("resource_url")
    public String resourceUrl;
    @SerializedName("id")
    public String id;

}
