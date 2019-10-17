package ml.strikers.kateaserver.fulfilment.repository;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.KeyFactory;

import java.util.Collection;

public abstract class AbstractRepository<T, ID> {

    final Datastore datastore;
    final KeyFactory keyFactory;

    public AbstractRepository(Datastore datastore, String kind) {
        this.keyFactory = datastore.newKeyFactory().setKind(kind);
        this.datastore = datastore;
    }

    public abstract T save(T entity);

    public abstract Collection<T> getEntityByProperty(String key, Object value);

}
