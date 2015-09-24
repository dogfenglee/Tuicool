package com.lowwor.tuicool.ui.topic;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.lowwor.tuicool.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lowworker on 2015/9/12.
 */
public class TabsPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    String[] mTabTitles;
    String[] mTopicIds  ;
    private List<TopicFragment> mFragments = new ArrayList<>();
    public TabsPagerAdapter(Context context,FragmentManager fm) {
        super(fm);
        this.mContext = context;

        mTabTitles = mContext.getResources().getStringArray(R.array.v2ex_favorite_tab_titles);
        mTopicIds = mContext.getResources().getStringArray(R.array.v2ex_favorite_tab_paths);
        initFragments();

        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {

       return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTabTitles.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }


    @Override
    public int getItemPosition(Object object) {

        return PagerAdapter.POSITION_NONE;
    }

    private void initFragments() {
        for (int i = 0; i < mTabTitles.length; i++) {
            TopicFragment fragment = new TopicFragment();
            Bundle bundle = new Bundle();
            bundle.putString("topicId", mTopicIds[i]);
            bundle.putBoolean("attach_main", true);
            bundle.putBoolean("show_menu", false);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }
    }
}
