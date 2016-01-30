package com.lowwor.tuicool.data.model;

import com.google.gson.annotations.SerializedName;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by lowworker on 2015/9/20.
 */
@ParcelablePlease
public class Image {
    @SerializedName("src")
    public String src;
    @SerializedName("w")
    public int w;
    @SerializedName("h")
    public int h;
    @SerializedName("id")
    public String id;


}
