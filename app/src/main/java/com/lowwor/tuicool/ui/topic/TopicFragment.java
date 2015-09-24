package com.lowwor.tuicool.ui.topic;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lowwor.tuicool.R;
import com.lowwor.tuicool.api.TuicoolApiRepository;
import com.lowwor.tuicool.api.exceptions.NetworkErrorException;
import com.lowwor.tuicool.api.exceptions.NetworkTimeOutException;
import com.lowwor.tuicool.api.exceptions.NetworkUknownHostException;
import com.lowwor.tuicool.model.Article;
import com.lowwor.tuicool.model.ArticlesWrapper;
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
 * Created by lowworker on 2015/9/12.
 */
public class TopicFragment extends BaseFragment {

    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout mSwipeRefresh;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;

    private LinearLayoutManager mLinearLayoutManager;
    private List<Article> mArticles;
    private TopicAdapter mTopicAdapter;
    private int mPage;

    @Inject
    TuicoolApiRepository mTuicoolApiRepository;

    public static TopicFragment newInstance() {

        TopicFragment topicsFragment = new TopicFragment();
        return topicsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic, container, false);
        ButterKnife.bind(this, view);
        initializeRecyclerView();
        initializeSwipeRefresh();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initializeDependencyInjector();
        mPage = 0;
        loadData(getTopicId(), mPage);
    }

    private String getTopicId(){
     return    getArguments().getString("topicId");
    }

    private void initializeRecyclerView() {

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        mArticles = new ArrayList<>();
        mTopicAdapter = new TopicAdapter(mArticles,getActivity());
        mRecyclerview.setAdapter(mTopicAdapter);
        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mTopicAdapter.getItemCount();

                if (lastVisibleItem > totalItemCount - 4 && dy > 0) {

                    mPage++;
                    loadData(getTopicId(), mPage);
                }

            }
        });


    }


    private void initializeSwipeRefresh() {
        mSwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mPage = 0;
                loadData(getArguments().getString("topicId"), mPage);
            }
        });
    }

    private void loadData(String topicId,int page) {

        mTuicoolApiRepository.getArticleListByTopicId(topicId,page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        articleList -> onArticleListReceived(articleList),
                        error -> manageError(error));
    }

    private void initializeDependencyInjector() {
        ((MainActivity) getActivity()).topicComponent().inject(this);
    }

    private void onArticleListReceived(ArticlesWrapper articlesWrapper) {
        Logger.i("onTopicsReceived" + articlesWrapper.getArticles().get(0).feedTitle);
        if(mPage==0){
            mArticles.clear();
        }
        mSwipeRefresh.setRefreshing(false);
        mArticles.addAll(articlesWrapper.getArticles());
        mTopicAdapter.notifyDataSetChanged();
    }

    private void manageError(Throwable error) {

        if(mPage!=0){
            mPage--;
        }
        if (error instanceof NetworkUknownHostException)
            showError("It has not been possible to resolve V2EX");

        if (error instanceof NetworkTimeOutException)
            showError("It has ended the waiting time for connecting to the server V2EX");

        if (error instanceof NetworkErrorException)
            showError("There was a problem with the network");
    }

    public void showError(String error) {
        Snackbar.make(mRecyclerview, error, Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
