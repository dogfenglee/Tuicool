package com.lowwor.tuicool.db.tables;

import android.support.annotation.NonNull;

/**
 * Created by lowworker on 2015/9/27.
 */
public class HotTopicsItemTable {


    @NonNull
    public static final String TABLE = "subscribes";


    @NonNull
    public static final String COLUMN_ID = "_id";

    @NonNull
    public static final String COLUMN_NAME = "name";


    private HotTopicsItemTable() {
        throw new IllegalStateException("No instances please");
    }


    @NonNull
    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE + "("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, "
                + COLUMN_NAME + " TEXT NOT NULL"
                + ");";
    }
}
