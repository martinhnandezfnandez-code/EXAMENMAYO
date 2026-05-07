package com.examenmayo.domain.repository;

import com.examenmayo.domain.model.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, UUID> {
    Optional<Jugador> findByNickname(String nickname);
    boolean existsByNickname(String nickname);

    @Query("SELECT j FROM Jugador j LEFT JOIN FETCH j.personajes WHERE j.id = :id")
    Optional<Jugador> findByIdWithPersonajes(UUID id);
}
