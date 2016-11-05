package io.ashdavies.samples.booking.domain.factory;

import java.util.Collection;

import javax.inject.Inject;

import io.ashdavies.commons.repository.Repository;
import io.ashdavies.samples.booking.data.cache.CustomerCacheRepository;
import io.ashdavies.samples.booking.data.network.CustomerNetworkRepository;
import io.ashdavies.samples.booking.domain.entity.CustomerEntity;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class CustomerRepositoryFactory implements Repository<CustomerEntity> {
    @Inject CustomerNetworkRepository network;
    @Inject CustomerCacheRepository cache;
    @Inject Scheduler scheduler;

    @Inject
    public CustomerRepositoryFactory() {
    }

    @Override
    public Observable<Collection<CustomerEntity>> get() {
        return Observable.concat(cache.get(), network.get())
                .filter(new Func1<Collection<CustomerEntity>, Boolean>() {
                    @Override public Boolean call(Collection<CustomerEntity> customers) {
                        return !customers.isEmpty();
                    }
                })
                .doOnNext(new Action1<Collection<CustomerEntity>>() {
                    @Override public void call(Collection<CustomerEntity> tables) {
                        cache.store(tables);
                    }
                })
                .first()
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread());
    }
}
