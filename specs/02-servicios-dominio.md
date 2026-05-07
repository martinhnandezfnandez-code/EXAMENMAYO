# Spec 2: Servicios de Dominio

## Requisitos
- Implementar servicios de dominio con lógica de negocio
- Strategy Pattern para cálculo de daño en combate
- ShopService con control de transacciones

## Acceptance Criteria
- [ ] CombatService: calcularDanio() usa Strategy Pattern, determinarGanador(), ejecutarTurno(), iniciarCombate()
- [ ] DamageStrategy interface con implementaciones: PhysicalDamageStrategy, MagicDamageStrategy, TrueDamageStrategy
- [ ] ShopService: comprarObjeto(), venderObjeto(), validarOro()
- [ ] ClanService: agregarMiembro(), expulsarMiembro(), calcularRanking()
- [ ] MisionService: completar(), asignarJugador(), listarPorEstado()
- [ ] PlayerService: subirNivel(), unirseClan(), getPersonajes()
- [ ] Tests unitarios de servicios pasan
- [ ] Código compila sin errores
