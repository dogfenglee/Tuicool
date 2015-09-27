package com.lowwor.tuicool.api;

import android.accounts.NetworkErrorException;

import com.lowwor.tuicool.api.exceptions.NetworkTimeOutException;
import com.lowwor.tuicool.api.exceptions.NetworkUknownHostException;
import com.lowwor.tuicool.model.ArticleWrapper;
import com.lowwor.tuicool.model.ArticlesWrapper;
import com.lowwor.tuicool.model.HotTopicsWrapper;
import com.orhanobut.logger.Logger;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import rx.Observable;

/**
 * Created by lowworker on 2015/9/19.
 */
public class TuicoolApiRepository {
    RestAdapter mRestApapter;
    TuicoolService mTuicoolService;

    public TuicoolApiRepository() {
        RestAdapter mRestApapter = new RestAdapter.Builder()
                .setEndpoint(TuicoolService.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.HEADERS_AND_ARGS)
                .setErrorHandler(new RetrofitErrorHandler())
                .setRequestInterceptor(new ApiRequestIntercepter())
                .build();
        mTuicoolService = mRestApapter.create(TuicoolService.class);


    }


    public Observable<ArticlesWrapper> getArticleListByTopicId(int topicId, int page) {
        return mTuicoolService.getArticleListByTopicId(topicId,page,2,30);
    }

    public Observable<ArticleWrapper> getArticleById(String articleId, int needImage) {

        return mTuicoolService.getArticleById(articleId, needImage, 2);
    }

    public Observable<HotTopicsWrapper> getHotTopics() {

        return mTuicoolService.getHotTopics();
    }

    public class RetrofitErrorHandler implements retrofit.ErrorHandler {

        @Override
        public Throwable handleError(RetrofitError cause) {

            Logger.i("manageError " + cause.getKind());
            if (cause.getKind() == RetrofitError.Kind.NETWORK) {

                if (cause.getCause() instanceof SocketTimeoutException)
                    return new NetworkTimeOutException();

                else if (cause.getCause() instanceof UnknownHostException)
                    return new NetworkUknownHostException();

                else if (cause.getCause() instanceof ConnectException)
                    return cause.getCause();

            } else {

                return new NetworkErrorException();
            }

            return new Exception();

        }
    }
}
