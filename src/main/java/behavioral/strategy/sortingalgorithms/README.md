# Strategy Pattern: Sorting Algorithms

## Overview

This example demonstrates the **Strategy Pattern** using different sorting algorithms. The Strategy pattern allows you to define a family of algorithms, encapsulate each one, and make them interchangeable at runtime.

## Problem Solved

In many applications, you need to sort data, but the optimal sorting algorithm depends on factors like:
- Data size
- Data characteristics (nearly sorted, random, etc.)
- Performance requirements
- Memory constraints

Instead of hardcoding a single sorting algorithm, the Strategy pattern allows you to:
- Switch between different sorting algorithms at runtime
- Add new sorting algorithms without modifying existing code
- Compare performance of different algorithms
- Choose the best algorithm based on context

## Structure

### Strategy Interface
- **`SortingStrategy`**: Defines the contract for all sorting algorithms

### Concrete Strategies
- **`BubbleSortStrategy`**: Simple O(n²) algorithm, good for small datasets
- **`QuickSortStrategy`**: Efficient O(n log n) average case, good for general use
- **`MergeSortStrategy`**: Stable O(n log n) algorithm, consistent performance

### Context
- **`SortingContext`**: Maintains a reference to a strategy and delegates sorting operations

## Key Benefits

1. **Algorithm Interchangeability**: Switch sorting algorithms at runtime
2. **Performance Optimization**: Choose the best algorithm for your data
3. **Extensibility**: Add new sorting algorithms without changing existing code
4. **Separation of Concerns**: Each algorithm is isolated in its own class
5. **Testability**: Each algorithm can be tested independently

## Usage Example

```java
SortingContext context = new SortingContext();
int[] data = {64, 34, 25, 12, 22, 11, 90};

// Use Bubble Sort for small arrays
context.setSortingStrategy(new BubbleSortStrategy());
context.performSort(data);

// Switch to Quick Sort for better performance
context.setSortingStrategy(new QuickSortStrategy());
context.performSort(data);

// Use Merge Sort for guaranteed O(n log n) performance
context.setSortingStrategy(new MergeSortStrategy());
context.performSort(data);
```

## Running the Example

```bash
# Compile
mvn compile

# Run the demonstration
mvn exec:java -Dexec.mainClass="behavioral.strategy.sortingalgorithms.ZMain"

# Or using java directly
java -cp target/classes behavioral.strategy.sortingalgorithms.ZMain
```

## Expected Output

The example demonstrates:
- Each sorting algorithm in action
- Performance comparison between algorithms
- Time measurements for different array sizes
- Graceful handling when no strategy is set

## When to Use This Pattern

- You have multiple ways to perform a task
- You want to switch algorithms at runtime
- You need to compare algorithm performance
- You want to isolate algorithm implementation details
- You anticipate adding new algorithms in the future

## Real-World Applications

- **Database Query Optimization**: Different join algorithms based on data size
- **Compression Algorithms**: Choose between speed vs. compression ratio
- **Pathfinding**: A* vs. Dijkstra vs. BFS based on graph characteristics
- **Machine Learning**: Different algorithms based on dataset size and features
- **Game AI**: Different difficulty levels using different AI strategies