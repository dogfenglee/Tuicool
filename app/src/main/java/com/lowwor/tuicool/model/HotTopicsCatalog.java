package com.lowwor.tuicool.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lowworker on 2015/9/20.
 */
public class HotTopicsCatalog   {

    @SerializedName("name")
    public String name;
    @SerializedName("items")
    public List<HotTopicsItem> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HotTopicsItem> getItems() {
        return items;
    }

    public void setItems(List<HotTopicsItem> items) {
        this.items = items;
    }
}
