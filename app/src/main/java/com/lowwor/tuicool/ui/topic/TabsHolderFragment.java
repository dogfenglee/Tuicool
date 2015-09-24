package com.lowwor.tuicool.ui.topic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lowwor.tuicool.R;
import com.lowwor.tuicool.ui.base.BaseFragment;
import com.lowwor.tuicool.ui.hottopics.HotTopicsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lowworker on 2015/9/12.
 */
public class TabsHolderFragment extends BaseFragment {


    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tablayout)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Logger.i("onCreateView");
        View view = inflater.inflate(R.layout.fragment_tab_holder, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Logger.i("onViewCreated");
        initializeToolbar();
        initialziePagerAndTabs();

    }

    private void initialziePagerAndTabs(){
        //getChildFragmentManager() important ,
        TabsPagerAdapter mTabsPagerAdapter = new TabsPagerAdapter(getActivity(), getChildFragmentManager());
        mViewPager.setAdapter(mTabsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initializeToolbar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        final ActionBar ab =   ((AppCompatActivity)getActivity()).getSupportActionBar();
        if(ab!=null){
            ab.setHomeAsUpIndicator(R.mipmap.ic_action_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.action_add:
                        Intent intent = new Intent(getActivity(), HotTopicsActivity.class);
                        startActivity(intent);
                    break;

                }


                return true;
            }
        });
    }
}
