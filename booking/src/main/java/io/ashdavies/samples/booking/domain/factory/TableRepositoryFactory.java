package io.ashdavies.samples.booking.domain.factory;

import java.util.Collection;

import javax.inject.Inject;

import io.ashdavies.commons.repository.Repository;
import io.ashdavies.samples.booking.data.cache.TableCacheRepository;
import io.ashdavies.samples.booking.data.network.TableNetworkRepository;
import io.ashdavies.samples.booking.domain.entity.TableEntity;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class TableRepositoryFactory implements Repository<TableEntity> {
    @Inject TableNetworkRepository network;
    @Inject TableCacheRepository cache;
    @Inject Scheduler scheduler;

    @Inject
    public TableRepositoryFactory() {
    }

    @Override
    public Observable<Collection<TableEntity>> get() {
        return Observable.concat(cache.get(), network.get())
                .filter(new Func1<Collection<TableEntity>, Boolean>() {
                    @Override public Boolean call(Collection<TableEntity> tables) {
                        return !tables.isEmpty();
                    }
                })
                .doOnNext(new Action1<Collection<TableEntity>>() {
                    @Override public void call(Collection<TableEntity> tables) {
                        cache.store(tables);
                    }
                })
                .first()
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void update(TableEntity table) {
        cache.store(table);
    }
}
