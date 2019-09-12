/**
 * 
 */
package edu.ncsu.csc316.dsa.sorter;

import java.util.Comparator;

/**
 * A comparison-based sorting algorithm.
 * @author griffin
 * @param <E> generic object
 */
public class InsertionSorter<E extends Comparable<E>> extends AbstractComparisonSorter<E> {

	/**
	 * Creates an InsertionSorter object to sort generic objects
	 * @param comparator The comparator to use
	 */
	public InsertionSorter(Comparator<E> comparator) {
		super(comparator);
	}
	
	/**
	 * Creates an InsertionSorter object to sort generic objects
	 */
	public InsertionSorter() {
		this(null);
	}

	@Override
	public void sort(E[] array) {
		for (int i = 1; i < array.length - 1; i++) {
			E x = array[i];
			int j = i - 1;
			while (j >= 0 && array[j].compareTo(x) > 0) {
				array[j + 1] = array[j];
				j = j - 1;
			}
			array[j + 1] = x;
		}
	}

}
