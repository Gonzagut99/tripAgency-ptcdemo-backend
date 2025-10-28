---
inclusion: always
---

# Implementation Plan & Development Guidelines

## Development Approach

Build the project systematically following DDD layers and CQRS patterns. Follow the existing Customer module as the reference implementation pattern.

## Phase 1: Foundation - User/Staff Authentication Module

### Domain Layer Implementation

- **Enums**: Create all domain enums with `D*` prefix in `[module]/domain/enums/`
  - `DRoles`, `DCurrency`, `DLiquidationStatus`, `DPaymentMethod`, etc.
- **Entities**:
  - `DUser` as aggregate root with `DStaff` as 1:1 relationship
  - Both extend `BaseAbstractDomainEntity`
  - Include business validation methods

### Application Layer (CQRS)

- **Commands**: `CreateUserCommand`, `UpdateUserRoleCommand`, `LoginCommand`
- **Queries**: `GetUserByIdQuery`, `GetAllStaffQuery`
- **Handlers**: Follow existing pattern from `CreateCustomerCommandHandler`

### Infrastructure Layer

- **JPA Entities**: `User`, `Staff` (no prefix) in `infrastructure/entities/`
- **Repositories**:
  - Interfaces in `infrastructure/repositories/interfaces/`
  - Implementations in `infrastructure/repositories/impl/`
- **Mappers**: Use MapStruct following `CustomerLombokMapper` pattern

### Presentation Layer

- **DTOs**: `CreateUserRequest`, `UserResponse`, `StaffResponse`
- **Controllers**: `AuthController`, `StaffController` with proper validation

## Phase 2: Core Business - Liquidation Aggregate (Domain-First)

### Critical Implementation Order

1. **Detail Entities First**: `DFlightBooking`, `DHotelBooking`, `DTour`
2. **Service Abstractions**: `DBaseAbstractService` → concrete services
3. **Supporting Entities**: `DPayment`, `DIncidency`
4. **Aggregate Root**: `DLiquidation` with all business logic

### Business Logic Requirements

- **Aggregate Root Pattern**: Only `DLiquidation` is publicly accessible
- **Composition Relationships**: Use `@OneToMany(cascade = ALL, orphanRemoval = true)`
- **Business Methods**:
  ```java
  public float calculateTotal() // Sum all service totals
  public void addFlightService(DFlightService fs) // Add + recalculate
  public void addPayment(DPayment p) // Add + update payment status
  public boolean isOverdue() // Check against payment deadline
  public void closeLiquidation() // Business state transition
  ```

### Repository Pattern

- **Single Repository**: Only `ILiquidationRepository` for the aggregate
- **No Child Repositories**: Services/Payments managed through aggregate root
- **Query Methods**: Support business queries (by status, customer, overdue)

## Phase 3: Liquidation Module - Application & Presentation Layers

### Application Layer (CQRS)

- **Commands**:
  - `CreateLiquidationCommand` (customerId, staffId)
  - `AddFlightServiceCommand`, `AddPaymentCommand`
  - `ValidatePaymentCommand`, `AddIncidencyCommand`
- **Queries**:
  - `GetLiquidationByIdQuery`
  - `GetLiquidationsByCustomerQuery`
  - `GetLiquidationsByStatusQuery`

### Infrastructure Implementation

- **Single Repository**: `LiquidationRepository` implementing domain interface
- **Comprehensive Mappers**: All entity mappings using MapStruct
- **No Child Repositories**: Maintain aggregate boundaries

### Presentation Layer

- **Complex DTOs**: `LiquidationResponse` showing complete aggregate state
- **Action DTOs**: `AddPaymentRequest`, `CreateLiquidationRequest`
- **REST Endpoints**: Follow RESTful patterns with proper HTTP methods

## Development Rules

### Entity Creation Pattern

1. Domain entities first with `D*` prefix
2. Infrastructure entities second (no prefix)
3. MapStruct mappers for bidirectional conversion
4. All entities extend `BaseAbstractDomainEntity`

### Business Logic Placement

- **Domain Layer**: All business rules and calculations
- **Application Layer**: Use case orchestration only
- **Infrastructure Layer**: Pure data persistence
- **Presentation Layer**: Input validation and response formatting

### Validation Strategy

- **Domain**: Constructor validation and business invariants
- **Infrastructure**: Bean Validation annotations
- **Presentation**: DTO validation with proper error responses

### Testing Approach

- **Domain**: Unit tests for business logic
- **Application**: Integration tests for command/query handlers
- **Infrastructure**: Repository tests with test containers
- **Presentation**: Controller tests with **MockMvc**
