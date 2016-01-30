package com.lowwor.tuicool.ui.article;

import android.content.Intent;
import android.os.Bundle;

import com.lowwor.tuicool.R;
import com.lowwor.tuicool.TuicoolApplication;
import com.lowwor.tuicool.injector.component.ArticleComponent;
import com.lowwor.tuicool.injector.component.DaggerArticleComponent;
import com.lowwor.tuicool.injector.module.ArticleModule;
import com.lowwor.tuicool.injector.module.FragmentModule;
import com.lowwor.tuicool.data.model.Article;
import com.lowwor.tuicool.ui.base.BaseActivity;


/**
 * Created by lowworker on 2015/9/20.
 */
public class ArticleActivity extends BaseActivity {
    private Article mArticle;
    private ArticleComponent mArticleComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        ArticleFragment articleFragment = new ArticleFragment();
        Bundle bundle = new Bundle();


        Intent intent = getIntent();
        if (intent.hasExtra("model")) {
            mArticle = intent.getParcelableExtra("model");
            bundle.putParcelable("model", mArticle);
            articleFragment.setArguments(bundle);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container,articleFragment).commit();
    }

    public ArticleComponent articleComponent() {
        if (mArticleComponent == null) {
            mArticleComponent = DaggerArticleComponent.builder()
                    .appComponent(((TuicoolApplication) getApplication()).getAppComponent())
                    .fragmentModule(new FragmentModule())
                    .articleModule(new ArticleModule())
                    .build();
        }
        return mArticleComponent;
    }
}
