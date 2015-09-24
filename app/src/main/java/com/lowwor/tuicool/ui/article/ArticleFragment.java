package com.lowwor.tuicool.ui.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lowwor.tuicool.R;
import com.lowwor.tuicool.api.TuicoolApiRepository;
import com.lowwor.tuicool.api.exceptions.NetworkErrorException;
import com.lowwor.tuicool.api.exceptions.NetworkTimeOutException;
import com.lowwor.tuicool.api.exceptions.NetworkUknownHostException;
import com.lowwor.tuicool.model.Article;
import com.lowwor.tuicool.model.ArticleWrapper;
import com.lowwor.tuicool.ui.base.BaseFragment;
import com.lowwor.tuicool.ui.customview.RichTextView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lowworker on 2015/9/20.
 */
public class ArticleFragment extends BaseFragment {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_feed_title)
    TextView tvFeedTitle;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_content)
    RichTextView tvContent;


    @Inject
    TuicoolApiRepository mTuicoolApiRepository;
    private Article mArticle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        ButterKnife.bind(this, view);
        mArticle = getArguments().getParcelable("model");
        tvTitle.setText(mArticle.title);
        tvTime.setText(mArticle.time);
        tvFeedTitle.setText(mArticle.feedTitle);
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

        mTuicoolApiRepository.getArticleById(articleId,1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        articlewrapper -> onArticleReceived(articlewrapper),
                        error -> manageError(error));
    }


    private void onArticleReceived(ArticleWrapper articleWrapper) {
        mArticle = articleWrapper.getArticle();
//        Logger.i("onTopicsReceived" + mArticle.content);
        tvContent.setRichText(mArticle.content);
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
        Snackbar.make(tvContent, error, Snackbar.LENGTH_LONG).setAction("Retry", new View.OnClickListener() {
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
