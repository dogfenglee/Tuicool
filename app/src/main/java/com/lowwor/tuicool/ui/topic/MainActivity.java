package com.lowwor.tuicool.ui.topic;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.lowwor.tuicool.R;
import com.lowwor.tuicool.TuicoolApplication;
import com.lowwor.tuicool.injector.component.DaggerTopicComponent;
import com.lowwor.tuicool.injector.component.TopicComponent;
import com.lowwor.tuicool.injector.module.FragmentModule;
import com.lowwor.tuicool.injector.module.TopicModule;
import com.lowwor.tuicool.ui.base.BaseActivity;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {



    @Bind(R.id.navigationview)
    NavigationView mNavigationView;
    @Bind(R.id.drawerlayout)
    DrawerLayout mDrawerLayout;

    private TopicComponent mTopicsComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initializeDrawer();
        initializeTabsFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Logger.i("onOptionItemSelected" + item.getItemId());
        switch (item.getItemId()){
            //setHomeAs
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);

                Logger.i("action_menu");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initializeTabsFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content, new TabsHolderFragment()).commit();
    }



    private void initializeDrawer() {

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                selectDrawerItem(menuItem);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public TopicComponent topicComponent() {
        if (mTopicsComponent == null) {
            mTopicsComponent = DaggerTopicComponent.builder()
                    .appComponent(((TuicoolApplication) getApplication()).getAppComponent())
                    .fragmentModule(new FragmentModule())
                    .topicModule(new TopicModule())
                    .build();
        }
        return mTopicsComponent;
    }

    private void selectDrawerItem(MenuItem menuItem){


        Fragment fragment = null;
        Class fragmentClass;

        switch (menuItem.getItemId()){

//            case R.id.navigation_tab:
//                fragmentClass = TabsHolderFragment.class;
//                break;
            default:
                fragmentClass = TabsHolderFragment.class;


        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();


        setTitle(menuItem.getTitle());
        menuItem.setChecked(true);
    }




}
