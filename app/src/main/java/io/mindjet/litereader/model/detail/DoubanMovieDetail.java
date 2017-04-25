package io.mindjet.litereader.model.detail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import io.mindjet.litereader.model.item.douban.Comment;
import io.mindjet.litereader.model.item.douban.Images;
import io.mindjet.litereader.model.item.douban.Rating;
import io.mindjet.litereader.model.item.douban.Review;
import io.mindjet.litereader.model.item.douban.Staff;
import io.mindjet.litereader.model.item.douban.Still;
import io.mindjet.litereader.model.item.douban.Trailer;

/**
 * Created by Jet on 3/16/17.
 */

public class DoubanMovieDetail implements Serializable{

    @SerializedName("rating")
    public Rating rating;
    @SerializedName("reviews_count")
    public String reviewsCount;
    @SerializedName("wish_count")
    public String wishCount;
    @SerializedName("original_title")
    public String originalTitle;
    @SerializedName("collect_count")
    public String collectCount;
    @SerializedName("images")
    public Images images;
    @SerializedName("year")
    public String year;
    @SerializedName("popular_comments")         //很短的影评没什么用
    public List<Comment> popularComments;
    @SerializedName("id")
    public String id;
    @SerializedName("mobile_url")
    public String mobileUrl;
    @SerializedName("photos_count")
    public String photosCount;
    @SerializedName("pubdate")
    public String pubdate;
    @SerializedName("title")
    public String title;
    @SerializedName("share_url")
    public String shareUrl;
    @SerializedName("languages")
    public List<String> languages;
    @SerializedName("writers")
    public List<Staff> writers;
    @SerializedName("pubdates")
    public List<String> pubdates;
    @SerializedName("tags")
    public List<String> tags;
    @SerializedName("durations")
    public List<String> durations;
    @SerializedName("genres")
    public List<String> genres;
    @SerializedName("trailers")
    public List<Trailer> trailers;
    @SerializedName("trailer_urls")
    public List<String> trailerUrls;
    @SerializedName("bloopers")
    public List<Trailer> bloopers;
    @SerializedName("clip_urls")
    public List<String> clipUrls;
    @SerializedName("casts")
    public List<Staff> actors;
    @SerializedName("countries")
    public List<String> countries;
    @SerializedName("photos")
    public List<Still> photos;
    @SerializedName("summary")
    public String summary;
    @SerializedName("clips")
    public List<Trailer> clips;
    @SerializedName("directors")
    public List<Staff> directors;
    @SerializedName("comments_count")
    public String commentsCount;
    @SerializedName("popular_reviews")          //长影评
    public List<Review> popularReviews;
    @SerializedName("ratings_count")
    public String ratingsCount;
}

