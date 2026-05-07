package com.examenmayo.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "clanes", indexes = {
    @Index(name = "idx_clan_nombre", columnList = "nombre", unique = true),
    @Index(name = "idx_clan_ranking", columnList = "ranking")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lider_id", nullable = false)
    private Jugador lider;

    @OneToMany(mappedBy = "clan", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Jugador> miembros = new ArrayList<>();

    @Column(nullable = false)
    @Builder.Default
    private int ranking = 0;

    public void agregarMiembro(Jugador jugador) {
        if (!miembros.contains(jugador)) {
            jugador.unirseClan(this);
        }
    }

    public void expulsarMiembro(Jugador jugador) {
        if (jugador.equals(this.lider)) {
            throw new IllegalStateException("No se puede expulsar al líder del clan");
        }
        if (miembros.remove(jugador)) {
            jugador.setClan(null);
        }
    }

    public void calcularRanking() {
        this.ranking = miembros.stream()
                .mapToInt(Jugador::getNivel)
                .sum();
    }
}
