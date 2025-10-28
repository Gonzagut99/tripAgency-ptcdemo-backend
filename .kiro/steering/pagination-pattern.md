# Pagination Pattern

## Overview
All `getAll` endpoints MUST use pagination following a consistent pattern across the application. This ensures performance and consistency in API responses.

## Implementation Rules

### 1. Pagination DTO Structure

Every module MUST have its own paginated request DTO that extends `PaginatedRequestDto`:

```java
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@TypeAlias("Paginated[Module]RequestDto")
@Getter
@Setter
public class Paginated[Module]RequestDto extends PaginatedRequestDto {
    public Paginated[Module]RequestDto(int page, int size) {
        super(page, size);
    }
}
```

**Examples:**
- `PaginatedCustomerRequestDto`
- `PaginatedUserRequestDto`
- `PaginatedStaffRequestDto`
- `PaginatedLiquidationRequestDto`

### 2. Base Pagination DTO

The base `PaginatedRequestDto` is located in `general/presentation/dtos/` and provides:
- Default page = 1 (normalized to 0 for Spring Data)
- Default size = 10
- Validation for minimum values
- Snake case JSON naming strategy
- `normalizePageNumber()` method to convert 1-based to 0-based indexing

### 3. Controller Pattern

Controllers MUST use `@ModelAttribute` to bind pagination parameters:

```java
@GetMapping("/paginados")
@Operation(summary = "Obtener [entities] paginados")
public Page<D[Entity]> getPaginated[Entities](@ModelAttribute Paginated[Module]RequestDto requestDto) {
    requestDto.normalizePageNumber();
    [Entity]PaginatedQuery query = new [Entity]PaginatedQuery(requestDto);
    return [entity]PaginatedQueryHandler.execute(query);
}
```

**Key Points:**
- Use `@ModelAttribute` NOT `@RequestBody` or `@RequestParam`
- Always call `normalizePageNumber()` before creating the query
- Return `Page<D[Entity]>` directly (domain entity)
- Endpoint path should be `/paginados` for Spanish or `/paginated` for English

### 4. CQRS Query Pattern

Create a specific query record for pagination:

```java
public record [Entity]PaginatedQuery(Paginated[Module]RequestDto requestDto) {}
```

### 5. Query Handler Pattern

The query handler converts the DTO to Spring Data `Pageable`:

```java
@Service
public class [Entity]PaginatedQueryHandler {
    private final I[Entity]Repository repository;
    
    public [Entity]PaginatedQueryHandler(I[Entity]Repository repository) {
        this.repository = repository;
    }
    
    public Page<D[Entity]> execute([Entity]PaginatedQuery query) {
        Pageable pageable = PageRequest.of(
            query.requestDto().getPage(),
            query.requestDto().getSize()
        );
        return repository.findAll(pageable);
    }
}
```

### 6. Repository Interface

Domain repository interface MUST include:

```java
Page<D[Entity]> findAll(Pageable pageConfig);
```

### 7. API Request Format

Clients send pagination parameters as query params:

```
GET /ptc/api/[module]/paginados?page=1&size=20
```

Or using snake_case (automatically converted):

```
GET /ptc/api/[module]/paginados?page=1&size=20
```

### 8. API Response Format

Spring Data returns standard Page structure:

```json
{
  "content": [...],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20,
    "offset": 0,
    "paged": true
  },
  "totalPages": 5,
  "totalElements": 100,
  "last": false,
  "first": true,
  "size": 20,
  "number": 0,
  "numberOfElements": 20,
  "empty": false
}
```

## Complete Example: Customer Module

### DTO
```java
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@TypeAlias("PaginatedCustomerRequestDto")
@Getter
@Setter
public class PaginatedCustomerRequestDto extends PaginatedRequestDto {
    public PaginatedCustomerRequestDto(int page, int size) {
        super(page, size);
    }
}
```

### Query
```java
public record CustomerPaginatedQuery(PaginatedCustomerRequestDto requestDto) {}
```

### Query Handler
```java
@Service
public class CustomerPaginatedQueryHandler {
    private final ICustomerRepository customerRepository;
    
    public CustomerPaginatedQueryHandler(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    public Page<DCustomer> execute(CustomerPaginatedQuery query) {
        Pageable pageable = PageRequest.of(
            query.requestDto().getPage(),
            query.requestDto().getSize()
        );
        return customerRepository.findAll(pageable);
    }
}
```

### Controller
```java
@GetMapping("/paginados")
@Operation(summary = "Obtener clientes paginados")
public Page<DCustomer> getPaginatedCustomers(@ModelAttribute PaginatedCustomerRequestDto requestDto) {
    requestDto.normalizePageNumber();
    CustomerPaginatedQuery query = new CustomerPaginatedQuery(requestDto);
    return customerPaginatedQueryHandler.execute(query);
}
```

## Anti-Patterns to Avoid

❌ **DON'T** use `Pageable` directly in controllers:
```java
// WRONG
public Page<DCustomer> getAll(Pageable pageable) { ... }
```

❌ **DON'T** use `@RequestParam` for pagination:
```java
// WRONG
public Page<DCustomer> getAll(@RequestParam int page, @RequestParam int size) { ... }
```

❌ **DON'T** return `List` for getAll operations:
```java
// WRONG
public List<DCustomer> getAll() { ... }
```

❌ **DON'T** forget to normalize page number:
```java
// WRONG - missing normalization
public Page<DCustomer> getPaginated(@ModelAttribute PaginatedCustomerRequestDto dto) {
    // Missing: dto.normalizePageNumber();
    ...
}
```

## Benefits

1. **Consistency**: Same pattern across all modules
2. **Type Safety**: Dedicated DTOs for each module
3. **Validation**: Built-in validation in base DTO
4. **Flexibility**: Easy to add module-specific filters later
5. **Performance**: Prevents loading all records at once
6. **API Standards**: Follows REST best practices
