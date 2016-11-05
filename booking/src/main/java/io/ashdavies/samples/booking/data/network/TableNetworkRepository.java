package io.ashdavies.samples.booking.data.network;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import io.ashdavies.commons.repository.Repository;
import io.ashdavies.samples.booking.data.service.BookingService;
import io.ashdavies.samples.booking.domain.entity.TableEntity;
import io.ashdavies.samples.booking.domain.transformer.TableListTransformer;
import rx.Observable;
import rx.functions.Func1;

public class TableNetworkRepository implements Repository<TableEntity> {
    private static final TableListTransformer transformer = new TableListTransformer();

    private final BookingService service;

    @Inject
    public TableNetworkRepository(BookingService service) {
        this.service = service;
    }

    @Override public Observable<Collection<TableEntity>> get() {
        return service.getTableMap()
                .map(new Func1<Collection<Boolean>, Collection<TableEntity>>() {
                    @Override public Collection<TableEntity> call(Collection<Boolean> booleans) {
                        return transformer.transform(booleans);
                    }
                });
    }
}
