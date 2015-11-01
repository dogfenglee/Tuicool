package com.lowwor.tuicool.ui.topic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lowwor.tuicool.R;
import com.lowwor.tuicool.db.TuicoolDatabaseRepository;
import com.lowwor.tuicool.model.HotTopicsItem;
import com.lowwor.tuicool.ui.base.BaseFragment;
import com.lowwor.tuicool.ui.hottopics.HotTopicsActivity;
import com.lowwor.tuicool.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

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
    @Inject
    TuicoolDatabaseRepository mTuicoolDatabaseRepository;

    Subscription mSubscription;

    private List<HotTopicsItem> mHotTopics;
    TabsPagerAdapter mTabsPagerAdapter;

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

        initializeDependencyInjector();
        initializeToolbar();
        initialziePagerAndTabs();

    }

    private void initialziePagerAndTabs() {
        //getChildFragmentManager() important ,
       // it will be subscribed to changes in tweets table!
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            return;
        }
        mSubscription =   mTuicoolDatabaseRepository.getHotTopicItems().observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        hotTopics -> onHotTopicsReceived(hotTopics),
                        error -> manageError(error));

    }

    private void onHotTopicsReceived(List<HotTopicsItem> hotTopics) {
        mTabsPagerAdapter = new TabsPagerAdapter(getActivity(), getChildFragmentManager(),hotTopics);
        mViewPager.setAdapter(mTabsPagerAdapter);
        mTabsPagerAdapter.notifyDataSetChanged();
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void manageError(Throwable error) {


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mSubscription.unsubscribe();
    }

    private void initializeDependencyInjector() {
        ((MainActivity) getActivity()).topicComponent().inject(this);
    }

    private void initializeToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.mipmap.ic_action_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        mToolbar.setOnMenuItemClickListener(

                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_add:
                            Intent intent = new Intent(getActivity(), HotTopicsActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.action_lang_multi:
                            setLanguage(Constants.LANGUAGE_MULTI);
                            break;
                        case R.id.action_lang_cn:
                            setLanguage(Constants.LANGUAGE_CN);
                            break;
                        case R.id.action_lang_en:
                            setLanguage(Constants.LANGUAGE_EN);
                            break;

                    }
                    return true;
                }

        );
    }

    private void setLanguage(int language){
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences(Constants.SP_TUICOOL, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putInt(Constants.SP_KEY_LANGUAGE, language);
        mEditor.commit();
    }
}
