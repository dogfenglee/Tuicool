package com.lowwor.tuicool.injector.component;

import com.lowwor.tuicool.injector.FragmentScope;
import com.lowwor.tuicool.injector.module.ArticleModule;
import com.lowwor.tuicool.injector.module.FragmentModule;
import com.lowwor.tuicool.ui.article.ArticleFragment;

import dagger.Component;

/**
 * Created by lowworker on 2015/9/20.
 */
@FragmentScope
@Component(dependencies = AppComponent.class,modules = {FragmentModule.class,ArticleModule.class})
public interface ArticleComponent {

    void inject(ArticleFragment fragment);
}
