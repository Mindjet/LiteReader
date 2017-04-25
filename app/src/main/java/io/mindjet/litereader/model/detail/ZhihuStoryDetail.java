package io.mindjet.litereader.model.detail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jet on 3/13/17.
 */

public class ZhihuStoryDetail implements Serializable {

    /*
    {
      "body": "<div class=\"main-wrap content-wrap\">\n<div class=\"headline\">\n\n<div class=\"img-place-holder\"></div>\n\n\n\n</div>\n\n<div class=\"content-inner\">\n\n\n\n\n<div class=\"question\">\n<h2 class=\"question-title\">北京大学 2010 级古生物专业为何只有一个学生？</h2>\n\n<div class=\"answer\">\n\n<div class=\"meta\">\n<img class=\"avatar\" src=\"http://pic1.zhimg.com/3df2a664c_is.jpg\">\n<span class=\"author\">周诗培</span>\n</div>\n\n<div class=\"content\">\n<p>看到个自己能答的问题了。</p>\r\n<p>首先有人所说的什么北大为一个人开设专业体现情怀什么的，没你们想象的那样高尚。真实情况是，这个专业就录了一个人，就这么简单。</p>\r\n<p>古生物专业属于北大元培学院。元培学院与常见的数学院物理学院中文系之内的院系不同，元培学院并不是一个按照专业划分的学院，而是一个住宿学院。学院里的学生可以自由选择其他院系的专业，例如信科，光华等，同时在选课上比专业院系的同学有更大的自由。除了选择其他院系的专业，元培学生还可以选择本院的三个专业：古生物，政经哲和外语外史。</p>\r\n<p>元培学院自己的专业的特点是，只有元培的学生才能选；课程综合性强，从不同的院系的课程中选取不同的课程组成自己的培养计划。古生物专业就是建立在地空学院，生科院等课程上的。</p>\r\n<p>元培学院的学生在大二上分流，确定自己的专业。这时候如果有人选择古生物，那么这一届古生物就有学生。学生按照培养方案选择课程，然后到所在院系上课。学校并不需要为这个学生专门开一门课程。</p>\r\n<p>报考北京大学元培学院的话，会有北大唯一的本科生男女混住宿舍楼，有不同专业的同学同居一寝的混宿制度。有点不好就是，别人问你学什么专业的时候，你会非常纠结&hellip;&hellip;</p>\r\n<p>利益相关，我是元培学院的学生。</p>\n</div>\n</div>\n\n\n<div class=\"view-more\"><a href=\"http://www.zhihu.com/question/24170880\">查看知乎讨论<span class=\"js-question-holder\"></span></a></div>\n\n</div>\n\n\n</div>\n</div>",
      "image_source": "Yestone.com 版权图片库",
      "title": "不是北大为一个人开专业，而是这个专业只招了一个人",
      "image": "http://p4.zhimg.com/33/ad/33adaf08d180ae6fcfac5faa8db7afe7.jpg",
      "share_url": "http://daily.zhihu.com/story/3977867",
      "js": [],
      "ga_prefix": "061809",
      "images": [
        "http://p1.zhimg.com/dd/c7/ddc75c460475d6e634158a3876d29f9e.jpg"
      ],
      "type": 0,
      "id": 3977867,
      "css": [
        "http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"
      ]
    }
     */

    @SerializedName("body")
    public String body;
    @SerializedName("title")
    public String title;
    @SerializedName("image_source")
    public String imageSource;
    @SerializedName("image")
    public String image;
    @SerializedName("share_url")
    public String shareUrl;
    @SerializedName("images")
    public List<String> images;
    @SerializedName("ga_prefix")
    public String gaPrefix;
    @SerializedName("id")
    public String id;
    @SerializedName("type")
    public String type;

}
