package io.mindjet.litereader.model.other;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jet on 3/17/17.
 */

public class Imdb {

    @SerializedName("Rated") //评级
    private String rated;
    @SerializedName("Metascore")   //   Metacritic 网站评分, Metascores 范围从0-100，数值越高，越受欢迎
    public String metascore;
    @SerializedName("imdbRating")
    public String imdbRating;
    @SerializedName("imdbVotes")
    public String imdbVotes;
    @SerializedName("imdbID")
    public String imdbId;

}