package com.examenmayo.domain.repository;

import com.examenmayo.domain.model.Personaje;
import com.examenmayo.domain.enums.ClasePersonaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, UUID> {
    List<Personaje> findByJugadorId(UUID jugadorId);
    Optional<Personaje> findByNombreAndJugadorId(String nombre, UUID jugadorId);
    long countByClase(ClasePersonaje clase);

    @Query("SELECT p FROM Personaje p LEFT JOIN FETCH p.inventario WHERE p.id = :id")
    Optional<Personaje> findByIdWithInventario(UUID id);
}
