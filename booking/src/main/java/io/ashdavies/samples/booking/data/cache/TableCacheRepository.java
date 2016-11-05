package io.ashdavies.samples.booking.data.cache;

import java.util.Collection;

import javax.inject.Inject;

import io.ashdavies.commons.repository.Repository;
import io.ashdavies.samples.booking.domain.entity.TableEntity;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class TableCacheRepository implements Repository<TableEntity>, CacheRepository<TableEntity> {
    private final EntityDataStore<TableEntity> store;

    @Inject
    public TableCacheRepository(Configuration configuration) {
        this(new EntityDataStore<TableEntity>(configuration));
    }

    public TableCacheRepository(EntityDataStore<TableEntity> store) {
        this.store = store;
    }

    @Override public Observable<Collection<TableEntity>> get() {
        return Observable.create(new Observable.OnSubscribe<Collection<TableEntity>>() {
            @Override public void call(Subscriber<? super Collection<TableEntity>> subscriber) {
                subscriber.onNext(store.select(TableEntity.class).get().toList());
                subscriber.onCompleted();
            }
        });
    }

    @Override public void store(Collection<TableEntity> collection) {
        store.upsert(collection);
    }

    public void store(TableEntity table) {
        store.upsert(table);
    }

    public void reset() {
        store.select(TableEntity.class).get().toObservable()
                .map(new Func1<TableEntity, TableEntity>() {
                    @Override public TableEntity call(TableEntity entity) {
                        return entity.toBuilder()
                                .available(true)
                                .build();
                    }
                })
                .subscribe(new Action1<TableEntity>() {
                    @Override public void call(TableEntity entity) {
                        store(entity);
                    }
                });
    }
}
