# Implementation Plan: User/Staff Authentication Module

## Overview
This implementation plan breaks down the User/Staff Authentication Module into discrete, actionable coding tasks following the DDD and CQRS patterns established in the Customer module.

## Task List

- [x] 1. Create domain enumerations





  - Create `DRoles` enum in `users/domain/enums/` with values: SALES, COUNTER, ACCOUNTING, OPERATIONS, SUPERADMIN, SUPPORT
  - Create `DCurrency` enum in `users/domain/enums/` with values: PEN, USD
  - _Requirements: 1.3, 2.3_






- [ ] 2. Create domain entities
  - [ ] 2.1 Implement DUser domain entity
    - Create `DUser` class in `users/domain/entities/` extending `BaseAbstractDomainEntity`
    - Add fields: `Optional<String> userName`, `String email`, `String passwordHash`
    - Implement `validateEmail()` method for email format validation


    - Implement `validatePasswordStrength(String password)` method
    - Override `toString()` excluding passwordHash for security
    - _Requirements: 1.1, 1.2, 1.4, 1.5, 6.1_



  - [ ] 2.2 Implement DStaff domain entity
    - Create `DStaff` class in `users/domain/entities/` extending `BaseAbstractDomainEntity`
    - Add fields: `String phoneNumber`, `Float salary`, `DCurrency currency`, `Optional<LocalDateTime> hireDate`, `DRoles role`, `Long userId`, `DUser user`




    - Implement `assignRole(DRoles newRole)` business method
    - Implement `updateSalary(Float newSalary, DCurrency newCurrency)` business method
    - Override `toString()` including user information
    - _Requirements: 2.1, 2.2, 2.3, 2.4, 2.5, 6.1_

- [x] 3. Create infrastructure enumerations


  - Create `Roles` enum in `users/infrastructure/enums/` matching DRoles values
  - Create `Currency` enum in `users/infrastructure/enums/` matching DCurrency values
  - _Requirements: 1.3, 2.3, 6.2_

- [ ] 4. Create infrastructure entities
  - [x] 4.1 Implement User JPA entity



    - Create `User` class in `users/infrastructure/entities/` extending `BaseAbstractEntity`
    - Add JPA annotations: `@Entity`, `@Table(name = "users")`


    - Add fields with proper column annotations: `userName`, `email`, `passwordHash`
    - Add `@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)` relationship to Staff
    - Use `@SuperBuilder(toBuilder = true)` for builder pattern
    - _Requirements: 1.1, 1.2, 1.4, 6.2_

  - [ ] 4.2 Implement Staff JPA entity
    - Create `Staff` class in `users/infrastructure/entities/` extending `BaseAbstractEntity`


    - Add JPA annotations: `@Entity`, `@Table(name = "staff")`
    - Add fields with proper column annotations: `phoneNumber`, `salary`, `currency`, `hireDate`, `role`
    - Add `@OneToOne @JoinColumn(name = "user_id", nullable = false, unique = true)` relationship to User
    - Use `@Enumerated(EnumType.STRING)` for role and currency
    - Use `@SuperBuilder(toBuilder = true)` for builder pattern
    - _Requirements: 2.1, 2.2, 2.3, 2.4, 2.5, 6.2_





- [ ] 5. Create MapStruct mappers




  - [ ] 5.1 Implement User mapper
    - Create `IUserLombokMapper` interface in `users/infrastructure/mappers/`
    - Add `@Mapper` annotation


    - Define `User toPersistence(DUser domainUser)` method
    - Define `DUser toDomain(User persistenceUser)` method
    - Add helper methods for `Optional<String>` conversions
    - Create `UserLombokMapper` implementation class
    - _Requirements: 6.3_






  - [ ] 5.2 Implement Staff mapper
    - Create `IStaffLombokMapper` interface in `users/infrastructure/mappers/`
    - Add `@Mapper` annotation
    - Define `Staff toPersistence(DStaff domainStaff)` method
    - Define `DStaff toDomain(Staff persistenceStaff)` method


    - Add helper methods for `Optional<LocalDateTime>` conversions
    - Add enum mapping methods for Roles and Currency
    - Create `StaffLombokMapper` implementation class
    - _Requirements: 6.3_




- [ ] 6. Create domain repository interfaces
  - [ ] 6.1 Create IUserRepository interface
    - Create interface in `users/domain/repositories/`
    - Define methods: `save()`, `update()`, `findById()`, `findByEmail()`, `findAll(Pageable)`, `deleteById()`, `existsByEmail()`
    - All methods use domain entities (DUser)
    - _Requirements: 5.1, 5.2, 5.3, 6.4_





  - [ ] 6.2 Create IStaffRepository interface
    - Create interface in `users/domain/repositories/`
    - Define methods: `save()`, `update()`, `findById()`, `findByUserId()`, `findByRole()`, `findAll(Pageable)`, `deleteById()`
    - All methods use domain entities (DStaff)
    - _Requirements: 5.3, 5.4, 6.4_

- [ ] 7. Create JPA repository interfaces
  - Create `IUserJpaRepository` in `users/infrastructure/repositories/interfaces/` extending `JpaRepository<User, Long>`


  - Add custom query methods: `findByEmail()`, `existsByEmail()`
  - Create `IStaffJpaRepository` in `users/infrastructure/repositories/interfaces/` extending `JpaRepository<Staff, Long>`
  - Add custom query methods: `findByUserId()`, `findByRole()`
  - _Requirements: 6.5_

- [ ] 8. Implement repository implementations
  - [x] 8.1 Implement UserRepository



    - Create `UserRepository` class in `users/infrastructure/repositories/impl/`
    - Inject `IUserJpaRepository` and `IUserLombokMapper`
    - Implement all methods from `IUserRepository`
    - Use mapper to convert between domain and infrastructure entities
    - Add `@Service` annotation
    - _Requirements: 6.5_


  - [ ] 8.2 Implement StaffRepository
    - Create `StaffRepository` class in `users/infrastructure/repositories/impl/`
    - Inject `IStaffJpaRepository` and `IStaffLombokMapper`
    - Implement all methods from `IStaffRepository`
    - Use mapper to convert between domain and infrastructure entities
    - Handle user relationship properly in conversions
    - Add `@Service` annotation
    - _Requirements: 6.5_


- [ ] 9. Create presentation DTOs
  - Create `CreateUserDto` in `users/presentation/dto/` with validation annotations
  - Create `CreateStaffDto` in `users/presentation/dto/` with validation annotations
  - Create `UserResponseDto` in `users/presentation/dto/` (exclude passwordHash)
  - Create `StaffResponseDto` in `users/presentation/dto/` (include user data)
  - Create `PaginatedUserRequestDto` extending `PaginatedRequestDto`

  - Create `PaginatedStaffRequestDto` extending `PaginatedRequestDto`
  - _Requirements: 7.2, 7.3, 7.4_

- [ ] 10. Create commands and command handlers
  - [ ] 10.1 Implement CreateUserCommand and handler
    - Create `CreateUserCommand` record in `users/application/commands/`
    - Create `CreateUserCommandHandler` in `users/application/commands/handlers/`
    - Inject `IUserRepository` and `ApplicationEventPublisher`

    - Validate email uniqueness using repository
    - Hash password using BCrypt
    - Create and save DUser entity
    - Publish `UserCreatedDomainEvent`
    - Add `@Service` and `@Transactional` annotations
    - _Requirements: 1.1, 1.2, 1.5, 7.1, 8.1, 8.4_


  - [ ] 10.2 Implement CreateStaffCommand and handler
    - Create `CreateStaffCommand` record in `users/application/commands/`
    - Create `CreateStaffCommandHandler` in `users/application/commands/handlers/`
    - Inject `IStaffRepository`, `IUserRepository`, and `ApplicationEventPublisher`





    - Validate that userId exists
    - Create and save DStaff entity
    - Publish `StaffCreatedDomainEvent`
    - Add `@Service` and `@Transactional` annotations
    - _Requirements: 2.1, 2.2, 2.3, 2.4, 7.1, 8.1, 8.4_

- [ ] 11. Create queries and query handlers
  - [x] 11.1 Implement GetUserByIdQuery and handler


    - Create `GetUserByIdQuery` record in `users/application/queries/`
    - Create `GetUserByIdQueryHandler` in `users/application/queries/handlers/`
    - Inject `IUserRepository`
    - Retrieve user by ID or throw exception
    - Add `@Service` annotation
    - _Requirements: 5.1_

  - [ ] 11.2 Implement UserPaginatedQuery and handler
    - Create `UserPaginatedQuery` record in `users/application/queries/`
    - Create `UserPaginatedQueryHandler` in `users/application/queries/handlers/`
    - Inject `IUserRepository`
    - Convert PaginatedUserRequestDto to Pageable
    - Return Page<DUser>
    - Add `@Service` annotation
    - _Requirements: 5.2, 5.3_

  - [ ] 11.3 Implement GetStaffByIdQuery and handler
    - Create `GetStaffByIdQuery` record in `users/application/queries/`
    - Create `GetStaffByIdQueryHandler` in `users/application/queries/handlers/`
    - Inject `IStaffRepository`
    - Retrieve staff by ID with user data or throw exception
    - Add `@Service` annotation
    - _Requirements: 5.1, 5.3_

  - [ ] 11.4 Implement StaffPaginatedQuery and handler
    - Create `StaffPaginatedQuery` record in `users/application/queries/`
    - Create `StaffPaginatedQueryHandler` in `users/application/queries/handlers/`
    - Inject `IStaffRepository`
    - Convert PaginatedStaffRequestDto to Pageable
    - Return Page<DStaff> with user data
    - Add `@Service` annotation
    - _Requirements: 5.2, 5.3_

  - [ ] 11.5 Implement GetStaffByRoleQuery and handler
    - Create `GetStaffByRoleQuery` record in `users/application/queries/`
    - Create `GetStaffByRoleQueryHandler` in `users/application/queries/handlers/`
    - Inject `IStaffRepository`
    - Filter staff by role
    - Return List<DStaff>
    - Add `@Service` annotation
    - _Requirements: 5.4_

- [ ] 12. Create domain events
  - Create `UserCreatedDomainEvent` in `users/application/events/` with userId and message
  - Create `StaffCreatedDomainEvent` in `users/application/events/` with staffId and message
  - _Requirements: 8.4_

- [ ] 13. Create REST controllers
  - [ ] 13.1 Implement UserController
    - Create `UserController` in `users/presentation/controllers/` extending `BaseV1Controller`
    - Inject command and query handlers
    - Implement POST `/users` endpoint for user creation
    - Implement GET `/users/{id}` endpoint for user retrieval
    - Implement GET `/users/paginados` endpoint with @ModelAttribute
    - Add OpenAPI annotations
    - Add `@RestController` and `@RequestMapping("/users")` annotations
    - _Requirements: 5.1, 5.2, 5.3, 7.3, 7.4, 8.1_

  - [ ] 13.2 Implement StaffController
    - Create `StaffController` in `users/presentation/controllers/` extending `BaseV1Controller`
    - Inject command and query handlers
    - Implement POST `/staff` endpoint for staff creation
    - Implement GET `/staff/{id}` endpoint for staff retrieval
    - Implement GET `/staff/paginados` endpoint with @ModelAttribute
    - Implement GET `/staff/by-role/{role}` endpoint for role filtering
    - Add OpenAPI annotations
    - Add `@RestController` and `@RequestMapping("/staff")` annotations
    - _Requirements: 5.1, 5.3, 5.4, 7.3, 7.4, 8.1_

- [ ]* 14. Create database migration
  - Create Flyway migration script for users and staff tables
  - Include proper indexes on email, user_id, and role columns
  - Add foreign key constraint from staff to users
  - _Requirements: 1.4, 2.1, 2.5_

- [ ]* 15. Add integration tests
  - [ ]* 15.1 Test CreateUserCommandHandler
    - Test successful user creation
    - Test duplicate email validation
    - Test password hashing
    - Test event publishing
    - _Requirements: 1.1, 1.2, 8.4_

  - [ ]* 15.2 Test CreateStaffCommandHandler
    - Test successful staff creation
    - Test invalid userId validation
    - Test event publishing
    - _Requirements: 2.1, 7.1, 8.4_

  - [ ]* 15.3 Test query handlers
    - Test GetUserByIdQueryHandler with valid and invalid IDs
    - Test UserPaginatedQueryHandler with different page sizes
    - Test GetStaffByIdQueryHandler with user data inclusion
    - Test StaffPaginatedQueryHandler pagination
    - Test GetStaffByRoleQueryHandler filtering
    - _Requirements: 5.1, 5.2, 5.3, 5.4_

  - [ ]* 15.4 Test REST controllers
    - Test UserController endpoints with MockMvc
    - Test StaffController endpoints with MockMvc
    - Test validation error responses
    - Test pagination query parameters
    - _Requirements: 7.3, 7.4_
