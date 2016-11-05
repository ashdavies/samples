package io.ashdavies.samples.booking.data.service;

import java.util.List;

import io.ashdavies.samples.booking.domain.entity.CustomerEntity;
import retrofit2.http.GET;
import rx.Observable;

public interface BookingService {

    @GET("/customers")
    Observable<List<CustomerEntity>> getCustomerList();

    @GET("/tables")
    Observable<List<Boolean>> getTableMap();
}
