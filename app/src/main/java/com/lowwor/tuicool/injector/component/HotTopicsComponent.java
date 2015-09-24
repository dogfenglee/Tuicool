package com.lowwor.tuicool.injector.component;

import com.lowwor.tuicool.injector.FragmentScope;
import com.lowwor.tuicool.injector.module.FragmentModule;
import com.lowwor.tuicool.injector.module.HotTopicsModule;
import com.lowwor.tuicool.ui.hottopics.HotTopicsFragment;

import dagger.Component;

/**
 * Created by lowworker on 2015/9/20.
 */
@FragmentScope
@Component(dependencies = AppComponent.class,modules = {FragmentModule.class,HotTopicsModule.class})
public interface HotTopicsComponent {

    void inject(HotTopicsFragment fragment);
}
