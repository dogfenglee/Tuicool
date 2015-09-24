package com.lowwor.tuicool.injector.component;

import com.lowwor.tuicool.TuicoolApplication;
import com.lowwor.tuicool.api.TuicoolApiRepository;
import com.lowwor.tuicool.injector.AppModule;
import com.lowwor.tuicool.injector.module.RepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by lowworker on 2015/9/19.
 */

@Singleton
@Component(modules = {AppModule.class, RepositoryModule.class})
public interface AppComponent {

    TuicoolApplication app();
    TuicoolApiRepository tuicoolApiRepository();

}
