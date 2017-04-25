package io.mindjet.litereader.model.item.douban;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jet on 3/16/17.
 */

// 演职人员实体，可为演员、导演或者作者
public class Staff implements Serializable {
    @SerializedName("avatars")
    public Images avatars;
    @SerializedName("name_en")
    public String nameEn;
    @SerializedName("name")
    public String name;
    @SerializedName("alt")
    public String alt;
    @SerializedName("id")
    public String id;
}
