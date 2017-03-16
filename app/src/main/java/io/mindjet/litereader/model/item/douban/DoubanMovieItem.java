package io.mindjet.litereader.model.item.douban;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jet on 3/16/17.
 */

public class DoubanMovieItem {

    @SerializedName("rating")
    public Rating rating;
    @SerializedName("genres")
    public List<String> genres;
    @SerializedName("title")
    public String title;
    @SerializedName("casts")
    public List<Staff> actors;
    @SerializedName("duration")
    public List<String> duration;
    @SerializedName("collect_count")
    public String collectCount;
    @SerializedName("mainland_pubdate")
    public String mainlandPubdate;
    @SerializedName("has_video")
    public boolean hasVideo;
    @SerializedName("original_title")
    public String originalTitle;
    @SerializedName("directors")
    public List<Staff> directors;
    @SerializedName("pubdates")
    public List<String> pubdates;
    @SerializedName("year")
    public String year;
    @SerializedName("images")
    public Images images;
    @SerializedName("alt")
    public String alt;
    @SerializedName("id")
    public String id;

}
