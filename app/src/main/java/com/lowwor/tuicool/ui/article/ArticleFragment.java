package com.lowwor.tuicool.ui.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.lowwor.tuicool.R;
import com.lowwor.tuicool.api.TuicoolApiRepository;
import com.lowwor.tuicool.api.exceptions.NetworkErrorException;
import com.lowwor.tuicool.api.exceptions.NetworkTimeOutException;
import com.lowwor.tuicool.api.exceptions.NetworkUknownHostException;
import com.lowwor.tuicool.model.Article;
import com.lowwor.tuicool.model.ArticleWrapper;
import com.lowwor.tuicool.ui.base.BaseFragment;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lowworker on 2015/9/20.
 */
public class ArticleFragment extends BaseFragment {


    @Bind(R.id.pb_loading)
    ProgressBar pbLoading;
    @Bind(R.id.webview)
    WebView mWebView;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;


    @Inject
    TuicoolApiRepository mTuicoolApiRepository;
    private Article mArticle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        ButterKnife.bind(this, view);
        mArticle = getArguments().getParcelable("model");
        initToolbar();
        initWebview();
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeDependencyInjector();

        loadData(mArticle.id);
    }

    private void initializeDependencyInjector() {
        ((ArticleActivity) getActivity()).articleComponent().inject(this);
    }

    private void loadData(String articleId) {
        pbLoading.setVisibility(View.VISIBLE);
        mTuicoolApiRepository.getArticleById(articleId, 1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        articlewrapper -> onArticleReceived(articlewrapper),
                        error -> manageError(error));
    }


    private void onArticleReceived(ArticleWrapper articleWrapper) {

        pbLoading.setVisibility(View.GONE);
        mArticle = articleWrapper.getArticle();
//        Logger.i("onTopicsReceived" + mArticle.content);
        mWebView.loadDataWithBaseURL(null, mArticle.content, "text/html", "UTF-8", null);
//        Logger.i(mArticle.content);
    }

    private void manageError(Throwable error) {
        pbLoading.setVisibility(View.GONE);
        if (error instanceof NetworkUknownHostException)
            showError("It has not been possible to resolve V2EX");

        if (error instanceof NetworkTimeOutException)
            showError("It has ended the waiting time for connecting to the server V2EX");

        if (error instanceof NetworkErrorException)
            showError("There was a problem with the network");
    }

    public void showError(String error) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mWebView != null) mWebView.destroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWebView != null) mWebView.onResume();
    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mArticle.title);
            actionBar.setSubtitle(mArticle.feedTitle + "   " + mArticle.time);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    private void initWebview() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        mWebView.setWebChromeClient(new ChromeClient());
    }

    private class ChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);

            Logger.i(newProgress + "");
            pbLoading.setProgress(newProgress);
            if (newProgress == 100) {
                pbLoading.setVisibility(View.GONE);
            } else {
                pbLoading.setVisibility(View.VISIBLE);
            }
        }

    }

}
