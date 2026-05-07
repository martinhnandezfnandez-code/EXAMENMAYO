package com.examenmayo.domain.repository;

import com.examenmayo.domain.model.Mision;
import com.examenmayo.domain.enums.EstadoMision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface MisionRepository extends JpaRepository<Mision, UUID> {
    List<Mision> findByEstado(EstadoMision estado);
    List<Mision> findByJugadoresId(UUID jugadorId);
}
