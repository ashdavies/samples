package io.ashdavies.samples.booking.data.cache;

import java.util.Collection;

public interface CacheRepository<Entity> {
    void store(Collection<Entity> collection);
}
