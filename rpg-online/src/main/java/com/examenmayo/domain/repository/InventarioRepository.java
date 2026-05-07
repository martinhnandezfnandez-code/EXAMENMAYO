package com.examenmayo.domain.repository;

import com.examenmayo.domain.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, UUID> {
    Optional<Inventario> findByPersonajeId(UUID personajeId);
}
