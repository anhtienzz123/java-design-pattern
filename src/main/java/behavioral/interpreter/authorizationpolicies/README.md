# Authorization Policies using Interpreter Pattern

This example demonstrates the **Interpreter Pattern** applied to authorization and access control policies. The implementation provides a domain-specific language (DSL) for defining complex authorization rules that can be parsed and evaluated at runtime.

## Pattern Overview

The Interpreter pattern defines a representation for a language's grammar and provides an interpreter to process sentences in that language. In this authorization system:

- **Abstract Expression**: `Expression` interface defines the contract for interpreting authorization rules
- **Terminal Expressions**: `RoleExpression`, `PermissionExpression`, `ResourceExpression`, `TimeBasedExpression`, `IpAddressExpression`
- **Non-terminal Expressions**: `AndExpression`, `OrExpression`, `NotExpression`
- **Context**: `SecurityContext` contains user and request information for evaluation
- **Client**: `AuthorizationEngine` parses policies and coordinates evaluation

## Key Components

### 1. Expression Interface
```java
public interface Expression {
    boolean interpret(SecurityContext context);
}
```

### 2. Security Context
The `SecurityContext` record contains all necessary information for policy evaluation:
- User ID and role
- User permissions
- Requested resource and operation
- Request timestamp and client IP address

### 3. Terminal Expressions
- **RoleExpression**: Checks if user has a specific role
- **PermissionExpression**: Verifies user permissions
- **ResourceExpression**: Matches requested resources (supports wildcards)
- **TimeBasedExpression**: Evaluates time-based conditions (business hours, after hours)
- **IpAddressExpression**: Checks IP address conditions (internal, external, specific IPs)

### 4. Non-terminal Expressions
- **AndExpression**: Logical AND operation between two expressions
- **OrExpression**: Logical OR operation between two expressions
- **NotExpression**: Logical NOT operation on an expression

### 5. Policy Parser
The `PolicyParser` converts string-based policy rules into expression trees using a simple grammar:

```
ROLE(admin) AND (PERMISSION(read) OR PERMISSION(write))
TIME(business_hours) AND IP(internal)
NOT (ROLE(guest) OR IP(external))
```

### 6. Authorization Engine
The `AuthorizationEngine` manages multiple policies and provides:
- Policy registration and management
- Authorization evaluation with detailed results
- Single policy evaluation capabilities

## Policy Language Syntax

The authorization DSL supports the following constructs:

### Terminal Expressions
- `ROLE(role_name)` - Check user role
- `PERMISSION(permission_name)` - Check user permission
- `RESOURCE(resource_pattern)` - Match resource (supports * wildcards)
- `TIME(condition)` - Time-based checks (business_hours, after_hours)
- `IP(condition)` - IP address checks (internal, external, or specific IP)

### Logical Operators
- `AND` - Both conditions must be true
- `OR` - Either condition must be true
- `NOT` - Negates the condition

### Grouping
- `()` - Parentheses for expression grouping and precedence

## Example Policies

```java
// Simple role-based access
"ROLE(admin)"

// Manager access during business hours
"ROLE(manager) AND TIME(business_hours)"

// Developer API access with specific permissions
"ROLE(developer) AND RESOURCE(/api/*) AND (PERMISSION(read) OR PERMISSION(write))"

// Internal users only
"IP(internal) AND (ROLE(employee) OR ROLE(contractor))"

// Emergency access outside business hours
"PERMISSION(emergency_access) AND NOT TIME(business_hours)"

// Sensitive data access with multiple conditions
"ROLE(data_analyst) AND PERMISSION(sensitive_read) AND TIME(business_hours) AND IP(internal)"
```

## Usage Example

```java
// Create authorization engine
AuthorizationEngine engine = new AuthorizationEngine();

// Add policies
engine.addPolicy("Admin Access", "ROLE(admin)");
engine.addPolicy("Developer API", "ROLE(developer) AND RESOURCE(/api/*)");

// Create security context
SecurityContext context = new SecurityContext(
    "user123", "developer", Set.of("read", "write"),
    "/api/users", "GET", LocalDateTime.now(), "192.168.1.100"
);

// Evaluate authorization
AuthorizationResult result = engine.authorize(context);
System.out.println("Authorized: " + result.authorized());

// Print detailed evaluation
result.printDetails();
```

## Benefits of This Implementation

1. **Flexibility**: Easy to add new types of expressions and conditions
2. **Maintainability**: Policy rules are expressed in a readable DSL
3. **Extensibility**: New terminal expressions can be added without modifying existing code
4. **Separation of Concerns**: Policy definition is separated from evaluation logic
5. **Reusability**: Parsed expressions can be cached and reused
6. **Debugging**: Detailed evaluation results help with troubleshooting

## Real-world Applications

This pattern is commonly used in:
- **Access Control Systems**: Role-based and attribute-based access control
- **API Gateways**: Request authorization and rate limiting
- **Cloud Security**: Resource access policies (AWS IAM, Azure RBAC)
- **Workflow Engines**: Business rule evaluation
- **Configuration Management**: Conditional settings evaluation

## Running the Example

```bash
# Compile the project
mvn compile

# Run the demonstration
mvn exec:java -Dexec.mainClass="behavioral.interpreter.authorizationpolicies.ZMain"

# Or run directly after compilation
java -cp target/classes behavioral.interpreter.authorizationpolicies.ZMain
```

The demonstration shows various authorization scenarios including admin access, time-based restrictions, API access controls, and emergency overrides.