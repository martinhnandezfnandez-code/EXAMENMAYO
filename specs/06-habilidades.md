# Spec 6: Sistema de Habilidades

## Requisitos
- Sistema completo de habilidades RPG por clase
- Herencia y polimorfismo para distintos tipos de habilidad
- Cooldown, coste de maná, nivel requerido

## Acceptance Criteria
- [ ] Habilidad clase abstracta con: id, nombre, descripcion, costeMana, danio, cooldown, nivelRequerido
- [ ] HabilidadOfensiva, HabilidadCurativa, HabilidadBuff heredan de Habilidad
- [ ] SkillManager con: usarHabilidad(), desbloquearHabilidad(), verificarMana(), aplicarCooldown()
- [ ] Habilidades predefinidas por clase (Guerrero, Mago, Arquero)
- [ ] SkillFactory para crear habilidades según clase
- [ ] Integración con combate: usar habilidades en turnos
- [ ] Tests unitarios pasan
