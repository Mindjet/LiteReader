package io.mindjet.litereader.model.item;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jet on 3/15/17.
 */

public class ZhihuSectionItem {

    /*
      "description": "看别人的经历，理解自己的生活",
      "id": 1,
      "name": "深夜惊奇",
      "thumbnail": "http://pic3.zhimg.com/91125c9aebcab1c84f58ce4f8779551e.jpg"
     */
    @SerializedName("description")
    public String description;
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("thumbnail")
    public String thumbnail;

}

