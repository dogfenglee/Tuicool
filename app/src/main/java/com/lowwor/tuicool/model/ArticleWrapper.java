package com.lowwor.tuicool.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lowworker on 2015/9/20.
 */
public class ArticleWrapper {


    /**
     * site : {"image":"http://stimg0.tuicool.com/JRV3Ub.png","id":"JRV3Ub","title":"Sybase Unwired Platform Developer Center"}
     * like : 0
     * success : true
     * article : {"images":[{"src":"http://img2.tuicool.com/VNZnIn.jpg","w":522,"h":399,"id":"VNZnIn"}],"img":"http://aimg1.tuicool.com/VNZnIn.jpg","topics":[{"image":"http://ttimg2.tuicool.com/11080010.png","name":"安卓开发","id":11080010,"followed":false}],"id":"A7JZZj","time":"09-19 05:06","cmt":0,"title":"Download and Upload application settings from Mobile Platform","lang":2,"feed_title":"Sybase Unwired Platform Developer Center","url":"http://scn.sap.com/community/developer-center/mobility-platform/blog/2015/09/18/download-and-upload-application-settings-from-mobile-platform?utm_source=tuicool","content":"<div> \n <p> During the on-boarding process with the mobile platform (SMP on premise or HCPms), client apps send n> <span> <strong>this<\/strong> <\/span> <span>.<\/span> <span> <strong>error<\/strong> <\/span> <span>=<\/span> <span> pan> stions and comments are welcome,<\/p> \n <p>Claudia<\/p> \n<\/div>"}
     */
    @SerializedName("site")
    private Site site;
    @SerializedName("like")
    private String like;
    @SerializedName("success")
    private boolean success;
    @SerializedName("article")
    private Article article;

    public void setSite(Site site) {
        this.site = site;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Site getSite() {
        return site;
    }

    public String getLike() {
        return like;
    }

    public boolean isSuccess() {
        return success;
    }

    public Article getArticle() {
        return article;
    }

}
