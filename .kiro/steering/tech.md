# Technology Stack

## Core Framework
- **Spring Boot 3.5.6** with Java 21
- **Spring Modulith** for modular monolith architecture
- **Spring Data JPA** with Hibernate for persistence
- **Spring Security** (currently disabled in main application)
- **PostgreSQL** as primary database

## Key Libraries & Tools
- **Lombok** for boilerplate code reduction
- **MapStruct 1.6.3** for entity mapping between layers
- **QueryDSL 5.1.0** for type-safe queries
- **Flyway** for database migrations
- **JMolecules** for CQRS and DDD annotations
- **SpringDoc OpenAPI** for API documentation
- **Bean Validation** for input validation

## Build System
**Maven** with custom annotation processors for Lombok, MapStruct, and QueryDSL.

## Common Commands
```bash
# Development
./mvnw spring-boot:run
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Build
./mvnw clean compile
./mvnw clean package

# Testing
./mvnw test
./mvnw spring-boot:test

# Database (Docker)
docker-compose up -d postgres
```

## Development Environment
- **Docker Compose** for local PostgreSQL (port 5434)
- **Spring Boot DevTools** for hot reload
- **Multiple profiles**: dev, prod
- **API Documentation**: Available at `/ptc/api/swagger-ui.html`