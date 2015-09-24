package com.lowwor.tuicool.injector.module;

import android.content.Context;

import com.lowwor.tuicool.injector.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lowworker on 2015/9/13.
 */

@Module
public class ActivityModule {

    private final Context mContext;

    public ActivityModule(Context mContext) {

        this.mContext = mContext;
    }

    @Provides
    @ActivityScope
    Context provideActivityContext() {
        return mContext;
    }
}
