package behavioral.strategy.sortingalgorithms;

// Strategy
public interface SortingStrategy {
	void sort(int[] array);
	String getAlgorithmName();
}