package com.lowwor.tuicool.ui.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lowwor.tuicool.R;
import com.lowwor.tuicool.data.api.TuicoolApiRepository;
import com.lowwor.tuicool.data.api.exceptions.NetworkErrorException;
import com.lowwor.tuicool.data.api.exceptions.NetworkTimeOutException;
import com.lowwor.tuicool.data.api.exceptions.NetworkUknownHostException;
import com.lowwor.tuicool.data.model.Article;
import com.lowwor.tuicool.data.model.ArticleWrapper;
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
    @Bind(R.id.tv_title)
    TextView mTitle;


    @Inject
    TuicoolApiRepository mTuicoolApiRepository;
    private Article mArticle;

    private String codeScrollable = "<style>pre{overflow-x: auto;overflow-y: auto;}</style>";
    private String codePrettifyJs =  " <script src=\"file:///android_asset/js/run_prettify.js\"></script>";
    private String imageAutoScale = "<style>img{display: inline;height: auto;max-width: 100%;}</style>";

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
        mWebView.loadDataWithBaseURL(null, getHtml(mArticle), "text/html", "UTF-8", null);
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
        SpannableString spanText = new SpannableString(mArticle.title+"     "+mArticle.feedTitle);
        spanText.setSpan(new TextAppearanceSpan(getActivity(), R.style.ToolBarFeedTitle), mArticle.title.length(), spanText.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        mTitle.setText(spanText);
        mTitle.postDelayed(() -> mTitle.setSelected(true), 1738);
        mTitle.setSelected(true);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    private void initWebview() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        mWebView.setWebChromeClient(new ChromeClient());
        mWebView.setWebViewClient(new CustomWebViewClient());
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

    private class CustomWebViewClient extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    private String getHtml(Article article){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(codePrettifyJs);
        stringBuilder.append(imageAutoScale);
        stringBuilder.append(codeScrollable);
        stringBuilder.append("<meta name=viewport content=target-densitydpi=medium-dpi, width=device-width/>");
        stringBuilder.append(mArticle.content);
        return stringBuilder.toString();
    }



}
