package com.lowwor.tuicool.db;

import com.lowwor.tuicool.db.tables.HotTopicsItemTable;
import com.lowwor.tuicool.model.HotTopicsItem;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by lowworker on 2015/9/27.
 */
public class TuicoolDatabaseRepository {

    StorIOSQLite mStorIOSQLite;

    @Inject
    public TuicoolDatabaseRepository(StorIOSQLite storIOSQLite) {
        this.mStorIOSQLite = storIOSQLite;
    }

    public Observable<List<HotTopicsItem>> getHotTopicItems() {
        return mStorIOSQLite
                .get()
                .listOfObjects(HotTopicsItem.class)
                .withQuery(HotTopicsItemTable.QUERY_ALL)
                .prepare()
                .createObservable();

    }
    public void addHotTopic(HotTopicsItem hotTopic) {
        mStorIOSQLite.put()
                .object(hotTopic)
                .prepare()
                .executeAsBlocking();
    }
    public void removeHotTopic(HotTopicsItem hotTopic) {
        mStorIOSQLite
                .delete()
                .object(hotTopic)
                .prepare()
                .executeAsBlocking(); //
    }
    public boolean isSubscribe(HotTopicsItem hotTopic){
        List<HotTopicsItem>  hotTopics =   mStorIOSQLite
                .get()
                .listOfObjects(HotTopicsItem.class)
                .withQuery(Query.builder()
                        .table(HotTopicsItemTable.TABLE)
                        .where(HotTopicsItemTable.COLUMN_ID+" = ?")
                        .whereArgs(hotTopic.id)
                        .build())
                .prepare()
                .executeAsBlocking();

        if (hotTopics.isEmpty()) {
            return false;
        }else{
            return true;
        }
    }
}
