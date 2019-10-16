package ml.strikers.kateaserver.repository;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface AbstractRepository<T, ID> {
    T save(T entity) throws JsonProcessingException;

    T getById(ID id);

}
