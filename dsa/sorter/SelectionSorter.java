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
public class SelectionSorter<E extends Comparable<E>> extends AbstractComparisonSorter<E> {
	
	/**
	 * Creates a SelectionSorter object to sort generic objects.
	 * @param comparator The comparator to use
	 */
    public SelectionSorter(Comparator<E> comparator) {
        super(comparator);
    }
    
    /**
	 * Creates an SelectionSorter object to sort generic objects
	 */
    public SelectionSorter() {
    	this(null);
    } 

    @Override
    public void sort(E[] data) {
    	for (int i = 0; i < data.length - 1; i++) {
    		int min = i;
    		for (int j = i + 1; j < data.length - 1; j++) {
    			if (data[j].compareTo(data[min]) < 0) {
    				min = j;
    			}
    			E x = data[i];
    			data[i] = data[min];
    			data[min] = x;
    		}
    	}
    }

}
