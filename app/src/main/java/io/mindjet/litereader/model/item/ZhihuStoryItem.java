package io.mindjet.litereader.model.item;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jet on 3/13/17.
 */

public class ZhihuStoryItem {
    /*
      "title": "人工智能又来抢人类的饭碗了，这次是插画师",
      "ga_prefix": "031316",
      "images": [
        "http://pic3.zhimg.com/ae7d3c30accadbec5b7e6b561e025a6e.jpg"
      ],
      "multipic": true,
      "type": 0,
      "id": 9284316
     */
    @SerializedName("title")
    public String title;
    @SerializedName("ga_prefix")
    public String gaPrefix;
    @SerializedName("images")
    public List<String> images;
    @SerializedName("multipic")
    public boolean multiPic;
    @SerializedName("type")
    public String type;
    @SerializedName("id")
    public String id;

}
