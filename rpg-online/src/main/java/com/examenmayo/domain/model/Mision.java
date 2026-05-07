package com.examenmayo.domain.model;

import com.examenmayo.domain.enums.EstadoMision;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "misiones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mision {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @PositiveOrZero
    @Column(nullable = false)
    private int recompensa;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EstadoMision estado = EstadoMision.PENDIENTE;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "mision_jugadores",
        joinColumns = @JoinColumn(name = "mision_id"),
        inverseJoinColumns = @JoinColumn(name = "jugador_id")
    )
    @Builder.Default
    private List<Jugador> jugadores = new ArrayList<>();

    public void completar() {
        if (this.estado != EstadoMision.EN_PROGRESO) {
            throw new IllegalStateException("La misión debe estar EN_PROGRESO para completarse");
        }
        this.estado = EstadoMision.COMPLETADA;
        this.jugadores.forEach(j -> j.agregarExperiencia(this.recompensa));
    }

    public void asignarJugador(Jugador jugador) {
        if (!this.jugadores.contains(jugador)) {
            this.jugadores.add(jugador);
        }
        if (this.estado == EstadoMision.PENDIENTE) {
            this.estado = EstadoMision.EN_PROGRESO;
        }
    }
}
