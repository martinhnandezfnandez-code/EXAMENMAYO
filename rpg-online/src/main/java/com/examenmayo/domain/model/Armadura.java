package com.examenmayo.domain.model;

import com.examenmayo.domain.enums.Rareza;
import com.examenmayo.domain.enums.TipoObjeto;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@DiscriminatorValue("ARMADURA")
@Getter
@Setter
@NoArgsConstructor
public class Armadura extends Objeto {

    @PositiveOrZero
    @Column(nullable = false)
    private int defensaExtra;

    @Builder
    public Armadura(String nombre, Rareza rareza, int valor, int defensaExtra) {
        super(nombre, TipoObjeto.ARMADURA, rareza, valor);
        this.defensaExtra = defensaExtra;
    }
}
