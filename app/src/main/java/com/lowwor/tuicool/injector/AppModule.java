package com.lowwor.tuicool.injector;

import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.lowwor.tuicool.TuicoolApplication;
import com.lowwor.tuicool.db.DbOpenHelper;
import com.lowwor.tuicool.model.HotTopicsItem;
import com.lowwor.tuicool.model.HotTopicsItemStorIOSQLiteDeleteResolver;
import com.lowwor.tuicool.model.HotTopicsItemStorIOSQLiteGetResolver;
import com.lowwor.tuicool.model.HotTopicsItemStorIOSQLitePutResolver;
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
}
