# Spec 4: Infraestructura y Persistencia

## Requisitos
- Repositorios JPA
- Configuración PostgreSQL/H2
- Seguridad preparada (Spring Security + JWT)
- Docker Compose

## Acceptance Criteria
- [ ] Repositorios JPA para todas las entidades
- [ ] application.yml con perfiles dev (H2) y prod (PostgreSQL)
- [ ] SecurityConfig con JWT filter placeholders
- [ ] Roles: PLAYER, ADMIN, MODERATOR
- [ ] docker-compose.yml con PostgreSQL
- [ ] Código compila sin errores
