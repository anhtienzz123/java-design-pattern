# Request Approval System - Chain of Responsibility Pattern

## Overview

This package demonstrates the **Chain of Responsibility** design pattern through a practical business scenario: a multi-level request approval system. In this system, different types of requests (expense reimbursements, purchase orders, budget allocations, etc.) need to be approved by appropriate levels of management based on the request amount and type.

## Purpose

The Chain of Responsibility pattern allows you to pass requests along a chain of handlers. Each handler decides either to process the request or to pass it to the next handler in the chain. This pattern promotes loose coupling by freeing objects from having to know which object will handle their request.

## How the Chain of Responsibility is Used

### Pattern Structure

1. **Handler Interface/Abstract Class**: `ApprovalHandler`

    - Defines the interface for handling requests
    - Maintains a reference to the next handler in the chain
    - Implements the chain traversal logic

2. **Concrete Handlers**:

    - `TeamLeaderApprover` - Handles small requests up to $1,000
    - `ManagerApprover` - Handles medium requests up to $10,000
    - `DirectorApprover` - Handles large requests up to $50,000
    - `VicePresidentApprover` - Handles very large requests up to $200,000

3. **Request Object**: `Request`
    - Contains the data being processed (amount, type, description, requester)
    - Includes different request types (expense reimbursement, purchase order, etc.)

### Chain Configuration

The approval chain is configured in a hierarchical order:

```
Team Leader → Manager → Director → Vice President
```

Each handler in the chain:

-   Checks if it can approve the request based on amount and type
-   If yes, processes the request and stops the chain
-   If no, forwards the request to the next handler
-   If no handler can process the request, it gets rejected

### Key Benefits

1. **Decoupling**: The sender doesn't need to know which specific handler will process the request
2. **Flexibility**: The chain can be configured dynamically and handlers can be added/removed easily
3. **Single Responsibility**: Each handler focuses on its specific approval criteria
4. **Open/Closed Principle**: New handler types can be added without modifying existing code

## Implementation Details

### Approval Logic

Each handler implements different approval criteria:

-   **Team Leader**: Approves expense reimbursements and purchase orders ≤ $1,000
-   **Manager**: Approves all request types except project funding ≤ $10,000
-   **Director**: Approves all request types ≤ $50,000
-   **Vice President**: Approves all request types ≤ $200,000

### Request Types

The system supports various business request types:

-   `EXPENSE_REIMBURSEMENT`: Employee expense claims
-   `PURCHASE_ORDER`: Equipment and software purchases
-   `BUDGET_ALLOCATION`: Department or project budgets
-   `SALARY_ADJUSTMENT`: Salary increases and bonuses
-   `PROJECT_FUNDING`: Major project investments

## Usage Example

```java
// Create the approval chain
ApprovalHandler teamLeader = new TeamLeaderApprover("Alice Johnson");
ApprovalHandler manager = new ManagerApprover("Bob Smith");
ApprovalHandler director = new DirectorApprover("Carol Davis");
ApprovalHandler vicePresident = new VicePresidentApprover("David Wilson");

// Configure the chain
teamLeader.setNext(manager)
          .setNext(director)
          .setNext(vicePresident);

// Create and process a request
Request request = new Request(5000.0, "New development server", "Jane Smith",
                             Request.RequestType.PURCHASE_ORDER);
teamLeader.handleRequest(request);
```

## Running the Demo

Execute the `ApprovalSystemDemo` class to see the pattern in action with various test scenarios:

```bash
java designpattern.behavioral.chainofresponsibility.approvalsystem.ApprovalSystemDemo
```

The demo will show how different requests are routed through the approval chain and handled by the appropriate level of management.

## Real-World Applications

This pattern is commonly used in:

-   Expense management systems
-   Document approval workflows
-   Authorization and authentication systems
-   Help desk ticket routing
-   Purchase order processing
-   Loan approval systems
-   Content moderation pipelines
