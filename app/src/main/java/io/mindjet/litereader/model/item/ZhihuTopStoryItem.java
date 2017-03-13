package io.mindjet.litereader.model.item;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jet on 3/13/17.
 */

public class ZhihuTopStoryItem {

    /*
      "image": "http://pic4.zhimg.com/53ed0d4314ca415bd8108cd82be1e6a7.jpg",
      "type": 0,
      "id": 9284277,
      "ga_prefix": "031314",
      "title": "咪蒙：网红，病人，潮水的一种方向"
     */

    @SerializedName("image")
    public String image;
    @SerializedName("type")
    public String type;
    @SerializedName("id")
    public String id;
    @SerializedName("ga_prefix")
    public String gaPrefix;
    @SerializedName("title")
    public String title;

}
