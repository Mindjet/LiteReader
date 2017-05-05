package io.mindjet.litereader.model.item.one;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 作者 实体
 * <p>
 * Created by Mindjet on 5/4/17.
 */

public class Author implements Serializable {

    /**
     * user_id : 6080525
     * user_name : 丹丹扬
     * desc : 她和时间跳华尔兹。
     * wb_name :
     * is_settled : 0
     * settled_type : 0
     * summary : 她和时间跳华尔兹。
     * fanNum : 835
     * web_url : http://image.wufazhuce.com/FhqPpjWkOEWCtqK3tPCAVpkAtALp
     */

    @SerializedName("user_id")
    public String id;
    @SerializedName("user_name")
    public String name;
    @SerializedName("desc")
    public String signature;
    @SerializedName("web_url")
    public String avatar;
    @SerializedName("fans_total")
    public String fanNum;
    @SerializedName("wb_name")
    public String WbName;
}
