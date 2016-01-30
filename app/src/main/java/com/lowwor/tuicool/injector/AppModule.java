package com.lowwor.tuicool.injector;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.lowwor.tuicool.TuicoolApplication;
import com.lowwor.tuicool.data.db.DbOpenHelper;
import com.lowwor.tuicool.data.model.HotTopicsItem;
import com.lowwor.tuicool.data.model.HotTopicsItemStorIOSQLiteDeleteResolver;
import com.lowwor.tuicool.data.model.HotTopicsItemStorIOSQLiteGetResolver;
import com.lowwor.tuicool.data.model.HotTopicsItemStorIOSQLitePutResolver;
import com.lowwor.tuicool.utils.Constants;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lowworker on 2015/9/19.
 */
@Module
public class AppModule {

    private final TuicoolApplication mTuicoolApplication;

    public AppModule(TuicoolApplication tuicoolApplication){
        this.mTuicoolApplication = tuicoolApplication;
    }

    @Provides
    @Singleton
    TuicoolApplication provideTuicoolApplication(){
        return mTuicoolApplication;
    }

    @Provides
    @NonNull
    @Singleton
    public StorIOSQLite provideStorIOSQLite(@NonNull SQLiteOpenHelper sqLiteOpenHelper) {
        return DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(sqLiteOpenHelper)
                .addTypeMapping(HotTopicsItem.class, SQLiteTypeMapping.<HotTopicsItem>builder()
                        .putResolver(new HotTopicsItemStorIOSQLitePutResolver())
                        .getResolver(new HotTopicsItemStorIOSQLiteGetResolver())
                        .deleteResolver(new HotTopicsItemStorIOSQLiteDeleteResolver())
                        .build())
                .build();
    }


    @Provides
    @NonNull
    @Singleton
    public SQLiteOpenHelper provideSQLiteOpenHelper(@NonNull TuicoolApplication context) {
        return new DbOpenHelper(context);
    }

    @Provides
    @Singleton
    public RxSharedPreferences provideRxSharedPreferences(@NonNull TuicoolApplication context) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.SP_TUICOOL, Context.MODE_PRIVATE);
        return RxSharedPreferences.create(preferences);
    }

}
