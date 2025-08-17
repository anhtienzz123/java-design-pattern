# Banking System - Facade Pattern Example

## Overview

This example demonstrates the **Facade Pattern** using a comprehensive banking system. The Facade pattern provides a simplified interface to a complex subsystem, hiding the intricate details of multiple interconnected services and presenting a unified, easy-to-use API for common banking operations.

## Problem Statement

Modern banking systems involve multiple complex subsystems that must work together:
- **Account Management**: Balance checking, debiting, crediting
- **Security**: Authentication, authorization, transaction logging
- **Transaction Processing**: ID generation, limit validation, transfer processing
- **Notifications**: SMS, email alerts for transactions

Without the Facade pattern, clients would need to:
1. Understand the complex interactions between all subsystems
2. Make multiple calls in the correct sequence
3. Handle coordination and error scenarios across systems
4. Manage the complexity of interdependent operations

## Solution

The Facade pattern solves this by providing a single, simplified interface (`BankingFacade`) that:
- Orchestrates complex operations across multiple subsystems
- Handles the proper sequence of calls and error handling
- Presents simple methods for common banking operations
- Encapsulates business logic and validation rules

## Architecture

```
BankingFacade (Facade)
├── AccountService (Subsystem)
├── SecurityService (Subsystem)
├── TransactionService (Subsystem)
└── NotificationService (Subsystem)
```

## Subsystem Components

### 1. AccountService
- **Purpose**: Manages account operations and balances
- **Responsibilities**:
  - Account validation
  - Balance inquiries
  - Debit/credit operations
  - Account data storage

### 2. SecurityService
- **Purpose**: Handles authentication, authorization, and security logging
- **Responsibilities**:
  - Customer authentication
  - Account access authorization
  - Transaction logging for audit trails
  - Security compliance

### 3. TransactionService
- **Purpose**: Processes and manages financial transactions
- **Responsibilities**:
  - Transaction ID generation
  - Transfer processing
  - Transaction limit validation
  - Transaction recording

### 4. NotificationService
- **Purpose**: Manages customer communications
- **Responsibilities**:
  - SMS notifications
  - Email notifications
  - Transaction alerts
  - Customer contact information lookup

## Facade Operations

### 1. Balance Inquiry
```java
BigDecimal balance = bankingFacade.checkBalance(username, password, accountNumber);
```

**Internal Process:**
1. Authenticate customer credentials
2. Authorize account access
3. Validate account existence
4. Retrieve account balance
5. Log security transaction

### 2. Money Transfer
```java
boolean success = bankingFacade.transferMoney(username, password, fromAccount, toAccount, amount);
```

**Internal Process:**
1. Authenticate customer
2. Authorize source account access
3. Validate both accounts
4. Check transaction limits
5. Verify sufficient funds
6. Generate transaction ID
7. Process transfer
8. Update account balances
9. Record transaction
10. Log security event
11. Send notifications

### 3. Cash Withdrawal
```java
boolean success = bankingFacade.withdrawMoney(username, password, accountNumber, amount);
```

**Internal Process:**
1. Authenticate customer
2. Authorize account access
3. Validate account
4. Check transaction limits
5. Process withdrawal
6. Generate transaction ID
7. Record transaction
8. Log security event
9. Send notifications

## Key Benefits

### 1. **Simplified Interface**
- Complex banking operations reduced to single method calls
- Clients don't need to understand subsystem interactions
- Consistent API for all banking operations

### 2. **Encapsulation**
- Business logic hidden within the facade
- Subsystem complexity abstracted away
- Internal changes don't affect client code

### 3. **Coordination**
- Proper sequencing of subsystem calls
- Centralized error handling and validation
- Transaction integrity across multiple systems

### 4. **Security**
- Centralized authentication and authorization
- Consistent security logging
- Standardized validation processes

### 5. **Maintainability**
- Changes to subsystems don't impact clients
- New features can be added without changing existing interfaces
- Single point of control for business rules

## Usage Examples

```java
BankingFacade bankingFacade = new BankingFacade();

// Simple balance check
BigDecimal balance = bankingFacade.checkBalance("john_doe", "password123", "12345");

// Money transfer with full validation and notifications
boolean transferSuccess = bankingFacade.transferMoney(
    "john_doe", "password123", "12345", "67890", new BigDecimal("100.00")
);

// Cash withdrawal with security logging
boolean withdrawalSuccess = bankingFacade.withdrawMoney(
    "jane_smith", "securePass456", "67890", new BigDecimal("200.00")
);
```

## Error Handling Scenarios

The facade handles various error conditions gracefully:

1. **Authentication Failures**: Invalid username/password combinations
2. **Authorization Failures**: Access to unauthorized accounts
3. **Invalid Accounts**: Non-existent account numbers
4. **Transaction Limits**: Operations exceeding daily limits
5. **Insufficient Funds**: Withdrawal/transfer amounts exceeding balance

## Real-World Applications

- **Online Banking Systems**: Web and mobile banking applications
- **ATM Networks**: Automated teller machine operations
- **Payment Gateways**: E-commerce payment processing
- **Financial APIs**: Third-party integration interfaces
- **Banking Microservices**: Service orchestration layers

## Running the Example

```bash
# Compile
mvn compile

# Run the demonstration
mvn exec:java -Dexec.mainClass="structural.facade.bankingsystem.ZMain"

# Or using java directly
java -cp target/classes structural.facade.bankingsystem.ZMain
```

## Sample Output

The demonstration shows:
- Successful operations with detailed subsystem interactions
- Failed authentication attempts
- Unauthorized access attempts
- Transaction limit violations
- Insufficient funds scenarios

Each operation that appears simple to the client actually involves multiple subsystem calls, security checks, validations, and notifications.

## Extension Points

This banking facade can be extended with additional operations:

- **Loan Applications**: Integration with credit scoring systems
- **Investment Services**: Portfolio management and trading
- **International Transfers**: Currency conversion and compliance
- **Fraud Detection**: Real-time transaction monitoring
- **Customer Service**: Account management and support operations

## Design Considerations

1. **Performance**: Consider caching for frequently accessed data
2. **Security**: Implement proper encryption and secure communication
3. **Scalability**: Design for high transaction volumes
4. **Reliability**: Implement transaction rollback mechanisms
5. **Compliance**: Ensure regulatory compliance and audit trails

The Facade pattern is particularly valuable in banking systems where complexity, security, and reliability are paramount, while still providing a simple interface for everyday operations.