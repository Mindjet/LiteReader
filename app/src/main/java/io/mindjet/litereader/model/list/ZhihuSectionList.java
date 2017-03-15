package io.mindjet.litereader.model.list;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.mindjet.litereader.model.item.ZhihuSectionItem;

/**
 * Created by Jet on 3/15/17.
 */

public class ZhihuSectionList {
    @SerializedName("data")
    public List<ZhihuSectionItem> data;
}
