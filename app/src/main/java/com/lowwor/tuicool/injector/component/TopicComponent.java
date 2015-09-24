package com.lowwor.tuicool.injector.component;

import com.lowwor.tuicool.injector.FragmentScope;
import com.lowwor.tuicool.injector.module.FragmentModule;
import com.lowwor.tuicool.injector.module.TopicModule;
import com.lowwor.tuicool.ui.topic.TopicFragment;

import dagger.Component;

/**
 * Created by lowworker on 2015/9/13.
 */
@FragmentScope
@Component(dependencies = AppComponent.class,modules = {FragmentModule.class,TopicModule.class})
public interface TopicComponent {

    void inject(TopicFragment fragment);
}
