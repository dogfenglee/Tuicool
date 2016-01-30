package com.lowwor.tuicool.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;
import com.lowwor.tuicool.data.db.tables.HotTopicsItemTable;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by lowworker on 2015/9/20.
 */
@ParcelablePlease

@StorIOSQLiteType(table = HotTopicsItemTable.TABLE)
public class HotTopicsItem implements Parcelable {

    @SerializedName("image")
    public String image;
    @StorIOSQLiteColumn(name = HotTopicsItemTable.COLUMN_NAME)
    @SerializedName("name")
    public String name;
    @SerializedName("id")
    @StorIOSQLiteColumn(name = HotTopicsItemTable.COLUMN_ID, key = true)
    public int id;
    @SerializedName("followed")
    public boolean followed;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        HotTopicsItemParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<HotTopicsItem> CREATOR = new Creator<HotTopicsItem>() {
        public HotTopicsItem createFromParcel(Parcel source) {
            HotTopicsItem target = new HotTopicsItem();
            HotTopicsItemParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public HotTopicsItem[] newArray(int size) {
            return new HotTopicsItem[size];
        }
    };
}
