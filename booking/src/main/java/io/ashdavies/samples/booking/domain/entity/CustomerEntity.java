package io.ashdavies.samples.booking.domain.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import io.requery.Entity;
import io.requery.Key;

@Entity
@AutoValue
public abstract class CustomerEntity {
    @Key public abstract int getId();

    public abstract String getCustomerFirstName();
    public abstract String getCustomerLastName();

    public static CustomerEntity create(int id, String customerFirstName, String customerLastName) {
        return new AutoValue_CustomerEntity(id, customerFirstName, customerLastName);
    }

    public static TypeAdapter<CustomerEntity> typeAdapter(Gson gson) {
        return new AutoValue_CustomerEntity.GsonTypeAdapter(gson);
    }
}
