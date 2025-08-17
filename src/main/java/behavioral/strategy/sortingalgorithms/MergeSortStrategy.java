package behavioral.strategy.sortingalgorithms;

// Concrete Strategy
public class MergeSortStrategy implements SortingStrategy {
	
	@Override
	public void sort(int[] array) {
		mergeSort(array, 0, array.length - 1);
	}
	
	private void mergeSort(int[] array, int left, int right) {
		if (left < right) {
			int middle = left + (right - left) / 2;
			mergeSort(array, left, middle);
			mergeSort(array, middle + 1, right);
			merge(array, left, middle, right);
		}
	}
	
	private void merge(int[] array, int left, int middle, int right) {
		int leftSize = middle - left + 1;
		int rightSize = right - middle;
		
		int[] leftArray = new int[leftSize];
		int[] rightArray = new int[rightSize];
		
		System.arraycopy(array, left, leftArray, 0, leftSize);
		System.arraycopy(array, middle + 1, rightArray, 0, rightSize);
		
		int i = 0, j = 0, k = left;
		
		while (i < leftSize && j < rightSize) {
			if (leftArray[i] <= rightArray[j]) {
				array[k] = leftArray[i];
				i++;
			} else {
				array[k] = rightArray[j];
				j++;
			}
			k++;
		}
		
		while (i < leftSize) {
			array[k] = leftArray[i];
			i++;
			k++;
		}
		
		while (j < rightSize) {
			array[k] = rightArray[j];
			j++;
			k++;
		}
	}
	
	@Override
	public String getAlgorithmName() {
		return "Merge Sort";
	}
}