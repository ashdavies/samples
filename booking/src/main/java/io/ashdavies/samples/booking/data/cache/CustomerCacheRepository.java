package io.ashdavies.samples.booking.data.cache;

import java.util.Collection;

import javax.inject.Inject;

import io.ashdavies.commons.repository.Repository;
import io.ashdavies.samples.booking.domain.entity.CustomerEntity;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import rx.Observable;
import rx.Subscriber;

public class CustomerCacheRepository implements Repository<CustomerEntity>, CacheRepository<CustomerEntity> {
    private final EntityDataStore<CustomerEntity> store;

    @Inject
    public CustomerCacheRepository(Configuration configuration) {
        this(new EntityDataStore<CustomerEntity>(configuration));
    }

    public CustomerCacheRepository(EntityDataStore<CustomerEntity> store) {
        this.store = store;
    }

    @Override public Observable<Collection<CustomerEntity>> get() {
        return Observable.create(new Observable.OnSubscribe<Collection<CustomerEntity>>() {
            @Override public void call(Subscriber<? super Collection<CustomerEntity>> subscriber) {
                subscriber.onNext(store.select(CustomerEntity.class).get().toList());
                subscriber.onCompleted();
            }
        });
    }

    @Override public void store(Collection<CustomerEntity> collection) {
        store.upsert(collection);
    }
}
