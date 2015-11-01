package com.lowwor.tuicool.ui.topic;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.lowwor.tuicool.R;
import com.lowwor.tuicool.api.TuicoolApiRepository;
import com.lowwor.tuicool.api.exceptions.NetworkErrorException;
import com.lowwor.tuicool.api.exceptions.NetworkTimeOutException;
import com.lowwor.tuicool.api.exceptions.NetworkUknownHostException;
import com.lowwor.tuicool.model.Article;
import com.lowwor.tuicool.model.ArticlesWrapper;
import com.lowwor.tuicool.ui.base.BaseFragment;
import com.lowwor.tuicool.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

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
    private int mLanguage;
    Preference<Integer> mLanguagePreference;
    private  Subscription mSubscription;
    CompositeSubscription mSubscriptions = new CompositeSubscription();


    @Inject
    TuicoolApiRepository mTuicoolApiRepository;
    @Inject
    RxSharedPreferences mRxSharedPreferences;

    public static TopicFragment newInstance() {

        TopicFragment topicsFragment = new TopicFragment();
        return topicsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        initializeRecyclerView();
        initializeSwipeRefresh();
        initializeDependencyInjector();
        mPage = 0;

        bindLanguage();
        loadData(getTopicId(), mPage, mLanguage);

    }


    private void bindLanguage(){
        mLanguagePreference = mRxSharedPreferences.getInteger(Constants.SP_KEY_LANGUAGE,Constants.LANGUAGE_MULTI);
        mSubscriptions.add(mLanguagePreference.asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        mPage = 0;
                        mLanguage = integer;
                        setRefresh(true);
                        mRecyclerview.scrollToPosition(0);
                        loadData(getTopicId(), mPage, mLanguage);
                    }
                }));
    }

    private int getTopicId() {
        return getArguments().getInt("topicId");
    }

    private void initializeRecyclerView() {

        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        mArticles = new ArrayList<>();
        mTopicAdapter = new TopicAdapter(mArticles, getActivity());
        mRecyclerview.setAdapter(mTopicAdapter);
        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mTopicAdapter.getItemCount();

                if (lastVisibleItem > totalItemCount - 4 && dy > 0) {

                    loadData(getTopicId(), mPage + 1, mLanguage);
                }

            }
        });
    }

    private void initializeSwipeRefresh() {
        mSwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeRefresh.setOnRefreshListener(
                () -> {
                    mPage = 0;
                    loadData(getTopicId(), mPage, mLanguage);
                }

        );
    }

    private void loadData(int topicId, int page,int lang) {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            return;
        }
       mSubscription = mTuicoolApiRepository.getArticleListByTopicId(topicId, page,lang).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        articleList -> onArticleListReceived(articleList),
                        error -> manageError(error));
    }

    private void initializeDependencyInjector() {
        ((MainActivity) getActivity()).topicComponent().inject(this);
    }

    private void onArticleListReceived(ArticlesWrapper articlesWrapper) {
//        Logger.i("onTopicsReceived" + articlesWrapper.getArticles().size());

        if (mPage == 0) {
            mArticles.clear();
        }
        mPage++;

        setRefresh(false);

        if (!articlesWrapper.getArticles().isEmpty()) {
            mArticles.addAll(articlesWrapper.getArticles());
        } else {

        }
        mTopicAdapter.notifyDataSetChanged();
    }

    private void manageError(Throwable error) {
        setRefresh(false);
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

    private void setRefresh(boolean isRefresh) {
        if (mSwipeRefresh == null) {
            return;
        }
        if (isRefresh&&getUserVisibleHint()) {
            mSwipeRefresh.post(
                    () -> mSwipeRefresh.setRefreshing(true)
            );
        }else{
            mSwipeRefresh.setRefreshing(false);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

        mSubscriptions.unsubscribe();
        mSubscription.unsubscribe();
    }





}
