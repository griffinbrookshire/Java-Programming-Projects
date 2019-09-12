package edu.ncsu.csc316.dsa.sorter;

/**
 * An interface to define behavior of specific types of Sorters.
 * @author griffin
 * @param <E> generic object
 */
public interface Sorter<E> {
	
	/**
	 * Sorts an array of generic objects
	 * @param array The array of objects to sort
	 */
	void sort(E[] array);

}
