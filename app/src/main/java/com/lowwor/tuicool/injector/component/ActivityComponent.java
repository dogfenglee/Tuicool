package com.lowwor.tuicool.injector.component;


import android.content.Context;

import com.lowwor.tuicool.injector.ActivityScope;
import com.lowwor.tuicool.injector.module.ActivityModule;

import dagger.Component;

/**
 * Created by lowworker on 2015/9/13.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Context context();
}