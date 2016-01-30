package com.lowwor.tuicool.ui.hottopics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lowwor.tuicool.R;
import com.lowwor.tuicool.data.api.TuicoolApiRepository;
import com.lowwor.tuicool.data.api.exceptions.NetworkErrorException;
import com.lowwor.tuicool.data.api.exceptions.NetworkTimeOutException;
import com.lowwor.tuicool.data.api.exceptions.NetworkUknownHostException;
import com.lowwor.tuicool.data.db.TuicoolDatabaseRepository;
import com.lowwor.tuicool.data.model.HotTopicsCatalog;
import com.lowwor.tuicool.data.model.HotTopicsItem;
import com.lowwor.tuicool.data.model.HotTopicsWrapper;
import com.lowwor.tuicool.ui.base.BaseFragment;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lowworker on 2015/9/20.
 */
public class HotTopicsFragment extends BaseFragment implements CatalogAdapter.CatalogClickListener, HotTopicAdapter.HotTopicClickListener {

    @Inject
    TuicoolApiRepository mTuicoolApiRepository;
    @Inject
    TuicoolDatabaseRepository mTuicoolDatabaseRepository;
    @Bind(R.id.catalog_recycler)
    RecyclerView mCatalogRecycler;
    @Bind(R.id.hot_topic_recycler)
    RecyclerView mHotTopicRecycler;

    private LinearLayoutManager mCatalogManager;
    private LinearLayoutManager mHotTopicManager;
    private List<HotTopicsCatalog> mCatalogs;
    private List<HotTopicsItem> mHotTopics;
    private CatalogAdapter mCatalogAdapter;
    private HotTopicAdapter mHotTopicAdapter;

    private HotTopicsWrapper mHotTopicsWrapper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_hot_topics, container, false);

        initializeDependencyInjector();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ButterKnife.bind(this, view);
        initializeRecyclerView();
        loadData();
    }


    private void initializeRecyclerView() {


        mCatalogManager = new LinearLayoutManager(getActivity());
        mCatalogRecycler.setLayoutManager(mCatalogManager);
        mCatalogs = new ArrayList<>();
        mCatalogAdapter = new CatalogAdapter(mCatalogs, getActivity(), this);
        mCatalogRecycler.setAdapter(mCatalogAdapter);

        mHotTopicManager = new LinearLayoutManager(getActivity());
        mHotTopicRecycler.setLayoutManager(mHotTopicManager);
        mHotTopics = new ArrayList<>();
        mHotTopicAdapter = new HotTopicAdapter(mHotTopics, getActivity(), this,mTuicoolDatabaseRepository);
        mHotTopicRecycler.setAdapter(mHotTopicAdapter);
    }


    @Override
    public void onElementClick(int position) {
        mHotTopics.clear();
        mHotTopics.addAll(mHotTopicsWrapper.getCatalogs().get(position).getItems());
        mHotTopicAdapter.notifyDataSetChanged();
    }

    @Override
    public void onHotTopicClick(int position) {
        HotTopicsItem hotTopic = mHotTopics.get(position);

        if (mTuicoolDatabaseRepository.isSubscribe(hotTopic)) {
            mTuicoolDatabaseRepository.removeHotTopic(hotTopic);
        } else {
            mTuicoolDatabaseRepository.addHotTopic(hotTopic);
        }


    }


    private void loadData() {

        mTuicoolApiRepository.getHotTopics().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        hotTopics -> onHotTopicsReceived(hotTopics),
                        error -> manageError(error));
    }

    private void initializeDependencyInjector() {
        ((HotTopicsActivity) getActivity()).hotTopicsComponent().inject(this);
    }

    private void onHotTopicsReceived(HotTopicsWrapper hotTopicsWrapper) {
        Logger.i("onHotTopicsReceived" + hotTopicsWrapper.getCatalogs().get(0).getName());

        mHotTopicsWrapper = hotTopicsWrapper;

        mCatalogs.addAll(hotTopicsWrapper.getCatalogs());
        mCatalogAdapter.notifyDataSetChanged();

        mHotTopics.addAll(hotTopicsWrapper.getCatalogs().get(0).getItems());
        mHotTopicAdapter.notifyDataSetChanged();
    }


    private void manageError(Throwable error) {

        if (error instanceof NetworkUknownHostException)
            showError("It has not been possible to resolve V2EX");

        if (error instanceof NetworkTimeOutException)
            showError("It has ended the waiting time for connecting to the server V2EX");

        if (error instanceof NetworkErrorException)
            showError("There was a problem with the network");
    }

    public void showError(String error) {
//        Snackbar.make(mRecyclerview, error, Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
