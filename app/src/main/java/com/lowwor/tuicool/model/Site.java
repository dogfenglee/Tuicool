package com.lowwor.tuicool.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by lowworker on 2015/9/20.
 */
@ParcelablePlease
public class Site implements Parcelable {
    @SerializedName("image")
    public String image;
    @SerializedName("id")
    public String id;
    @SerializedName("title")
    public String title;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        SiteParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Site> CREATOR = new Creator<Site>() {
        public Site createFromParcel(Parcel source) {
            Site target = new Site();
            SiteParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Site[] newArray(int size) {
            return new Site[size];
        }
    };
}
