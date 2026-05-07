package com.examenmayo.infrastructure.persistence.json;

import com.examenmayo.domain.model.*;
import java.util.*;

public class ItemJsonRepository implements GenericRepository<Objeto> {
    private final JsonDatabase db;
    private final String storeName;

    public ItemJsonRepository(JsonDatabase db, String storeName) {
        this.db = db;
        this.storeName = storeName;
    }

    @Override public Objeto save(Objeto entity) {
        if (entity.getId() == null) entity.setId(UUID.randomUUID());
        db.save(storeName, entity.getId(), entity);
        return entity;
    }

    @Override public Optional<Objeto> findById(UUID id) { return db.findById(storeName, id, Objeto.class); }
    @Override public List<Objeto> findAll() { return db.findAll(storeName, Objeto.class); }
    @Override public void deleteById(UUID id) { db.deleteById(storeName, id); }
    @Override public void deleteAll() { db.deleteAll(storeName); }
    @Override public long count() { return db.count(storeName); }
    @Override public boolean existsById(UUID id) { return db.existsById(storeName, id); }
}
