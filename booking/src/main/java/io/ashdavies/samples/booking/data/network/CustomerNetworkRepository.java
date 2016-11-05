package io.ashdavies.samples.booking.data.network;

import java.util.Collection;

import javax.inject.Inject;

import io.ashdavies.commons.repository.Repository;
import io.ashdavies.samples.booking.data.service.BookingService;
import io.ashdavies.samples.booking.domain.entity.CustomerEntity;
import rx.Observable;

public class CustomerNetworkRepository implements Repository<CustomerEntity> {
    private final BookingService service;

    @Inject
    public CustomerNetworkRepository(BookingService service) {
        this.service = service;
    }

    @Override
    public Observable<Collection<CustomerEntity>> get() {
        return service.getCustomerList();
    }
}
