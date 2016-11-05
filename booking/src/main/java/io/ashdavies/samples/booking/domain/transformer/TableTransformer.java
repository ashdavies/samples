package io.ashdavies.samples.booking.domain.transformer;

import java.util.concurrent.atomic.AtomicInteger;

import io.ashdavies.commons.transformer.Transformer;
import io.ashdavies.samples.booking.domain.entity.TableEntity;

public class TableTransformer implements Transformer<Boolean, TableEntity> {
    private final AtomicInteger integer;

    public TableTransformer() {
        this(new AtomicInteger());
    }

    public TableTransformer(AtomicInteger integer) {
        this.integer = integer;
    }

    @Override
    public TableEntity transform(Boolean bool) {
        return TableEntity.create(integer.getAndIncrement(), bool);
    }
}
