package com.examenmayo.infrastructure.persistence.json;

import com.examenmayo.domain.model.*;
import java.util.*;

public class PlayerJsonRepository implements GenericRepository<Jugador> {
    private final JsonDatabase db;
    private final String storeName;

    public PlayerJsonRepository(JsonDatabase db, String storeName) {
        this.db = db;
        this.storeName = storeName;
    }

    @Override
    public Jugador save(Jugador entity) {
        if (entity.getId() == null) entity.setId(UUID.randomUUID());
        db.save(storeName, entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Jugador> findById(UUID id) {
        return db.findById(storeName, id, Jugador.class);
    }

    @Override
    public List<Jugador> findAll() {
        return db.findAll(storeName, Jugador.class);
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
    public long count() {
        return db.count(storeName);
    }

    @Override
    public boolean existsById(UUID id) {
        return db.existsById(storeName, id);
    }
}
