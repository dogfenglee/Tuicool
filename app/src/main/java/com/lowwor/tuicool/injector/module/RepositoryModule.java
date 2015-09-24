package com.lowwor.tuicool.injector.module;


import com.lowwor.tuicool.api.TuicoolApiRepository;

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

}
