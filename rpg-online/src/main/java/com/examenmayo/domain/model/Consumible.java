package com.examenmayo.domain.model;

import com.examenmayo.domain.enums.Rareza;
import com.examenmayo.domain.enums.TipoEfecto;
import com.examenmayo.domain.enums.TipoObjeto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@DiscriminatorValue("CONSUMIBLE")
@Getter
@Setter
@NoArgsConstructor
public class Consumible extends Objeto {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoEfecto efecto;

    @Positive
    @Column(nullable = false)
    private int potencia;

    @Builder
    public Consumible(String nombre, Rareza rareza, int valor, TipoEfecto efecto, int potencia) {
        super(nombre, TipoObjeto.CONSUMIBLE, rareza, valor);
        this.efecto = efecto;
        this.potencia = potencia;
    }

    public void aplicar(Personaje personaje) {
        switch (this.efecto) {
            case CURACION -> personaje.setVida(Math.min(100, personaje.getVida() + this.potencia));
            case MANA -> personaje.setMana(Math.min(100, personaje.getMana() + this.potencia));
            case BUFF_ATAQUE -> personaje.setAtaque(personaje.getAtaque() + this.potencia);
            case BUFF_DEFENSA -> personaje.setDefensa(personaje.getDefensa() + this.potencia);
            case REVIVIR -> { if (personaje.getVida() <= 0) personaje.setVida(this.potencia); }
        }
    }
}
