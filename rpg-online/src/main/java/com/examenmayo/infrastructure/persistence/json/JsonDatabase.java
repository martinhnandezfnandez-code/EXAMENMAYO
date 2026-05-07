package com.examenmayo.infrastructure.persistence.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class JsonDatabase {
    private final Path dataDir;
    private final ObjectMapper mapper;
    private final Map<String, Map<UUID, Object>> stores = new ConcurrentHashMap<>();

    public JsonDatabase(Path dataDir) {
        this.dataDir = dataDir;
        this.mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .enable(SerializationFeature.INDENT_OUTPUT)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        initDirectory();
    }

    public JsonDatabase() {
        this(Path.of("data"));
    }

    private void initDirectory() {
        try {
            Files.createDirectories(dataDir);
            log.info("Directorio de datos: {}", dataDir.toAbsolutePath());
        } catch (IOException e) {
            log.error("No se pudo crear el directorio de datos", e);
        }
    }

    public <T> void save(String storeName, UUID id, T entity) {
        stores.computeIfAbsent(storeName, k -> new ConcurrentHashMap<>());
        stores.get(storeName).put(id, entity);
        persistToFile(storeName);
    }

    public <T> Optional<T> findById(String storeName, UUID id, Class<T> type) {
        Map<UUID, Object> store = stores.get(storeName);
        if (store == null) return Optional.empty();
        Object entity = store.get(id);
        if (entity == null) return Optional.empty();
        return Optional.of(type.cast(entity));
    }

    public <T> List<T> findAll(String storeName, Class<T> type) {
        Map<UUID, Object> store = stores.get(storeName);
        if (store == null) return List.of();
        return store.values().stream()
                .map(type::cast)
                .collect(Collectors.toList());
    }

    public void deleteById(String storeName, UUID id) {
        Map<UUID, Object> store = stores.get(storeName);
        if (store != null) {
            store.remove(id);
            persistToFile(storeName);
        }
    }

    public void deleteAll(String storeName) {
        stores.remove(storeName);
        File file = getFile(storeName);
        if (file.exists()) file.delete();
    }

    public long count(String storeName) {
        Map<UUID, Object> store = stores.get(storeName);
        return store == null ? 0 : store.size();
    }

    public boolean existsById(String storeName, UUID id) {
        Map<UUID, Object> store = stores.get(storeName);
        return store != null && store.containsKey(id);
    }

    @SuppressWarnings("unchecked")
    public <T> void loadAll(String storeName, Class<T> type) {
        File file = getFile(storeName);
        if (!file.exists()) return;
        try {
            Map<String, Object> raw = mapper.readValue(file, new TypeReference<Map<String, Object>>() {});
            Map<UUID, Object> loaded = new ConcurrentHashMap<>();
            raw.forEach((key, value) -> {
                T entity = mapper.convertValue(value, type);
                loaded.put(UUID.fromString(key), entity);
            });
            stores.put(storeName, loaded);
            log.info("Cargados {} registros de {}", loaded.size(), storeName);
        } catch (IOException e) {
            log.warn("No se pudo cargar {}: {}", storeName, e.getMessage());
        }
    }

    public <T> void loadAll(String storeName, Class<T> type, Function<Map<String, Object>, T> deserializer) {
        File file = getFile(storeName);
        if (!file.exists()) return;
        try {
            Map<String, Object> raw = mapper.readValue(file, new TypeReference<Map<String, Object>>() {});
            Map<UUID, Object> loaded = new ConcurrentHashMap<>();
            raw.forEach((key, value) -> {
                if (value instanceof Map<?, ?> map) {
                    Map<String, Object> typedMap = new HashMap<>();
                    map.forEach((k, v) -> typedMap.put(String.valueOf(k), v));
                    T entity = deserializer.apply(typedMap);
                    loaded.put(UUID.fromString(key), entity);
                }
            });
            stores.put(storeName, loaded);
        } catch (IOException e) {
            log.warn("No se pudo cargar {}", storeName, e.getMessage());
        }
    }

    private void persistToFile(String storeName) {
        Map<UUID, Object> store = stores.get(storeName);
        if (store == null) return;
        try {
            Map<String, Object> serializable = new LinkedHashMap<>();
            store.forEach((id, entity) -> serializable.put(id.toString(), entity));
            mapper.writeValue(getFile(storeName), serializable);
        } catch (IOException e) {
            log.error("Error al persistir {}", storeName, e);
        }
    }

    private File getFile(String storeName) {
        return dataDir.resolve(storeName + ".json").toFile();
    }
}
