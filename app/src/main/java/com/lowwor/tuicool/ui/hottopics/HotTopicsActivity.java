package com.lowwor.tuicool.ui.hottopics;

import android.os.Bundle;

import com.lowwor.tuicool.R;
import com.lowwor.tuicool.TuicoolApplication;
import com.lowwor.tuicool.injector.component.DaggerHotTopicsComponent;
import com.lowwor.tuicool.injector.component.HotTopicsComponent;
import com.lowwor.tuicool.injector.module.FragmentModule;
import com.lowwor.tuicool.injector.module.HotTopicsModule;
import com.lowwor.tuicool.ui.base.BaseActivity;

/**
 * Created by lowworker on 2015/9/20.
 */
public class HotTopicsActivity extends BaseActivity {


    private HotTopicsComponent mHotTopicsComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        HotTopicsFragment hotTopicsFragment = new HotTopicsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,hotTopicsFragment).commit();
    }
    public HotTopicsComponent hotTopicsComponent() {
        if (mHotTopicsComponent == null) {
            mHotTopicsComponent = DaggerHotTopicsComponent.builder()
                    .appComponent(((TuicoolApplication) getApplication()).getAppComponent())
                    .fragmentModule(new FragmentModule())
                    .hotTopicsModule(new HotTopicsModule())
                    .build();
        }
        return mHotTopicsComponent;
    }
}
