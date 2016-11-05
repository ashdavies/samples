package io.ashdavies.samples.booking.domain.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import io.requery.Entity;
import io.requery.Key;

@Entity
@AutoValue
public abstract class TableEntity {
    @Key public abstract int getId();

    public abstract boolean isAvailable();

    public Builder toBuilder() {
        return new AutoValue_TableEntity.Builder(this);
    }

    public static TableEntity create(int id, boolean available) {
        return new AutoValue_TableEntity(id, available);
    }

    public static TypeAdapter<TableEntity> typeAdapter(Gson gson) {
        return new AutoValue_TableEntity.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_TableEntity.Builder();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder id(int id);
        public abstract Builder available(boolean available);

        public abstract TableEntity build();
    }
}
