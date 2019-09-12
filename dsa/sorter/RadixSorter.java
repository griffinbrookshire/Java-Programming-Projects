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
public class RadixSorter<E extends Identifiable> implements Sorter<E> {
	
	/**
	 * Creates a RadixSorter object to sort generic objects
	 */
	public RadixSorter() {
		/**
		 * No data
		 */
	}

	@Override
	public void sort(E[] array) {
		int k = 0;
		for (int i = 0; i <= array.length - 1; i++) {
			k = Math.max(k,  array[i].getId());
		}
		int x = (int)(Math.ceil(Math.log(k + 1) / Math.log(10)));
		int p = 1;
		for (int j = 0; j <= x; j++) {
			int[] b = new int[10];
			for (int i = 0; i <= array.length - 1; i++) {
				b[ (array[i].getId() / p) % 10 ] = b[ (array[i].getId() / p) % 10 ] + 1;
			}
			for (int i = 1; i <= 9; i++) {
				b[i] = b[i - 1] + b[i];
			}
			int[] f = new int[array.length];
			for (int i = array.length - 1; i >= 0; i--) {
				f[ b[ (array[i].getId() / p) % 10 ] - 1 ] = array[i].getId();
				b[ (array[i].getId() / p) % 10 ] = b[ (array[i].getId() / p) % 10 ] - 1;
			}
			for (int i = 0; i < array.length; i++) {
				int idx = getObjectById(array, f[i]);
				E found = array[idx];
				E swap = array[i];
				array[i] = found;
				array[idx] = swap;
			}
			p = p * 10;
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
