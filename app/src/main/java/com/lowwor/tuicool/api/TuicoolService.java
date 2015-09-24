package com.lowwor.tuicool.api;

import com.lowwor.tuicool.model.ArticleWrapper;
import com.lowwor.tuicool.model.ArticlesWrapper;
import com.lowwor.tuicool.model.HotTopicsWrapper;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by lowworker on 2015/9/19.
 */
public interface TuicoolService {

      public final String BASE_URL = "http://api.tuicool.com/api/";


    @GET("/topics/{topicId}.json")
    Observable<ArticlesWrapper> getArticleListByTopicId(@Path("topicId")String topicId,@Query("pn")int page,@Query("lang")int lang,@Query("size")int size);


    @GET("/articles/{articleId}.json")
    Observable<ArticleWrapper> getArticleById(@Path("articleId")String articleId,@Query("need_image_meta")int needImage,@Query("type") int type);

    @GET("/topics/hot_all.json")
    Observable<HotTopicsWrapper> getHotTopics();


}
