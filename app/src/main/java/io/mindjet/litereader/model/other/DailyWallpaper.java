package io.mindjet.litereader.model.other;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jet on 3/15/17.
 */

public class DailyWallpaper {

    @SerializedName("images")
    public List<Data> data;

    public class Data {
        @SerializedName("url")
        public String url;
        @SerializedName("copyright")
        public String copyright;
    }

}
