package com.examenmayo.domain.repository;

import com.examenmayo.domain.model.Combate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CombateRepository extends JpaRepository<Combate, UUID> {
}
