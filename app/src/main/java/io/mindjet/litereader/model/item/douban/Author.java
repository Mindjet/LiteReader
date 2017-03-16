package io.mindjet.litereader.model.item.douban;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jet on 3/16/17.
 */

// 用户实体
public class Author {

    @SerializedName("uid")
    public String uid;
    @SerializedName("avatar")
    public String avatar;
    @SerializedName("signature")
    public String signature;
    @SerializedName("alt")
    public String alt;
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;

}
