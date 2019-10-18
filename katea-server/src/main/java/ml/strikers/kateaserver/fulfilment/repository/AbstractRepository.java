package ml.strikers.kateaserver.fulfilment.repository;

import com.google.cloud.datastore.Datastore;

public abstract class AbstractRepository<T, ID> {

    final Datastore datastore;

    public AbstractRepository(Datastore datastore) {
        this.datastore = datastore;
    }

    public abstract T save(T entity);

}
