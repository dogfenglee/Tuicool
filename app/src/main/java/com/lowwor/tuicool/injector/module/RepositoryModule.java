package com.lowwor.tuicool.injector.module;


import com.lowwor.tuicool.data.api.TuicoolApiRepository;
import com.lowwor.tuicool.data.db.TuicoolDatabaseRepository;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lowworker on 2015/9/12.
 */
@Module
public class RepositoryModule {

    @Provides
    @Singleton
    TuicoolApiRepository provideTuicoolApiRepository(){
        return  new TuicoolApiRepository();
    }



    @Provides
    @Singleton
    TuicoolDatabaseRepository provideTuicoolDatabaseRepository(  StorIOSQLite storIOSQLite){
        return  new TuicoolDatabaseRepository(storIOSQLite);
    }



}
