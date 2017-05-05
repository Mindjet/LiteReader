package io.mindjet.litereader.model.item.one;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * ONE文章 详情 实体
 * <p>
 * Created by Mindjet on 5/5/17.
 */

public class ArticleDetail {

    @SerializedName("content_id")
    public String id;
    @SerializedName("hp_title")
    public String title;
    @SerializedName("hp_content")
    public String content;
    @SerializedName("last_update_date")
    public String updateDate;
    @SerializedName("guide_word")
    public String guideWord;
    public List<Author> author;
    @SerializedName("web_url")
    public String webUrl;
    @SerializedName("hp_author_introduce")
    public String authorIntroduce;
    @SerializedName("praisenum")
    public String praiseNum;

}
