package com.lowwor.tuicool;

import android.app.Application;

import com.lowwor.tuicool.injector.AppModule;
import com.lowwor.tuicool.injector.component.AppComponent;
import com.lowwor.tuicool.injector.component.DaggerAppComponent;

/**
 * Created by lowworker on 2015/9/19.
 */
public class TuicoolApplication extends Application {

    private AppComponent mAppComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector(){

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

    }


    public AppComponent getAppComponent() {

        return mAppComponent;
    }
}
