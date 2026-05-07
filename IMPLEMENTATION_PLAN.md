# Implementation Plan - RPG Online

## Overview
RPG Online backend implementing DDD + Clean Architecture for a scalable MMORPG system.

## Modules

### Domain Layer (core, no external deps)
- `domain/enums/` - 7 enums: ClasePersonaje, Rareza, TipoObjeto, EstadoMision, ResultadoCombate, TipoEfecto, Role
- `domain/model/` - 11 classes: Jugador, Personaje, Inventario, Objeto (abstract), Arma, Armadura, Consumible, Clan, Combate, Mision, DamageStrategy + 3 implementations
- `domain/repository/` - 7 JPA repository interfaces
- `domain/service/` - 6 services: PlayerService, CharacterService, CombatService, ClanService, MisionService, ShopService
- `domain/exception/` - 4 custom exceptions

### Application Layer
- `application/dto/` - 7 DTO files (14 records: Request/Response pairs)
- `application/mapper/` - 1 MapStruct mapper interface

### Infrastructure Layer
- `infrastructure/persistence/` - JPA configuration
- `infrastructure/security/` - SecurityConfig + JwtAuthenticationFilter placeholder
- `infrastructure/configuration/` - OpenApiConfig, JpaConfig, DataInitializer

### Presentation Layer
- `presentation/controller/` - 6 REST controllers: Player, Character, Clan, Combat, Mission, Shop
- `presentation/advice/` - GlobalExceptionHandler with ProblemDetail

### Configuration
- `application.yml` - 3 profiles: dev (H2), prod (PostgreSQL), test (H2)
- `docker-compose.yml` - PostgreSQL 16 Alpine
- `Dockerfile` - Multi-stage build with Eclipse Temurin 21

### Testing (26 unit tests)
- JugadorTest (7 tests)
- PersonajeTest (6 tests)
- InventarioTest (3 tests)
- CombateTest (4 tests)
- MisionTest (3 tests)
- DamageStrategyTest (3 tests)
- ArmaTest (1 test)
- ConsumibleTest (2 tests)
- PersonajeClaseTest (2 tests)

## REST API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET/POST | /players | List/Create players |
| GET/PUT/DELETE | /players/{id} | CRUD player |
| POST | /players/{id}/level-up | Level up player |
| GET | /characters | List characters |
| GET/DELETE | /characters/{id} | CRUD character |
| POST | /characters/player/{id} | Create character for player |
| POST | /characters/{id}/use-item/{itemId} | Use consumable |
| GET/POST | /clans | List/Create clans |
| GET/DELETE | /clans/{id} | CRUD clan |
| POST/DELETE | /clans/{id}/members/{playerId} | Manage members |
| GET/POST | /combats | List/Start combat |
| GET | /combats/{id} | Get combat result |
| GET/POST | /missions | List/Create missions |
| GET/DELETE | /missions/{id} | CRUD mission |
| POST | /missions/{id}/assign/{playerId} | Assign mission |
| POST | /missions/{id}/complete | Complete mission |
| GET | /missions/status/{estado} | Filter by status |
| GET | /missions/player/{playerId} | Player missions |
| GET | /shop/catalog | List shop items |
| POST | /shop/buy | Buy item |
| POST | /shop/sell | Sell item |

## Architecture Decisions
- SINGLE_TABLE inheritance for items (performance)
- UUID primary keys (distributed-friendly)
- Strategy Pattern for damage (extensible)
- ProblemDetail for errors (RFC 9457)
- Profiles: H2 dev/test, PostgreSQL prod
- JWT filter placeholder (future auth)
