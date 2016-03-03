package Project02;

/**
 * 
 * This tests the performance of various sorting algorithms and prints the
 * result of their performances into a table.
 * 
 * The algorithms that are tested are merge sort, quick sort and selection sort
 * 
 * @author Kaitlin Gu
 * @creation October 22, 2014
 */
public class SortPerformance {

	public static void main(String[] args) {

		// Loop through first list sizes to prevent the first call to sort
		// evaluating at a higher time
		for (int i = 10000; i <= 30000; i = i + 10000) {
			Integer[] list = generateRandomArray(i);

			Integer[] mergeList = ArrayTools.copyArray(list);
			long startMergeSort = System.currentTimeMillis();
			ArrayTools.mergeSort(mergeList);
			long endMergeSort = System.currentTimeMillis();
			long mergeResult = endMergeSort - startMergeSort;

			Integer[] quickList = ArrayTools.copyArray(list);
			long startQuickSort = System.currentTimeMillis();
			ArrayTools.QuickSort(quickList);
			long endQuickSort = System.currentTimeMillis();
			long quickResult = endQuickSort - startQuickSort;

		}
		System.out.printf("%50s%n", "SORTING TIMES IN MILLISECONDS");
		// Testing Merge Sort, Quick Sort and Selection Sort
		System.out.printf("%-10s%-20s%-20s%-20s%n", "N", "Merge Sort",
				"Quick Sort", "Selection Sort");

		// Loop through different sized list
		for (int i = 10000; i <= 100000; i = i + 10000) {
			// Generate random array of particular list size
			Integer[] list = generateRandomArray(i);
			// Copy array for each sort
			Integer[] mergeList = ArrayTools.copyArray(list);
			long startMergeSort = System.currentTimeMillis();
			ArrayTools.mergeSort(mergeList);
			long endMergeSort = System.currentTimeMillis();
			long mergeResult = endMergeSort - startMergeSort;

			Integer[] quickList = ArrayTools.copyArray(list);
			long startQuickSort = System.currentTimeMillis();
			ArrayTools.QuickSort(quickList);
			long endQuickSort = System.currentTimeMillis();
			long quickResult = endQuickSort - startQuickSort;

			Integer[] selectionList = ArrayTools.copyArray(list);
			long startSelectionSort = System.currentTimeMillis();
			ArrayTools.selectionSort(selectionList);
			long endSelectionSort = System.currentTimeMillis();
			long selectionResult = endSelectionSort - startSelectionSort;
			// Print results
			System.out.printf("%-10d%-20d%-20d%-20d%n", i, mergeResult,
					quickResult, selectionResult);
		}

		System.out.println();
		// Testing Merge Sort and Quick Sort

		System.out.printf("%-10s%-20s%-10s%n", "N", "Merge Sort", "Quick Sort");

		// Loop through evaluating different list sizes
		for (int i = 50000; i <= 1000000; i = i + 50000) {
			// Generate random array
			Integer[] list = generateRandomArray(i);
			// Copy array for each sort method
			Integer[] quickList = ArrayTools.copyArray(list);
			long startQuickSort = System.currentTimeMillis();
			ArrayTools.QuickSort(quickList);
			long endQuickSort = System.currentTimeMillis();
			long quickResult = endQuickSort - startQuickSort;

			Integer[] mergeList = ArrayTools.copyArray(list);
			long startMergeSort = System.currentTimeMillis();
			ArrayTools.mergeSort(mergeList);
			long endMergeSort = System.currentTimeMillis();
			long mergeResult = endMergeSort - startMergeSort;
			// Print results
			System.out.printf("%-10d%-20d%-20d%n", i, mergeResult, quickResult);
		}

	}

	/**
	 * Generates a random array given a size populates the array with random
	 * values between 0 and Integer.MAX_VALUE
	 * 
	 * @param size
	 * @return The array of that size
	 */
	public static Integer[] generateRandomArray(int size) {
		Integer[] theArray = new Integer[size];
		for (int i = 0; i < size; i++) {
			theArray[i] = (int) (Math.random() * Integer.MAX_VALUE);
		}
		return theArray;
	}

}
