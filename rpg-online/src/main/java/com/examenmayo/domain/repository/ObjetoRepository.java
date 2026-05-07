package com.examenmayo.domain.repository;

import com.examenmayo.domain.model.Objeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ObjetoRepository extends JpaRepository<Objeto, UUID> {
    List<Objeto> findByInventarioId(UUID inventarioId);
    List<Objeto> findByTipo(String tipo);
}
