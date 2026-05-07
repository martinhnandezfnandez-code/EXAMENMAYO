package com.examenmayo.infrastructure.persistence.json;

import com.examenmayo.domain.model.*;
import java.util.*;

public class MissionJsonRepository implements GenericRepository<Mision> {
    private final JsonDatabase db;
    private final String storeName;

    public MissionJsonRepository(JsonDatabase db, String storeName) {
        this.db = db;
        this.storeName = storeName;
    }

    @Override public Mision save(Mision entity) {
        if (entity.getId() == null) entity.setId(UUID.randomUUID());
        db.save(storeName, entity.getId(), entity);
        return entity;
    }

    @Override public Optional<Mision> findById(UUID id) { return db.findById(storeName, id, Mision.class); }
    @Override public List<Mision> findAll() { return db.findAll(storeName, Mision.class); }
    @Override public void deleteById(UUID id) { db.deleteById(storeName, id); }
    @Override public void deleteAll() { db.deleteAll(storeName); }
    @Override public long count() { return db.count(storeName); }
    @Override public boolean existsById(UUID id) { return db.existsById(storeName, id); }
}
