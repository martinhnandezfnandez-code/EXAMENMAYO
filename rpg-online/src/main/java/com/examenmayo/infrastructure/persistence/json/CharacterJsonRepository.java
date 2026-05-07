package com.examenmayo.infrastructure.persistence.json;

import com.examenmayo.domain.model.*;
import java.util.*;

public class CharacterJsonRepository implements GenericRepository<Personaje> {
    private final JsonDatabase db;
    private final String storeName;

    public CharacterJsonRepository(JsonDatabase db, String storeName) {
        this.db = db;
        this.storeName = storeName;
    }

    @Override
    public Personaje save(Personaje entity) {
        if (entity.getId() == null) entity.setId(UUID.randomUUID());
        db.save(storeName, entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Personaje> findById(UUID id) {
        return db.findById(storeName, id, Personaje.class);
    }

    @Override
    public List<Personaje> findAll() {
        return db.findAll(storeName, Personaje.class);
    }

    @Override
    public void deleteById(UUID id) {
        db.deleteById(storeName, id);
    }

    @Override
    public void deleteAll() {
        db.deleteAll(storeName);
    }

    @Override
    public long count() { return db.count(storeName); }

    @Override
    public boolean existsById(UUID id) { return db.existsById(storeName, id); }
}
