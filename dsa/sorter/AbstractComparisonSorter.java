/**
 * 
 */
package edu.ncsu.csc316.dsa.sorter;

import java.util.Comparator;

/**
 * An abstract sorter class to act as a superclass to comparison-based sorters.
 * @author griffin
 * @param <E> generic object
 */
public abstract class AbstractComparisonSorter<E extends Comparable<E>> implements Sorter<E> {

	/** Holds the current comparator */
    private Comparator<E> comparator;
    
    /**
     * Creates a new Sorter with the given comparator
     * @param comparator Comparator to use
     */
    public AbstractComparisonSorter(Comparator<E> comparator) {
        setComparator(comparator);
    }
    
    /**
     * Sets the comparator
     * @param comparator The comparator to set
     */
    private void setComparator(Comparator<E> comparator) {
        if(comparator == null) {
           comparator = new NaturalOrder();
        }
        this.comparator = comparator;
    }
    
    /**
     * Compares two generic objects
     * @param data1 First object to compare
     * @param data2 Second object to compare
     * @return 1 if the first object should come after the second object,
     *        -1 if the first object should come before the second object,
     *        0 if the objects should be considered equal
     */
    public int compare(E data1, E data2) {
        return comparator.compare(data1,  data2);
    }
    
    /**
     * An inner class that defines the natural order of sorting.
     * @author griffin
     *
     */
    private class NaturalOrder implements Comparator<E> {
    	
    	/**
    	 * Compares two generic objects
    	 */
        public int compare(E first, E second) {
            return ((Comparable<E>) first).compareTo(second);
        }
    }
    
}