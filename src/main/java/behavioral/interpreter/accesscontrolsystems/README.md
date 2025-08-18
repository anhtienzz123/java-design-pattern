# Access Control Systems using Interpreter Pattern

This example demonstrates a comprehensive **Access Control System** implementation using the **Interpreter Pattern**. The system provides an enterprise-grade Attribute-Based Access Control (ABAC) solution with policy evaluation, audit logging, caching, and multiple policy combining algorithms.

## Pattern Overview

The Interpreter pattern is used to define a domain-specific language (DSL) for access control rules and provide an interpreter to evaluate these rules at runtime. This implementation extends beyond basic interpretation to include:

- **Policy Management**: Multiple policies with different effects (PERMIT/DENY)
- **Policy Combining**: Various algorithms for combining multiple policy decisions
- **Performance Optimization**: Caching and efficient evaluation
- **Audit and Compliance**: Comprehensive logging of all access attempts
- **Scalability**: Support for multiple domains and concurrent evaluations

## Architecture Components

### 1. Core Expression Framework

```java
public interface AccessExpression {
    AccessResult interpret(AccessContext context);
    String getExpressionDescription();
}
```

### 2. Access Context (ABAC Model)

The system follows the Subject-Object-Action-Environment model:

- **Subject**: Who is requesting access (user, roles, attributes, clearance level)
- **Object**: What is being accessed (resource, type, owner, classification)
- **Action**: What operation is being performed (verb, type, parameters)
- **Environment**: Context of the request (time, location, IP, user agent)

### 3. Terminal Expressions

Individual evaluation components for different aspects:

- **SubjectExpression**: Evaluates user attributes, roles, groups, clearance levels
- **ObjectExpression**: Evaluates resource properties, ownership, classification, tags
- **ActionExpression**: Evaluates operations, verbs, categories, parameters
- **EnvironmentExpression**: Evaluates time, location, network, client information

### 4. Composite Expressions

Logical combinations of expressions:

- **AndExpression**: All conditions must be true
- **OrExpression**: At least one condition must be true
- **NotExpression**: Inverts the result
- **ConditionalExpression**: If-then-else logic

### 5. Policy Evaluation System

- **AccessRuleParser**: Converts string rules into expression trees
- **PolicyEvaluator**: Manages multiple policies and combining algorithms
- **AccessControlManager**: High-level interface with caching and auditing

## Access Control Language Syntax

### Basic Attribute Expressions

```
subject.role = "admin"                    # Check user role
subject.clearance >= 5                    # Check clearance level
subject.group = "managers"                # Check group membership
subject.attribute = "department=finance"  # Check custom attribute

object.type = "document"                  # Check resource type
object.owner = "$self"                    # Check if user owns resource
object.tag = "confidential"               # Check resource tag
object.classification <= subject.clearance # Compare classifications

action.verb = "read"                      # Check operation
action.category = "write"                 # Check operation category
action.parameter = "amount>100000"        # Check operation parameter

env.time = "business_hours"               # Check time conditions
env.network = "trusted"                   # Check network trust
env.location = "headquarters"             # Check physical location
env.ip = "192.168.1.100"                 # Check specific IP
```

### Logical Operators

```
# AND - All conditions must be true
subject.role = "manager" AND object.tag = "internal"

# OR - At least one condition must be true
action.verb = "read" OR action.verb = "view"

# NOT - Invert condition
NOT subject.role = "guest"

# Parentheses for grouping
(subject.role = "admin" OR subject.role = "manager") AND env.time = "business_hours"
```

### Conditional Logic

```
# IF-THEN-ELSE
IF subject.role = "manager" THEN action.category = "read" OR action.category = "write"

# IF-THEN (no else clause)
IF env.time = "after_hours" THEN subject.role = "admin"
```

### Comparison Operators

```
subject.clearance >= 5      # Greater than or equal
object.classification < 8   # Less than
subject.role != "guest"     # Not equals
subject.name contains "admin" # Contains
env.time > "2024-01-01T00:00:00" # Time comparison
```

## Policy Combining Algorithms

The system supports multiple algorithms for combining policy decisions:

### 1. Deny Overrides
- If any policy denies access, the final decision is DENY
- Used when security is paramount
- Default for most security-critical systems

### 2. Permit Overrides
- If any policy permits access, the final decision is PERMIT
- Used for systems with default-deny but need override capabilities
- Common in emergency access scenarios

### 3. First Applicable
- Uses the decision of the first applicable policy
- Policies are evaluated in order until one applies
- Provides predictable, ordered evaluation

### 4. Deny Unless Permit
- Denies access unless at least one policy explicitly permits
- Conservative approach requiring explicit permission
- Good for highly secure environments

### 5. Permit Unless Deny
- Permits access unless at least one policy explicitly denies
- Liberal approach assuming default permission
- Suitable for open collaboration environments

## Advanced Features

### 1. Caching System

- **Time-based Expiration**: Configurable TTL for cached decisions
- **Size Management**: Automatic eviction when cache limit reached
- **Performance Metrics**: Hit rate and performance statistics
- **Cache Key Generation**: Smart key generation based on context

### 2. Audit Logging

- **Comprehensive Logging**: All access attempts with full context
- **Performance Metrics**: Evaluation time tracking
- **Security Monitoring**: Failed access attempt tracking
- **Compliance Support**: Detailed audit trails for compliance requirements

### 3. Multi-Domain Support

- **Domain Separation**: Different policy sets for different systems
- **Scalable Architecture**: Support for multiple concurrent domains
- **Isolated Evaluation**: Domain-specific policy evaluation

## Usage Examples

### 1. Simple Role-Based Access

```java
PolicyEvaluator evaluator = new PolicyEvaluator(DENY_OVERRIDES);
evaluator.addPolicy("Admin Access", "subject.role = admin", PERMIT);

AccessControlManager manager = new AccessControlManager();
manager.registerEvaluator("documents", evaluator);

// Check access
boolean allowed = manager.isAccessAllowed("documents", context);
```

### 2. Complex ABAC Rules

```java
// Multi-condition policy
evaluator.addPolicy("Sensitive Data Access",
    "subject.clearance >= object.classification AND " +
    "env.time = business_hours AND " +
    "env.network = trusted AND " +
    "action.category = read",
    PERMIT);
```

### 3. Conditional Access

```java
// Emergency override policy
evaluator.addPolicy("Emergency Override",
    "IF subject.role = security_officer THEN " +
    "action.verb = emergency_access AND env.time = after_hours",
    PERMIT);
```

### 4. Time-Based Restrictions

```java
// Weekend restrictions
evaluator.addPolicy("Weekend Restrictions",
    "env.time = weekend AND action.category = write AND " +
    "NOT subject.role = administrator",
    DENY);
```

## Real-World Applications

This access control system is suitable for:

### Enterprise Applications
- **Document Management Systems**: Control access to sensitive documents
- **Financial Systems**: Restrict access to financial data and transactions
- **HR Systems**: Protect employee information and salary data
- **Healthcare Systems**: HIPAA-compliant patient data access control

### Cloud Platforms
- **Multi-tenant SaaS**: Tenant isolation and resource access control
- **API Gateways**: Request authorization and rate limiting
- **Microservices**: Service-to-service authentication and authorization

### Government and Military
- **Classified Information**: Multi-level security with clearance levels
- **Need-to-Know Access**: Fine-grained access control based on roles and attributes
- **Audit Requirements**: Comprehensive logging for compliance

### IoT and Smart Buildings
- **Device Access Control**: Control access to IoT devices and sensors
- **Location-Based Access**: Physical location-aware access control
- **Time-Sensitive Operations**: Schedule-based access restrictions

## Performance Characteristics

### Caching Benefits
- **Response Time**: Sub-millisecond response for cached decisions
- **Throughput**: Supports thousands of concurrent access checks
- **Memory Efficiency**: Configurable cache sizes with LRU eviction

### Scalability Features
- **Concurrent Evaluation**: Thread-safe policy evaluation
- **Horizontal Scaling**: Multiple manager instances with shared policies
- **Load Distribution**: Domain-based load distribution

## Security Considerations

### Policy Security
- **Input Validation**: Secure parsing of policy expressions
- **Injection Prevention**: Protection against policy injection attacks
- **Access Control**: Administrative access to policy management

### Runtime Security
- **Context Validation**: Validation of access context data
- **Audit Integrity**: Tamper-proof audit logging
- **Performance Protection**: Rate limiting and resource management

## Running the Example

```bash
# Compile the project
mvn compile

# Run the demonstration
mvn exec:java -Dexec.mainClass="behavioral.interpreter.accesscontrolsystems.ZMain"

# Or run directly after compilation
java -cp target/classes behavioral.interpreter.accesscontrolsystems.ZMain
```

The demonstration shows:

1. **Multiple Policy Domains**: Document management, financial systems, HR systems
2. **Various Access Scenarios**: Different users, roles, and resources
3. **Policy Combining**: Different algorithms for different domains
4. **Conditional Access**: IF-THEN-ELSE policy logic
5. **Performance Metrics**: Caching and audit statistics
6. **Comprehensive Logging**: Detailed access attempt logs

This implementation provides a production-ready access control system that can be adapted for various enterprise and cloud-native applications.