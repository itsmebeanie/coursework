package Project02;

import java.lang.reflect.Array;

/**
 * This class provides tools for use with generic arrays.
 * 
 * @author Kaitlin Gu
 * @creation October 22, 2014
 *
 */
public class ArrayTools {

	private ArrayTools() {

	}

	/**
	 * Wrapper method for quick sort that takes in the list and passes the
	 * arguments into quick sort method
	 * 
	 * @param list
	 */
	public static <E extends Comparable<E>> void QuickSort(E[] list) {
		// Quick sort sorts the list based on quick sort algorithm
		QuickSort(list, 0, list.length - 1);
	}

	private static <E extends Comparable<E>> void QuickSort(E[] list, int left,
			int right) {
		if (left < right)
			// For 10 list elements or less, call insertion sort
			if (right - left <= 10) {
				insertionSort(list, left, right + 1);
			} else {
				// Picks a pivot based on the middle of the current left bound
				// and
				// right
				// bound values of array
				int pivotIndex = (left + right) / 2;
				// Swaps the value at pivot with the value at right pointer
				swap(list, pivotIndex, right);
				// Partition the sub lists and return the value at which the
				// bounds
				// cross
				int leftBoundIndex = partition(list, left, right - 1,
						list[right]);
				// Place the pivot back into its final location
				swap(list, leftBoundIndex, right);
				// Continuing calling quick sort
				QuickSort(list, left, leftBoundIndex - 1);
				QuickSort(list, leftBoundIndex + 1, right);
			}
	}

	private static <E> void swap(E[] list, int i, int j) {
		// Swaps the value at index i with the value at index j
		E temp = list[i];
		list[i] = list[j];
		list[j] = temp;

	}

	private static <E extends Comparable<E>> int partition(E[] list, int left,
			int right, E pivot) {
		// While the left and right bounds have not crossed
		while (left <= right) {
			// Increment the left bound is less than the pivot
			while (list[left].compareTo(pivot) < 0) {
				left++;
			}
			// Decrement the right bound if it is greater than the pivot
			while (right >= left && list[right].compareTo(pivot) >= 0) {
				right--;
			}
			// If left and right bounds don't cross, swap the values
			if (right > left) {
				swap(list, left, right);
			}
		}
		// Return the value at which left bound and right bound cross
		return left;
	}

	/**
	 * Sorts the given array using selection sort algorithm.
	 * 
	 * @param list
	 *            array to sort.
	 */

	public static <E extends Comparable<E>> void selectionSort(E[] list) {
		for (int i = 0; i < list.length - 1; i++) {
			// Find the minimum in the list[i..list.length-1]
			E currentMin = list[i];
			int currentMinIndex = i;

			for (int j = i + 1; j < list.length; j++) {
				if (currentMin.compareTo(list[j]) > 0) {
					currentMin = list[j];
					currentMinIndex = j;
				}
			}

			// Swap list[i] with list[currentMinIndex] if necessary;
			if (currentMinIndex != i) {
				list[currentMinIndex] = list[i];
				list[i] = currentMin;
			}
		}
	}

	/**
	 * Sorts the given array using insertion sort algorithm.
	 * @left
	 * 	left bound of sorted array
	 * @right
	 * right bound of sorted array
	 * @param list
	 *            array to sort.
	 */
	public static <E extends Comparable<E>> void insertionSort(E[] list,
			int left, int right) {
		for (int i = left + 1; i < right; i++) {
			/*
			 * insert list[i] into a sorted sublist list[0..i-1] so that
			 * list[0..i] is sorted.
			 */
			E currentElement = list[i];
			int k;
			for (k = i - 1; k >= 0 && list[k].compareTo(currentElement) > 0; k--) {
				list[k + 1] = list[k];
			}

			// Insert the current element into list[k+1]
			list[k + 1] = currentElement;
		}
	}

	/**
	 * Wrapper method for recursive merge sort method which sorts the list based
	 * on merge sort algorithm
	 * 
	 * @param list
	 */

	public static <E extends Comparable<E>> void mergeSort(E[] list) {
		mergeSort(list, 0, list.length - 1);
	}

	private static <E extends Comparable<E>> void mergeSort(E[] list, int left,
			int right) {
		int mid;
		if (right > left) {
			mid = (left + right) / 2;
			mergeSort(list, left, mid);
			mergeSort(list, mid + 1, right);
			// Merge subarrays using merge method
			merge(list, left, mid, mid + 1, right);
		}

	}

	private static <E extends Comparable<E>> void merge(E[] list,
			int leftFirst, int leftLast, int rightFirst, int rightLast) {

		// Create temp array
		@SuppressWarnings("unchecked")
		E[] mergeList = (E[]) Array.newInstance(list.getClass()
				.getComponentType(), rightLast - leftFirst + 1);
		// Values to keep track of indices
		int rightIndex = rightFirst;
		int leftIndex = leftFirst;
		int index = 0;

		while (leftIndex <= leftLast && rightIndex <= rightLast) {
			// add elements to temp array in sorted order
			if (list[leftIndex].compareTo(list[rightIndex]) < 0) {
				mergeList[index++] = list[leftIndex++];

			} else {
				mergeList[index++] = list[rightIndex++];
			}

		}
		// Add remaining elements to temp array
		while (leftIndex <= leftLast) {
			mergeList[index++] = list[leftIndex++];

		}
		// Add remaining elements to temp array
		while (rightIndex <= rightLast) {
			mergeList[index++] = list[rightIndex++];
		}
		// Copy temp array into actual array
		for (int i = 0; i < mergeList.length; i++) {
			list[leftFirst + i] = mergeList[i];
		}
	}

	/**
	 * Prints content of the array as a comma separated list to standard output.
	 * It uses the implementation of toString() method for the given type.
	 * 
	 * @param list
	 *            an array to be printed
	 */
	public static <E> void printList(E[] list) {
		if (null != list) {
			for (int i = 0; i < list.length; i++)
				System.out.print(list[i] + "\n");
			System.out.print("\n");
		}

	}

	/**
	 * Creates a duplicate of a given array.
	 * 
	 * @param list
	 *            array to duplicate
	 * @return a copy of the given array
	 */
	public static <E> E[] copyArray(E[] list) {
		@SuppressWarnings("unchecked")
		E listCopy[] = (E[]) Array.newInstance(list.getClass()
				.getComponentType(), list.length);
		for (int i = 0; i < list.length; i++)
			listCopy[i] = list[i];
		return listCopy;
	}

}
