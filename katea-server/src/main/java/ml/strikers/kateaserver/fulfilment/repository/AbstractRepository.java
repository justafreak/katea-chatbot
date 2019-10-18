package ml.strikers.kateaserver.fulfilment.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.datastore.Datastore;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public abstract class AbstractRepository<T, ID> {

    final Datastore datastore;

    @Autowired
    ObjectMapper objectMapper;

    public AbstractRepository(Datastore datastore) {
        this.datastore = datastore;
    }

    public abstract T save(T entity);

}
