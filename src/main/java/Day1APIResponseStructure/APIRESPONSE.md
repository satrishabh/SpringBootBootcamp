# Day 1 Lab: API Response Structure, Exception Handling & DTO Mapping

## Overview
This lab covers Spring Boot best practices for building robust backend APIs using standardized response structures, centralized exception handling, and MapStruct-based DTO mapping.

## Objective
Implement a consistent API response format across all controllers, centralize error handling, add pagination metadata, and use MapStruct for safe and efficient entity-to-DTO mapping.

## Core Topics
- **Standardized API Response Structure**
- **Global Exception Handling**
- **MapStruct DTO Mapping**
- **Pagination support**
- **API documentation and response logging**

## Why These Practices Matter

### Why standardized API responses?
- Ensures every endpoint returns the same high-level fields.
- Makes frontend and API consumer integration easier.
- Improves debugging by adding metadata like `statusCode`, `status`, `path`, and `apiVersion`.
- Enables consistent status and error handling across services.

### Why centralized exception handling?
- Prevents duplicate try/catch blocks across controllers.
- Converts all exceptions into a common error format.
- Supports validation errors, missing resources, business exceptions, and internal server errors in one place.
- Makes logging and monitoring more consistent.

### Why use MapStruct for DTO mapping?
- MapStruct generates mapping code at compile time.
- Avoids boilerplate conversion logic in controllers and services.
- Makes DTO and entity mapping type-safe and maintainable.
- Supports custom mapping methods and conditional updates.

## What We Build

### 1. Enhanced `ApiResponse` class
A reusable response wrapper that includes:
- `success`
- `message`
- `data`
- `timestamp`
- `statusCode`
- `status`
- `path`
- `apiVersion`
- `errorCode`
- `validationErrors`
- `pagination`

### 2. `PaginationMetadata`
A separate class for paginated responses with:
- `page`
- `size`
- `totalElements`
- `totalPages`
- `first`
- `last`
- `hasNext`
- `hasPrevious`

### 3. `ResponseStatus` enum
Standard response and error codes for consistent messages.

### 4. `BaseController`
Common controller methods for standardized responses:
- `success(...)`
- `created(...)`
- `updated(...)`
- `deleted()`
- `paginated(...)`
- `error(...)`
- `notFound(...)`
- `validationError(...)`

### 5. Global exception handler
A `@RestControllerAdvice` that handles:
- `ResourceNotFoundException`
- `MethodArgumentNotValidException`
- `ConstraintViolationException`
- `IllegalArgumentException`
- `BusinessException`
- generic `Exception`

### 6. Business exceptions
Custom exceptions such as:
- `BusinessException`
- `UserAlreadyExistsException`
- `InvalidCredentialsException`

### 7. MapStruct mappers
Mappers for entity/DTO conversion, including:
- `UserMapper`
- `ProjectMapper`
- `DateMapper`
- central `MapperConfig`

### 8. Response logging interceptor
A Spring interceptor that logs request URI, status, and duration for every API call.

### 9. Tests and documentation
- Controller unit tests
- Integration tests
- Postman collection
- API documentation examples

## Basic Topics: What and Why

### Standardized API Response
**What**: A wrapper object used by all endpoints to deliver response data and metadata.
**Why**: Minimizes ambiguity for API consumers and gives frontends a stable contract.

### Exception Handling
**What**: A centralized mechanism to catch exceptions and build uniform error responses.
**Why**: Prevents inconsistent error payloads and keeps error formatting predictable.

### DTO Mapping
**What**: Mapping between entity objects and data transfer objects (DTOs) for API exchange.
**Why**: Keeps the domain model separate from API models and avoids exposing internal data structures.

### Pagination
**What**: Metadata returned alongside list data to describe page size, number, and total counts.
**Why**: Enables client-side paging controls and reduces large payloads.

### API Versioning
**What**: Including `apiVersion` in each response.
**Why**: Makes it easier to evolve the API while supporting older clients.

## Response Examples

### Success Response
```json
{
  "success": true,
  "message": "Operation completed successfully",
  "data": {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com"
  },
  "timestamp": "2024-01-15T14:25:30.123",
  "statusCode": 200,
  "status": "OK",
  "apiVersion": "1.0",
  "path": "/api/v1/users/1"
}
```

### Paginated Response
```json
{
  "success": true,
  "message": "Operation completed successfully",
  "data": [
    { "id": 1, "username": "user1", "email": "user1@example.com" }
  ],
  "timestamp": "2024-01-15T14:25:30.123",
  "statusCode": 200,
  "status": "OK",
  "apiVersion": "1.0",
  "path": "/api/v1/users/paginated",
  "pagination": {
    "page": 0,
    "size": 5,
    "totalElements": 42,
    "totalPages": 9,
    "first": true,
    "last": false,
    "hasNext": true,
    "hasPrevious": false
  }
}
```

### Validation Error Response
```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "email": "Email must be valid",
    "password": "Password must be at least 8 characters"
  },
  "timestamp": "2024-01-15T14:25:30.123",
  "statusCode": 400,
  "status": "Bad Request",
  "apiVersion": "1.0",
  "path": "/api/v1/users",
  "errorCode": "VALIDATION_ERROR",
  "validationErrors": {
    "email": "Email must be valid",
    "password": "Password must be at least 8 characters"
  }
}
```

## Interview Questions

### Standardized API Responses
1. What is a standardized API response format? Why should you use one?
2. What fields are commonly included in a response wrapper like `ApiResponse`?
3. How does including `statusCode`, `status`, `path`, and `apiVersion` help API clients?
4. How do you handle success and error responses differently in a base controller?

### Exception Handling
5. What is `@RestControllerAdvice` and how is it used?
6. Why should validation and business exceptions be handled centrally?
7. What response do you return for a `MethodArgumentNotValidException`?
8. How can you map custom business exceptions to specific HTTP status codes?

### DTO Mapping
9. What is MapStruct and why is it useful in Spring Boot projects?
10. What is the difference between `@Mapping` and `@BeanMapping` in MapStruct?
11. How do you update an existing entity using a DTO while ignoring null values?
12. Why should DTOs be used instead of exposing JPA entities directly in API responses?

### Pagination and Metadata
13. What is `PaginationMetadata` and why should it be included in list endpoints?
14. How do you compute `totalPages` for pagination metadata?
15. What is the benefit of `hasNext` and `hasPrevious` fields?

### General Best Practices
16. How would you structure API versioning in response objects?
17. Why is response logging important for production APIs?
18. What are the advantages of writing integration tests for API response structure?

## Best Practices Summary
- Keep API responses consistent across all controllers.
- Use a base controller for repeated response-building logic.
- Return paginated metadata separately from the payload.
- Handle validation and business errors centrally.
- Use MapStruct for compile-time safe DTO mappings.
- Document response examples for frontend and API consumers.
- Log request lifecycle details for easier monitoring and debugging.

## Next Steps
- Implement the classes and controller methods described above.
- Add `UserController` endpoints under `/api/v1` and verify response structure.
- Write unit and integration tests to validate standard responses.
- Optionally add OpenAPI/Swagger documentation for the standardized API.
