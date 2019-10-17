package ml.strikers.kateaserver.fulfilment.repository;

import ml.strikers.kateaserver.fulfilment.entity.Hotel;

import java.util.List;

public interface Repository<T, ID> {
    T save(T entity);

    T getById(ID id);

    List<Hotel> getByCity(String city);
}
