# Spec 7: Persistencia Simulada y Consola Retro

## Requisitos
- Persistencia JSON tipo Prisma ORM
- Interfaz de consola ASCII estilo Rogue 1980
- Game loop con menús interactivos

## Acceptance Criteria
- [ ] JsonDatabase con Jackson serialización a archivos .json
- [ ] GenericRepository<T> interfaz con save, findById, findAll, delete
- [ ] Repositorios concretos: PlayerRepository, CharacterRepository, SkillRepository, ClanRepository
- [ ] DataStore central que gestiona toda la persistencia
- [ ] GameEngine con game loop (init → update → render)
- [ ] ConsoleRenderer con estilo ASCII art
- [ ] GameMenu con opciones navegables
- [ ] InputHandler para entrada de teclado
- [ ] Screens: MainMenuScreen, CharacterScreen, CombatScreen, InventoryScreen, SkillScreen
- [ ] Integración completa con todos los sistemas existentes
- [ ] Tests unitarios pasan
