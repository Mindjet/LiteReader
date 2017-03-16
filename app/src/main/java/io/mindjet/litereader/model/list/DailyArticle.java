package io.mindjet.litereader.model.list;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jet on 3/16/17.
 */

public class DailyArticle {

    @SerializedName("data")
    public Data data;

    public class Data {

        @SerializedName("author")
        public String author;
        @SerializedName("title")
        public String title;
        @SerializedName("digest")
        public String digest;
        @SerializedName("content")
        public String content;
        @SerializedName("wc")
        public String wordCount;

    }

}
