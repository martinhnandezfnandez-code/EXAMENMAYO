package com.examenmayo.domain.model;

import com.examenmayo.domain.enums.ResultadoCombate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "combates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Combate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "combate_participantes",
        joinColumns = @JoinColumn(name = "combate_id"),
        inverseJoinColumns = @JoinColumn(name = "personaje_id")
    )
    @Builder.Default
    private List<Personaje> participantes = new ArrayList<>();

    @Column(nullable = false)
    @Builder.Default
    private int turnos = 0;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private ResultadoCombate resultado = ResultadoCombate.EMPATE;

    @Transient
    private DamageStrategy strategy;

    public void iniciarCombate() {
        if (participantes.size() < 2) {
            throw new IllegalStateException("Se necesitan al menos 2 participantes");
        }
        this.turnos = 0;
        this.resultado = ResultadoCombate.EMPATE;
    }

    public void ejecutarTurno() {
        if (this.resultado != ResultadoCombate.EMPATE) return;

        this.turnos++;
        Personaje atacante = participantes.get(turnos % 2);
        Personaje defensor = participantes.get((turnos + 1) % 2);

        if (!atacante.estaVivo()) {
            this.resultado = ResultadoCombate.DERROTA;
            return;
        }

        int danio = calcularDanio(atacante, defensor);
        defensor.recibirDanio(danio);

        if (!defensor.estaVivo()) {
            this.resultado = ResultadoCombate.VICTORIA;
        }
    }

    public int calcularDanio(Personaje atacante, Personaje defensor) {
        if (strategy == null) {
            strategy = new PhysicalDamageStrategy();
        }
        return atacante.atacar(defensor, strategy);
    }

    public Personaje determinarGanador() {
        if (resultado == ResultadoCombate.EMPATE) return null;
        return participantes.stream()
                .filter(Personaje::estaVivo)
                .findFirst()
                .orElse(null);
    }

    public void setStrategy(DamageStrategy strategy) {
        this.strategy = strategy;
    }
}
