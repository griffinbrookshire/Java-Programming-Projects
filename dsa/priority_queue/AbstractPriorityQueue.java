package edu.ncsu.csc316.dsa.priority_queue;

import java.util.Comparator;

/**
 * Implementation of an AbstractPriorityQueue
 * @author griffin
 *
 * @param <K> Key of the entry
 * @param <V> Value of the entry
 */
public abstract class AbstractPriorityQueue<K extends Comparable<K>, V> implements PriorityQueue<K, V> {

	/** Holds the comparator used */
    private Comparator<K> comparator;

    /**
     * Creates a new APQ
     * @param c Comparator to use
     */
    public AbstractPriorityQueue(Comparator<K> c) {
        setComparator(c);
    }
    
    private void setComparator(Comparator<K> c) {
        if(c == null) {
            c = new NaturalOrder();
        }
        comparator = c;
    }

    /**
     * Sets a natural method of sorting keys
     * @author griffin
     *
     */
    public class NaturalOrder implements Comparator<K> {
    	
    	/**
    	 * Compares two keys
    	 * @param first First key to compare
    	 * @param second Second key to compare
    	 * @return 1 if bigger, 0 if equal, -1 if smaller
    	 */
        public int compare(K first, K second) {
            return ((Comparable<K>) first).compareTo(second);
        }
    }

    /**
     * Calls the comparator compare method
     * @param data1 First key to compare
     * @param data2 Second key to compare
     * @return 1 if bigger, 0 if equal, -1 if smaller
     */
    public int compare(K data1, K data2) {
        return comparator.compare(data1, data2);
    }
    
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    // Make sure you import PriorityQueue.Entry and NOT Map.Entry!
    /**
     * A Priority Queue Entry
     * @author griffin
     *
     * @param <K> Key of the entry
     * @param <V> Value of the entry
     */
    public static class PQEntry<K, V> implements Entry<K, V> {

    	/** Key of the entry */
        private K key;
        
        /** Value of the entry */
        private V value;

        /**
         * Creates a new PQE
         * @param key Key of entry
         * @param value Value of entry
         */
        public PQEntry(K key, V value) {
            setKey(key);
            setValue(value);
        }

        /**
         * Sets the key to the given key
         * @param key Key to set to
         */
        public void setKey(K key) {
            this.key = key;
        }

        /**
         * Sets the value to the given value
         * @param value Value to set to
         */
        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }
    }
    
    // factory method for constructing a new priority queue entry object
    /**
     * Creates a new Entry
     * @param key Key of new entry
     * @param value Value of new Entry
     * @return New entry
     */
    protected Entry<K, V> createEntry(K key, V value) {
        return new PQEntry<K, V>(key, value);
    }
}