package com.lowwor.tuicool.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by lowworker on 2015/9/19.
 */
@ParcelablePlease
public class Article implements Parcelable {


    /**
     * st : 0
     * img : http://aimg0.tuicool.com/vUVBru.png!middle
     * abs :
     * rectime : 09-19 19:42
     * uts : 1442662973000
     * go : 0
     * id : UryMRvQ
     * time : 09-19 19:42
     * cmt : 0
     * title : Android中常见Intent习惯用法-上篇(附源码下载)
     * feed_title : CSDN博客
     */
    @SerializedName("st")
    public int st;
    @SerializedName("img")
    public String img;
    @SerializedName("abs")
    public String abs;
    @SerializedName("rectime")
    public String rectime;
    @SerializedName("uts")
    public long uts;
    @SerializedName("go")
    public int go;
    @SerializedName("id")
    public String id;
    @SerializedName("time")
    public String time;
    @SerializedName("cmt")
    public int cmt;
    @SerializedName("title")
    public String title;
    @SerializedName("feed_title")
    public String feedTitle;
    @SerializedName("content")
    public String content;
    @SerializedName("url")
    public String url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ArticleParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        public Article createFromParcel(Parcel source) {
            Article target = new Article();
            ArticleParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
