package ml.strikers.kateaserver.fulfilment.repository;

public interface Repository<T, ID> {
    T save(T entity);

    T getById(ID id);
}
