# Spec 5: Sistema de Experiencia Avanzada

## Requisitos
- Sistema de progresión RPG con fórmula escalable
- Strategy Pattern para curvas de experiencia
- Recompensas por nivel (atributos + habilidades)
- Integración con Personaje existente

## Acceptance Criteria
- [ ] ExperienceStrategy interface con calcularExpRequerida(int nivel)
- [ ] Implementaciones: QuadraticExpStrategy (nivel^2 * 100), LinearExpStrategy, FibonacciExpStrategy
- [ ] Personaje tiene: nivel, experienciaActual, experienciaNecesaria, puntosDeHabilidad
- [ ] ganarExperiencia() añade exp y sube nivel si corresponde
- [ ] subirNivel() mejora atributos y otorga puntos de habilidad
- [ ] desbloquearHabilidades() desbloquea skills según nivel y clase
- [ ] Tests unitarios pasan
