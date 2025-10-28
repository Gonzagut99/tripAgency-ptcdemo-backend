# Requirements Document

## Introduction

This document defines the requirements for the User/Staff Authentication Module, which serves as the foundation for user management and authentication in the PTC Agency Demo system. The module manages user accounts, staff profiles, and role-based access control following Domain-Driven Design principles with CQRS architecture.

## Glossary

- **System**: The PTC Agency Demo application
- **User**: A domain entity representing an authenticated account in the system
- **Staff**: A domain entity representing an employee with specific roles and salary information
- **Role**: An enumeration defining access levels (SALES, COUNTER, ACCOUNTING, OPERATIONS, SUPERADMIN, SUPPORT)
- **Authentication**: The process of verifying user credentials
- **Domain Layer**: Pure business logic layer with no external dependencies
- **Infrastructure Layer**: Technical implementation layer handling persistence and external services

## Requirements

### Requirement 1: User Account Management

**User Story:** As a system administrator, I want to create and manage user accounts, so that staff members can access the system with proper credentials.

#### Acceptance Criteria

1. WHEN a new user account is created, THE System SHALL validate that the email is unique across all users
2. WHEN a new user account is created, THE System SHALL store the password as a secure hash
3. THE System SHALL allow optional username field for user accounts
4. WHEN user data is persisted, THE System SHALL extend BaseAbstractDomainEntity for audit tracking
5. THE System SHALL validate that email follows standard email format

### Requirement 2: Staff Profile Management

**User Story:** As a system administrator, I want to manage staff profiles linked to user accounts, so that employee information and roles are properly tracked.

#### Acceptance Criteria

1. WHEN a staff profile is created, THE System SHALL require a valid userId reference
2. THE System SHALL enforce a one-to-one relationship between User and Staff entities
3. WHEN staff salary is recorded, THE System SHALL store both amount and currency type
4. THE System SHALL allow optional hire date for staff records
5. WHEN staff data is queried, THE System SHALL include associated user information

### Requirement 3: Role-Based Access Control

**User Story:** As a system administrator, I want to assign roles to staff members, so that access permissions can be controlled based on job functions.

#### Acceptance Criteria

1. THE System SHALL support the following role types: SALES, COUNTER, ACCOUNTING, OPERATIONS, SUPERADMIN, SUPPORT
2. WHEN a staff member is assigned a role, THE System SHALL validate the role against the defined enumeration
3. THE System SHALL store role assignments in the domain layer using DRoles enumeration
4. THE System SHALL map domain roles to infrastructure roles for persistence

### Requirement 4: User Authentication

**User Story:** As a staff member, I want to log in with my credentials, so that I can access the system securely.

#### Acceptance Criteria

1. WHEN a user attempts to login, THE System SHALL verify the provided email exists
2. WHEN credentials are validated, THE System SHALL compare the provided password against the stored hash
3. IF authentication fails, THEN THE System SHALL return an appropriate error message
4. WHEN authentication succeeds, THE System SHALL return user and staff information
5. THE System SHALL not expose password hashes in any response

### Requirement 5: User Query Operations

**User Story:** As a system administrator, I want to query user and staff information, so that I can view and manage accounts effectively.

#### Acceptance Criteria

1. THE System SHALL provide query capability to retrieve user by unique identifier
2. THE System SHALL provide query capability to retrieve all staff members
3. WHEN staff data is retrieved, THE System SHALL include associated user details
4. THE System SHALL support filtering staff by role type
5. THE System SHALL return results following CQRS query patterns

### Requirement 6: Domain-Infrastructure Separation

**User Story:** As a developer, I want clear separation between domain and infrastructure layers, so that business logic remains independent of technical concerns.

#### Acceptance Criteria

1. THE System SHALL implement User and Staff entities in domain layer with D prefix
2. THE System SHALL implement corresponding JPA entities in infrastructure layer without prefix
3. THE System SHALL use MapStruct for bidirectional mapping between layers
4. THE System SHALL define repository interfaces in domain layer
5. THE System SHALL implement repository interfaces in infrastructure layer

### Requirement 7: Data Validation and Integrity

**User Story:** As a system, I want to enforce data validation rules, so that data integrity is maintained across all layers.

#### Acceptance Criteria

1. WHEN user data is created, THE System SHALL validate required fields in domain constructors
2. THE System SHALL apply Bean Validation annotations in infrastructure entities
3. THE System SHALL validate DTO inputs in presentation layer
4. WHEN validation fails, THE System SHALL return descriptive error messages
5. THE System SHALL prevent creation of staff without valid user reference

### Requirement 8: CQRS Command Operations

**User Story:** As a developer, I want to implement commands for state-changing operations, so that the system follows CQRS patterns consistently.

#### Acceptance Criteria

1. THE System SHALL implement CreateUserCommand for user creation
2. THE System SHALL implement UpdateUserRoleCommand for role modifications
3. WHEN a command is executed, THE System SHALL use transactional boundaries
4. WHEN a command succeeds, THE System SHALL publish domain events
5. THE System SHALL follow the command handler pattern established in Customer module
