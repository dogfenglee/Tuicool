package com.lowwor.tuicool.injector;

import com.lowwor.tuicool.TuicoolApplication;

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
}
