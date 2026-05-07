package com.examenmayo.infrastructure.persistence.json;

import com.examenmayo.domain.model.*;
import java.util.*;

public class ClanJsonRepository implements GenericRepository<Clan> {
    private final JsonDatabase db;
    private final String storeName;

    public ClanJsonRepository(JsonDatabase db, String storeName) {
        this.db = db;
        this.storeName = storeName;
    }

    @Override public Clan save(Clan entity) {
        if (entity.getId() == null) entity.setId(UUID.randomUUID());
        db.save(storeName, entity.getId(), entity);
        return entity;
    }

    @Override public Optional<Clan> findById(UUID id) { return db.findById(storeName, id, Clan.class); }
    @Override public List<Clan> findAll() { return db.findAll(storeName, Clan.class); }
    @Override public void deleteById(UUID id) { db.deleteById(storeName, id); }
    @Override public void deleteAll() { db.deleteAll(storeName); }
    @Override public long count() { return db.count(storeName); }
    @Override public boolean existsById(UUID id) { return db.existsById(storeName, id); }
}
