package io.ashdavies.samples.booking.domain.transformer;

import io.ashdavies.commons.transformer.ListTransformer;
import io.ashdavies.samples.booking.domain.entity.TableEntity;

public final class TableListTransformer extends ListTransformer<Boolean, TableEntity> {
    public TableListTransformer() {
        super(new TableTransformer());
    }
}
