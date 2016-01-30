package com.lowwor.tuicool.data.model;

import com.google.gson.annotations.SerializedName;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by lowworker on 2015/9/20.
 */
@ParcelablePlease
public class Topic {


    @SerializedName("image")
    public String image;
    @SerializedName("name")
    public String name;
    @SerializedName("id")
    public int id;
    @SerializedName("followed")
    public boolean followed;
}
