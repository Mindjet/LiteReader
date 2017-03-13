package io.mindjet.litereader.model.list;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.mindjet.litereader.model.item.ZhihuStoryItem;
import io.mindjet.litereader.model.item.ZhihuTopStoryItem;

/**
 * Created by Jet on 3/13/17.
 */

public class ZhihuDailyList {

    @SerializedName("date")
    public String date;
    @SerializedName("stories")
    public List<ZhihuStoryItem> stories;
    @SerializedName("top_stories")
    public List<ZhihuTopStoryItem> topStories;

}
