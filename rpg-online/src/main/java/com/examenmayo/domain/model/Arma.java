package com.examenmayo.domain.model;

import com.examenmayo.domain.enums.Rareza;
import com.examenmayo.domain.enums.TipoObjeto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@DiscriminatorValue("ARMA")
@Getter
@Setter
@NoArgsConstructor
public class Arma extends Objeto {

    @Positive
    @Column(nullable = false)
    private int danio;

    @Builder
    public Arma(String nombre, Rareza rareza, int valor, int danio) {
        super(nombre, TipoObjeto.ARMA, rareza, valor);
        this.danio = danio;
    }
}
