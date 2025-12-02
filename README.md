# PTC Agency Demo - Backend

Sistema de gestión para agencia de viajes **PTC (Peru Travel Company)** desarrollado con Spring Boot 3.5.

## 📋 Descripción del Negocio

PTC Agency es una plataforma de gestión integral para agencias de viajes que permite:

- **Gestión de Clientes**: Registro y administración de clientes con información detallada
- **Gestión de Liquidaciones**: Control completo del ciclo de vida de reservas de viaje
- **Gestión de Pagos**: Seguimiento de pagos con soporte para múltiples monedas y evidencias
- **Gestión de Servicios**: Tours, vuelos, hoteles y servicios adicionales
- **Gestión de Incidencias**: Registro y seguimiento de problemas
- **Sistema de Notificaciones**: Notificaciones en tiempo real vía SSE (Server-Sent Events)
- **Gestión de Staff**: Administración del personal de la agencia
- **Autenticación JWT**: Sistema seguro de autenticación con refresh tokens

## 🏗️ Arquitectura

### Clean Architecture + CQRS + DDD

El proyecto implementa una arquitectura limpia con separación clara de responsabilidades:

```
src/main/java/com/tripagency/ptc/ptcagencydemo/
├── auth/                    # Módulo de autenticación
├── customers/               # Módulo de clientes
├── liquidations/            # Módulo de liquidaciones (reservas)
├── notifications/           # Módulo de notificaciones
├── users/                   # Módulo de usuarios
├── staff/                   # Módulo de personal
├── general/                 # Utilidades compartidas
├── config/                  # Configuraciones globales
└── web/                     # Configuración web (CORS, etc.)
```

### Estructura por Módulo (Ejemplo: Liquidations)

```
liquidations/
├── domain/                  # Capa de Dominio
│   ├── entities/           # Entidades de dominio (DLiquidation, DPayment, etc.)
│   ├── enums/              # Enumeraciones de dominio
│   ├── events/             # Eventos de dominio
│   └── repositories/       # Interfaces de repositorios
├── application/             # Capa de Aplicación
│   ├── commands/           # Comandos (Create, Update, Delete)
│   │   └── handlers/       # Manejadores de comandos
│   ├── queries/            # Consultas
│   │   └── handlers/       # Manejadores de consultas
│   ├── events/             # Manejadores de eventos
│   ├── services/           # Servicios de aplicación
│   └── dtos/               # DTOs de transferencia
├── infrastructure/          # Capa de Infraestructura
│   ├── entities/           # Entidades JPA
│   ├── repositories/       # Implementaciones de repositorios
│   │   ├── impl/          # Implementaciones concretas
│   │   └── interfaces/    # Interfaces JPA
│   └── mappers/            # Mappers (MapStruct)
├── presentation/            # Capa de Presentación
│   └── controllers/        # Controladores REST
└── package-info.java        # Configuración Spring Modulith
```

### Patrón CQRS (Command Query Responsibility Segregation)

- **Commands**: Operaciones de escritura (Create, Update, Delete)
- **Queries**: Operaciones de lectura
- Cada comando/query tiene su propio handler

```java
// Ejemplo de Command
public record CreateLiquidationCommand(CreateLiquidationDto dto) {}

// Ejemplo de Command Handler
@Service
public class CreateLiquidationCommandHandler {
    public DLiquidation execute(CreateLiquidationCommand command) {
        // Lógica de creación
    }
}
```

### Domain Events

Los eventos de dominio permiten comunicación desacoplada entre módulos:

```java
// Evento de dominio
public record LiquidationCreatedDomainEvent(
    Long liquidationId,
    String customerName,
    Long triggeredByUserId
) {}

// Handler que escucha el evento
@Component
public class LiquidationCreatedEventHandler {
    @EventListener
    public void handle(LiquidationCreatedDomainEvent event) {
        notificationService.sendNotification(...);
    }
}
```

## 🛠️ Stack Tecnológico

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | 21 | Lenguaje base |
| Spring Boot | 3.5.6 | Framework principal |
| Spring Security | 6.x | Autenticación y autorización |
| Spring Data JPA | 3.x | Persistencia |
| Spring Modulith | 1.4.3 | Arquitectura modular |
| PostgreSQL | 16+ | Base de datos |
| Flyway | - | Migraciones de BD |
| MapStruct | 1.6.3 | Mapeo de objetos |
| QueryDSL | 5.1.0 | Consultas type-safe |
| Lombok | - | Reducción de boilerplate |
| JWT (jjwt) | 0.12.6 | Tokens de autenticación |
| OpenAPI/Swagger | - | Documentación API |
| iText | 8.0.x | Generación de PDFs |
| Docker Compose | - | Contenedores de desarrollo |

## 🔐 Autenticación y Seguridad

### JWT Authentication Flow

1. **Login**: Usuario envía credenciales → Recibe `accessToken` + `refreshToken`
2. **Acceso**: Cliente envía `accessToken` en header `Authorization: Bearer <token>`
3. **Refresh**: Cuando `accessToken` expira, usa `refreshToken` para obtener nuevos tokens
4. **Logout**: Invalida tokens en servidor

### Gestión de Sesiones

- Soporte para múltiples sesiones por usuario
- Posibilidad de cerrar todas las sesiones
- Tracking de dispositivos y última actividad

### Endpoints de Auth

```
POST /auth/login          - Iniciar sesión
POST /auth/logout         - Cerrar sesión actual
POST /auth/logout-all     - Cerrar todas las sesiones
POST /auth/refresh        - Renovar tokens
GET  /auth/me             - Obtener usuario actual
GET  /auth/sessions       - Listar sesiones activas
POST /auth/change-password - Cambiar contraseña
```

## 📡 Sistema de Notificaciones

### Arquitectura de Notificaciones

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│  Domain Event   │───▶│  Event Handler   │───▶│ NotificationSvc │
└─────────────────┘    └──────────────────┘    └─────────────────┘
                                                        │
                              ┌─────────────────────────┼─────────────────────────┐
                              ▼                         ▼                         ▼
                       ┌────────────┐           ┌────────────┐           ┌────────────┐
                       │  Persist   │           │    SSE     │           │   Email    │
                       │    DB      │           │  Emitter   │           │  (future)  │
                       └────────────┘           └────────────┘           └────────────┘
```

### Tipos de Notificaciones

- `LIQUIDATION_CREATED`, `LIQUIDATION_STATUS_UPDATED`, `LIQUIDATION_DELETED`
- `PAYMENT_ADDED`, `PAYMENT_UPDATED`, `PAYMENT_DELETED`
- `SERVICE_CREATED`, `SERVICE_UPDATED`, `SERVICE_DELETED`
- `INCIDENCY_CREATED`, `INCIDENCY_RESOLVED`
- `CUSTOMER_CREATED`, `CUSTOMER_UPDATED`
- `STAFF_CREATED`, `STAFF_UPDATED`
- `USER_CREATED`, `USER_UPDATED`
- `SYSTEM_INFO`, `SYSTEM_WARNING`, `SYSTEM_ERROR`

### SSE (Server-Sent Events)

```
GET /notifications/subscribe/{userId}  - Suscripción SSE en tiempo real
GET /notifications/user/{userId}       - Obtener notificaciones paginadas
PUT /notifications/{id}/mark-as-read   - Marcar como leída
```

## 📦 Módulos del Sistema

### 1. Auth Module
- Autenticación JWT
- Gestión de sesiones
- Refresh tokens
- Cambio de contraseña

### 2. Users Module
- CRUD de usuarios
- Roles y permisos
- Asociación con Staff

### 3. Customers Module
- CRUD de clientes
- Información de contacto
- Historial de reservas

### 4. Liquidations Module
- Gestión de liquidaciones (reservas)
- Sub-entidades:
  - **Tours**: Paquetes turísticos
  - **Flight Bookings**: Reservas de vuelos
  - **Hotel Bookings**: Reservas de hoteles
  - **Additional Services**: Servicios extra
  - **Payments**: Pagos con evidencias
  - **Incidencies**: Incidencias

### 5. Staff Module
- Gestión del personal
- Roles: ADMIN, AGENT, ACCOUNTANT

### 6. Notifications Module
- Notificaciones persistentes
- SSE para tiempo real
- Historial por usuario

## 🗄️ Base de Datos

### Migraciones Flyway

```
V1__Create_sessions_table.sql       - Tabla de sesiones JWT
V2__Add_currency_to_payments.sql    - Soporte multi-moneda
V3__Add_evidence_url_to_payments.sql - Evidencias de pago
V4__Add_notification_type_columns.sql - Tipos de notificación
```

### Entidades Principales

```
users ─────────┬───── staff
               │
               └───── sessions

customers ─────────── liquidations
                           │
                           ├── tours
                           ├── flight_bookings
                           ├── hotel_bookings
                           ├── additional_services
                           ├── payments
                           └── incidencies

notifications ──────── user_notifications
```

## 🚀 Instalación y Ejecución

### Prerequisitos

- Java 21+
- Maven 3.9+
- Docker y Docker Compose
- PostgreSQL 16+ (o usar Docker)

### Desarrollo Local

1. **Clonar el repositorio**
```bash
git clone <repository-url>
cd ptcagencydemo
```

2. **Iniciar base de datos con Docker**
```bash
docker-compose up -d
```

3. **Ejecutar la aplicación**
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

4. **Acceder a Swagger UI**
```
http://localhost:8090/ptc/api/swagger-ui.html
```

### Variables de Entorno

```properties
# application-dev.properties
spring.datasource.url=jdbc:postgresql://localhost:5433/ptcdb
spring.datasource.username=postgres
spring.datasource.password=password

jwt.secret-key=<base64-encoded-secret>
jwt.access-token-expiration=3600000
jwt.refresh-token-expiration=604800000
```

## 📝 Buenas Prácticas Implementadas

### 1. Separación de Capas
- **Domain**: Lógica de negocio pura, sin dependencias externas
- **Application**: Casos de uso, orquestación
- **Infrastructure**: Implementaciones técnicas
- **Presentation**: API REST

### 2. Inmutabilidad
- Uso de `record` para DTOs y eventos
- Entidades de dominio con builders

### 3. Validación
- Bean Validation en DTOs
- Validación de negocio en handlers

### 4. Manejo de Errores
- Excepciones personalizadas por dominio
- `@ControllerAdvice` para manejo global
- Respuestas de error estandarizadas

### 5. Mapeo de Objetos
- MapStruct para conversiones type-safe
- Separación clara entre entidades de dominio e infraestructura

### 6. Soft Delete
- `isActive` flag en todas las entidades
- Auditoría con `createdDate` y `updatedDate`

### 7. Paginación
- Soporte completo de paginación en queries
- DTOs de request para parámetros de página

### 8. Documentación
- OpenAPI/Swagger automático
- Anotaciones `@Operation` en controllers

### 9. Modularidad
- Spring Modulith para verificar dependencias entre módulos
- `package-info.java` define contratos entre módulos

### 10. Testing
- Estructura preparada para tests unitarios e integración
- Perfiles separados para testing

## 🔗 API Endpoints Principales

### Liquidations
```
GET    /liquidations                    - Listar liquidaciones
GET    /liquidations/{id}               - Obtener por ID
POST   /liquidations                    - Crear liquidación
PUT    /liquidations/{id}/status        - Actualizar estado
DELETE /liquidations/{id}               - Eliminar (soft delete)
GET    /liquidations/{id}/quote-pdf     - Generar PDF de cotización
```

### Payments
```
POST   /liquidations/{id}/payments      - Agregar pago
PUT    /payments/{id}                   - Actualizar pago
PUT    /payments/{id}/status            - Actualizar estado de pago
DELETE /payments/{id}                   - Eliminar pago
POST   /payments/{id}/upload-evidence   - Subir evidencia
```

### Customers
```
GET    /customers                       - Listar clientes
GET    /customers/{id}                  - Obtener por ID
POST   /customers                       - Crear cliente
PUT    /customers/{id}                  - Actualizar cliente
DELETE /customers/{id}                  - Eliminar cliente
```

## 📊 Dashboard

El sistema provee endpoints para métricas del dashboard:

```
GET /liquidations/dashboard/summary     - Resumen de liquidaciones
GET /liquidations/dashboard/by-status   - Agrupado por estado
GET /liquidations/dashboard/by-month    - Agrupado por mes
GET /liquidations/upcoming-deadlines    - Próximos vencimientos
```

## 🤝 Contribución

1. Crear rama feature desde `develop`
2. Implementar cambios siguiendo la arquitectura
3. Agregar tests
4. Crear Pull Request

## 📄 Licencia

Proyecto privado - PTC Agency © 2025
