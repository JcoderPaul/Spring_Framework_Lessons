package spring.oldboy.repository;

import java.util.Optional;

/* Параметризуем как K-Key и E-Entity */
public interface CrudRepository<K, E> {

    Optional<E> findById(K id);

    void delete(E entity);
}
