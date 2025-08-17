package behavioral.strategy.sortingalgorithms;

import java.util.Arrays;

// Context
public class SortingContext {
	private SortingStrategy strategy;
	
	public SortingContext() {
	}
	
	public SortingContext(SortingStrategy strategy) {
		this.strategy = strategy;
	}
	
	public void setSortingStrategy(SortingStrategy strategy) {
		this.strategy = strategy;
	}
	
	public void performSort(int[] array) {
		if (strategy == null) {
			System.out.println("No sorting strategy set. Cannot perform sort.");
			return;
		}
		
		System.out.println("Original array: " + Arrays.toString(array));
		System.out.println("Using: " + strategy.getAlgorithmName());
		
		long startTime = System.nanoTime();
		strategy.sort(array);
		long endTime = System.nanoTime();
		
		System.out.println("Sorted array: " + Arrays.toString(array));
		System.out.println("Time taken: " + (endTime - startTime) / 1_000_000.0 + " ms");
		System.out.println();
	}
}