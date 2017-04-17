package io.mindjet.litereader.model.item.douban;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 演职员详情
 * <p>
 * Created by Mindjet on 2017/4/17.
 */

public class StaffDetail {

    @SerializedName("name")
    public String name;
    @SerializedName("works")
    public List<Work> works;
    @SerializedName("name_en")
    public String nameEn;
    @SerializedName("born_place")
    public String bornPlace;
    @SerializedName("gender")
    public String gender;
    @SerializedName("mobile_url")
    public String mobileUrl;
    @SerializedName("avatars")
    public Images avatars;

}
