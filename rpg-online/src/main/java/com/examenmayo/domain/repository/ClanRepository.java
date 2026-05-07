package com.examenmayo.domain.repository;

import com.examenmayo.domain.model.Clan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClanRepository extends JpaRepository<Clan, UUID> {
    Optional<Clan> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
