package com.lowwor.tuicool.ui.topic;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.lowwor.tuicool.model.HotTopicsItem;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by lowworker on 2015/9/12.
 */
public class TabsPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private List<HotTopicsItem> mHotTopics;
    public TabsPagerAdapter(Context context,FragmentManager fm,List<HotTopicsItem> hotTopics) {
        super(fm);
        this.mContext = context;
        mHotTopics = hotTopics;
    }

    @Override
    public Fragment getItem(int position) {
        TopicFragment fragment = new TopicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("topicId", mHotTopics.get(position).id);
        bundle.putBoolean("attach_main", true);
        bundle.putBoolean("show_menu", false);
        fragment.setArguments(bundle);
       return fragment;
    }

    @Override
    public int getCount() {
        return mHotTopics.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        Logger.i(mHotTopics.get(position).name);
        return mHotTopics.get(position).name;
    }


    @Override
    public int getItemPosition(Object object) {

        return PagerAdapter.POSITION_NONE;
    }


}
