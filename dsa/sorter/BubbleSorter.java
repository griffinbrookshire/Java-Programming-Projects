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
public class BubbleSorter<E extends Comparable<E>> extends AbstractComparisonSorter<E> {

	/**
	 * Creates a BubbleSorter object to sort objects by comparison
	 * @param comparator The comparator to use
	 */
	public BubbleSorter(Comparator<E> comparator) {
		super(comparator);
	}
	
	/**
	 * Creates an BubbleSorter object to sort generic objects
	 */
	public BubbleSorter() {
		this(null);
	}

	@Override
	public void sort(E[] array) {
		boolean r = true;
		while (r) {
			r = false;
			for (int i = 1; i < array.length - 1; i++) {
				if (array[i].compareTo(array[i - 1]) < 0) {
					E x = array[i - 1];
					array[i - 1] = array[i];
					array[i] = x;
					r = true;
				}
			}
		}
	}

}
