package com.examenmayo.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "jugadores", indexes = {
    @Index(name = "idx_jugador_nickname", columnList = "nickname", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false, unique = true, length = 50)
    private String nickname;

    @Column(nullable = false)
    @Builder.Default
    private int nivel = 1;

    @Column(nullable = false)
    @Builder.Default
    private long experiencia = 0;

    @Column(nullable = false, precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal oro = BigDecimal.ZERO;

    @Version
    private Long version;

    @OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Personaje> personajes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clan_id")
    private Clan clan;

    public void subirNivel() {
        this.nivel++;
        this.experiencia = 0;
    }

    public void unirseClan(Clan clan) {
        if (this.clan != null) {
            this.clan.getMiembros().remove(this);
        }
        this.clan = clan;
        if (clan != null && !clan.getMiembros().contains(this)) {
            clan.getMiembros().add(this);
        }
    }

    public Combate iniciarCombate(Personaje atacante, Personaje defensor) {
        return Combate.builder()
                .participantes(List.of(atacante, defensor))
                .build();
    }

    public void agregarExperiencia(long cantidad) {
        this.experiencia += cantidad;
        long expNecesaria = this.nivel * 100L;
        while (this.experiencia >= expNecesaria) {
            this.experiencia -= expNecesaria;
            subirNivel();
            expNecesaria = this.nivel * 100L;
        }
    }

    public void agregarOro(BigDecimal cantidad) {
        this.oro = this.oro.add(cantidad);
    }

    public void restarOro(BigDecimal cantidad) {
        if (this.oro.compareTo(cantidad) < 0) {
            throw new IllegalStateException("Oro insuficiente");
        }
        this.oro = this.oro.subtract(cantidad);
    }
}
