/**
 * 
 */
package edu.ncsu.csc316.dsa.sorter;

import edu.ncsu.csc316.dsa.data.Identifiable;

/**
 * A non comparison-based sorting algorithm.
 * @author griffin
 * @param <E> generic object
 */
public class CountingSorter<E extends Identifiable> implements Sorter<E> {
	
	/**
	 * Creates a CountingSorter object to sort generic objects.
	 */
	public CountingSorter() {
		/**
		 * No data
		 */
	}

	@Override
	public void sort(E[] array) {
		int min = array[0].getId();
		int max = array[0].getId();
		for (int i = 0; i <= array.length - 1; i++) {
			min = Math.min(array[i].getId(), min);
			max = Math.max(array[i].getId(), max);
		}
		int k = max - min + 1;
		
		int[] b = new int[k];
		for (int i = 0; i <= array.length - 1; i++) {
			b[ array[i].getId() - min ] = b[ array[i].getId() - min ] + 1;
		}
		
		for (int i = 1; i <= k - 1; i++) {
			b[i] = b[i - 1] + b[i];
		}
		
		int[] f = new int[array.length];
		for (int i = 0; i <= array.length - 1; i++) {
			f[ b[array[i].getId() - min] - 1] = array[i].getId();
			b[ array[i].getId() - min] = b[ array[i].getId() - min] - 1;
		}
		
		for (int i = 0; i < array.length; i++) {
			int idx = getObjectById(array, f[i]);
			E found = array[idx];
			E swap = array[i];
			array[i] = found;
			array[idx] = swap;
		}
	}
	
	private int getObjectById(E[] array, int id) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].getId() == id) {
				return i;
			}
		}
		return -1;
	}

}
