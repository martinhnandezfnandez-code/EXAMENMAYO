package com.examenmayo.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "inventarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Positive
    @Column(nullable = false)
    @Builder.Default
    private int capacidadMaxima = 20;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personaje_id", nullable = false)
    private Personaje personaje;

    @OneToMany(mappedBy = "inventario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Objeto> objetos = new ArrayList<>();

    public void agregarObjeto(Objeto objeto) {
        if (objetos.size() >= capacidadMaxima) {
            throw new IllegalStateException("Inventario lleno");
        }
        objeto.setInventario(this);
        this.objetos.add(objeto);
    }

    public void eliminarObjeto(Objeto objeto) {
        this.objetos.remove(objeto);
        objeto.setInventario(null);
    }

    public List<Objeto> listarObjetos() {
        return new ArrayList<>(objetos);
    }
}
