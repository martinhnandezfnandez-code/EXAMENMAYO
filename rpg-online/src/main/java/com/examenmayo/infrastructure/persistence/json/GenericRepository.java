package com.examenmayo.infrastructure.persistence.json;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenericRepository<T> {
    T save(T entity);
    Optional<T> findById(UUID id);
    List<T> findAll();
    void deleteById(UUID id);
    void deleteAll();
    long count();
    boolean existsById(UUID id);
}
