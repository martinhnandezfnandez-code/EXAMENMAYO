package com.examenmayo.domain.model;

import com.examenmayo.domain.enums.ClasePersonaje;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "personajes", indexes = {
    @Index(name = "idx_personaje_jugador", columnList = "jugador_id"),
    @Index(name = "idx_personaje_nombre", columnList = "nombre")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Personaje {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String nombre;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ClasePersonaje clase;

    @Column(nullable = false)
    @Builder.Default
    private int vida = 100;

    @Column(nullable = false)
    @Builder.Default
    private int mana = 50;

    @Column(nullable = false)
    @Builder.Default
    private int ataque = 10;

    @Column(nullable = false)
    @Builder.Default
    private int defensa = 5;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jugador_id", nullable = false)
    private Jugador jugador;

    @OneToOne(mappedBy = "personaje", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Inventario inventario = new Inventario();

    @PrePersist
    public void prePersist() {
        if (inventario != null) {
            inventario.setPersonaje(this);
        }
    }

    public int calcularPoderTotal() {
        int poderBase = this.ataque * 2 + this.defensa * 2 + this.vida + this.mana;
        if (inventario != null) {
            poderBase += inventario.listarObjetos().stream()
                    .mapToInt(o -> o instanceof Arma arma ? arma.getDanio()
                            : o instanceof Armadura armadura ? armadura.getDefensaExtra()
                            : 0)
                    .sum();
        }
        return poderBase;
    }

    public int atacar(Personaje defensor, DamageStrategy strategy) {
        return strategy.calcular(this, defensor);
    }

    public void recibirDanio(int danio) {
        int danioFinal = Math.max(0, danio - this.defensa);
        this.vida = Math.max(0, this.vida - danioFinal);
    }

    public void usarObjeto(Consumible consumible) {
        if (inventario == null || !inventario.listarObjetos().contains(consumible)) {
            throw new IllegalStateException("El objeto no está en el inventario");
        }
        consumible.aplicar(this);
        inventario.eliminarObjeto(consumible);
    }

    public boolean estaVivo() {
        return this.vida > 0;
    }
}
