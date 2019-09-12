package edu.ncsu.csc316.dsa.map;

import java.util.Comparator;

/**
 * Defines data and behavior of sorted maps
 * @author griffin
 *
 * @param <K> Key of entry
 * @param <V> Value of entry
 */
public abstract class AbstractSortedMap<K extends Comparable<K>, V> extends AbstractMap<K, V> {

	/** Comparator to use */
    private Comparator<K> compare;

    /**
     * Creates a sorted map
     * @param compare Comaprator to use
     */
    public AbstractSortedMap(Comparator<K> compare) {
        if (compare == null) {
            this.compare = new NaturalOrder();
        } else {
            this.compare = compare;
        }
    }

    /**
     * Compares two keys
     * @param key1 First key to compare
     * @param key2 Second key to compare
     * @return -1 if first is bigger, 1 if second is bigger, 0 if equal
     */
    public int compare(K key1, K key2) {
        return compare.compare(key1, key2);
    }

    /**
     * Defines the order of keys
     * @author griffin
     *
     */
    private class NaturalOrder implements Comparator<K> {
    	
    	/**
    	 * Compares two keys
    	 * @param first First key to compare
    	 * @param second Second key to compare
    	 * @return -1 if first is bigger, 1 if second is bigger, 0 if equal
    	 */
        public int compare(K first, K second) {
            return ((Comparable<K>) first).compareTo(second);
        }
    }
}