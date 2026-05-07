# Spec 1: Domain Model - Entidades Principales

## Requisitos
- Modelar Jugador, Personaje, Inventario, Objeto (Arma, Armadura, Consumible)
- Usar UUID para IDs
- JPA annotations para persistencia
- Encapsulación estricta

## Acceptance Criteria
- [ ] Jugador tiene: id, nickname, nivel, experiencia, oro; métodos subirNivel(), unirseClan(), iniciarCombate()
- [ ] Personaje tiene: nombre, clase, vida, mana, ataque, defensa; métodos calcularPoderTotal(), atacar(), recibirDanio(), usarObjeto()
- [ ] Inventario usa composición con objetos
- [ ] Objeto base con herencia: Arma (daño), Armadura (defensaExtra), Consumible (efecto)
- [ ] Clan con: nombre, lider, miembros, ranking; métodos agregarMiembro(), expulsarMiembro(), calcularRanking()
- [ ] Mision con: nombre, descripcion, recompensa, estado (PENDIENTE, EN_PROGRESO, COMPLETADA)
- [ ] Combate con: participantes, turnos, resultado
- [ ] Enums: ClasePersonaje, Rareza, TipoObjeto, EstadoMision, ResultadoCombate, TipoEfecto
- [ ] Tests unitarios de dominio pasan
- [ ] Código compila sin errores
