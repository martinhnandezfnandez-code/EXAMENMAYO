package com.examenmayo.domain.model;

import com.examenmayo.domain.enums.Rareza;
import com.examenmayo.domain.enums.TipoObjeto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "objetos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_objeto", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Objeto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoObjeto tipo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Rareza rareza;

    @PositiveOrZero
    @Column(nullable = false)
    private int valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventario_id")
    private Inventario inventario;

    protected Objeto(String nombre, TipoObjeto tipo, Rareza rareza, int valor) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.rareza = rareza;
        this.valor = valor;
    }
}
