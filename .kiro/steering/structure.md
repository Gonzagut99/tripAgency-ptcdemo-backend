# Project Structure & Architecture

## Package Organization
The project follows **Domain-Driven Design (DDD)** with **CQRS** patterns organized by business modules:

```
src/main/java/com/tripagency/ptc/ptcagencydemo/
├── [module]/                    # Business modules (e.g., customers)
│   ├── application/            # Application layer (CQRS)
│   │   ├── commands/          # Command handlers
│   │   ├── queries/           # Query handlers  
│   │   └── events/            # Domain events
│   ├── domain/                # Domain layer (pure business logic)
│   │   ├── entities/          # Domain entities (D* prefix)
│   │   ├── repositories/      # Repository interfaces
│   │   └── enums/            # Domain enums (D* prefix)
│   ├── infrastructure/        # Infrastructure layer
│   │   ├── entities/         # JPA entities
│   │   ├── repositories/     # Repository implementations
│   │   ├── mappers/          # MapStruct mappers
│   │   └── enums/           # Infrastructure enums
│   └── presentation/          # Presentation layer
│       ├── controllers/      # REST controllers
│       └── dto/             # Data Transfer Objects
└── general/                   # Shared components
    └── entities/             # Base entities
```

## Architectural Patterns

### Layer Separation
- **Domain**: Pure business logic, no external dependencies
- **Application**: Use cases, commands, queries, event handlers
- **Infrastructure**: Database, external services, technical concerns
- **Presentation**: REST APIs, DTOs, validation

### Entity Naming Conventions
- **Domain entities**: `D*` prefix (e.g., `DCustomer`)
- **Infrastructure entities**: No prefix (e.g., `Customer`)
- **Base classes**: `BaseAbstract*` prefix

### Repository Pattern
- Domain interfaces in `domain/repositories/`
- Infrastructure implementations in `infrastructure/repositories/impl/`
- JPA repositories in `infrastructure/repositories/interfaces/`

### Mapping Strategy
- **MapStruct** for domain ↔ infrastructure entity mapping
- **Manual mappers** implementing interfaces for complex transformations
- **Optional** fields handled explicitly in mappings

## Configuration Structure
- `application.properties` - Base configuration
- `application-{profile}.properties` - Environment-specific settings
- Context path: `/ptc/api/`
- Database migrations via Flyway