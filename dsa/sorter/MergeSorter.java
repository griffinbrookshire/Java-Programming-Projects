/**
 * 
 */
package edu.ncsu.csc316.dsa.sorter;

import java.util.Arrays;
import java.util.Comparator;

/**
 * A comparison-based sorting algorithm
 * @author griffin
 * @param <E> generic object
 */
public class MergeSorter<E extends Comparable<E>> extends AbstractComparisonSorter<E> {

	/**
	 * Creates a MergeSorter object to sort objects using merges
	 * @param comparator The comparator to use
	 */
	public MergeSorter(Comparator<E> comparator) {
		super(comparator);
	}
	
	/**
	 * Creates a MergeSorter object to sort objects using merges
	 */
	public MergeSorter() {
		this(null);
	}

	@Override
	public void sort(E[] array) {
		if (array.length < 2) {
			return;
		}
		int mid = array.length / 2;
		E[] left = Arrays.copyOfRange(array, 0, mid);
		E[] right = Arrays.copyOfRange(array, mid, array.length);
		sort(left);
		sort(right);
		merge(left, right, array);
	}
	
	private void merge(E[] left, E[] right, E[] original) {
		int leftIndex = 0;
		int rightIndex = 0;
		while (leftIndex + rightIndex < original.length) {
			if (rightIndex == right.length || (leftIndex < left.length && (compare(left[leftIndex], right[rightIndex]) < 0))) {
				original[leftIndex + rightIndex] = left[leftIndex];
				leftIndex++;
			} else {
				original[leftIndex + rightIndex] = right[rightIndex];
				rightIndex++;
			}
		}
	}

}
