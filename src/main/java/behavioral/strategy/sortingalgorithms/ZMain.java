package behavioral.strategy.sortingalgorithms;

import java.util.Arrays;

public class ZMain {
	public static void main(String[] args) {
		SortingContext context = new SortingContext();
		
		int[] smallArray = {64, 34, 25, 12, 22, 11, 90};
		int[] mediumArray = {5, 2, 8, 1, 9, 3, 7, 4, 6};
		int[] largeArray = {100, 50, 75, 25, 85, 30, 95, 40, 60, 20, 80, 10, 70, 35, 90, 45, 65, 15, 55};
		
		System.out.println("=== Strategy Pattern: Sorting Algorithms ===\n");
		
		System.out.println("1. Bubble Sort Strategy:");
		context.setSortingStrategy(new BubbleSortStrategy());
		context.performSort(Arrays.copyOf(smallArray, smallArray.length));
		
		System.out.println("2. Quick Sort Strategy:");
		context.setSortingStrategy(new QuickSortStrategy());
		context.performSort(Arrays.copyOf(mediumArray, mediumArray.length));
		
		System.out.println("3. Merge Sort Strategy:");
		context.setSortingStrategy(new MergeSortStrategy());
		context.performSort(Arrays.copyOf(largeArray, largeArray.length));
		
		System.out.println("4. Performance Comparison with Large Array:");
		performanceComparison(largeArray);
		
		System.out.println("5. Testing without strategy:");
		context.setSortingStrategy(null);
		context.performSort(Arrays.copyOf(smallArray, smallArray.length));
	}
	
	private static void performanceComparison(int[] originalArray) {
		SortingContext context = new SortingContext();
		SortingStrategy[] strategies = {
			new BubbleSortStrategy(),
			new QuickSortStrategy(),
			new MergeSortStrategy()
		};
		
		System.out.println("Comparing performance on array of size: " + originalArray.length);
		
		for (SortingStrategy strategy : strategies) {
			int[] testArray = Arrays.copyOf(originalArray, originalArray.length);
			context.setSortingStrategy(strategy);
			
			long startTime = System.nanoTime();
			strategy.sort(testArray);
			long endTime = System.nanoTime();
			
			System.out.printf("%-12s: %.3f ms%n", 
				strategy.getAlgorithmName(), 
				(endTime - startTime) / 1_000_000.0);
		}
		System.out.println();
	}
}