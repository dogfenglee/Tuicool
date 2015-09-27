package com.lowwor.tuicool.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.lowwor.tuicool.db.tables.HotTopicsItemTable;

/**
 * Created by lowworker on 2015/9/27.
 */
public class DbOpenHelper extends SQLiteOpenHelper {


    public DbOpenHelper(@NonNull Context context) {
        super(context, "tuicool_db", null, 1);
    }


    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        db.execSQL(HotTopicsItemTable.getCreateTableQuery());
    }


    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        // no impl
    }
}