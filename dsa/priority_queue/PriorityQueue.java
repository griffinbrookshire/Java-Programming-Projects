package edu.ncsu.csc316.dsa.priority_queue;

/**
 * Defines behavior for a PriorityQueue
 * @author griffin
 *
 * @param <K> Key of the entry
 * @param <V> Value of the entry
 */
public interface PriorityQueue<K, V> {

	/**
	 * Defines behavior for an entry
	 * @author griffin
	 *
	 * @param <K> Key of the entry
	 * @param <V> Value of the entry
	 */
    interface Entry<K, V> {
    	
    	/**
    	 * Gets the key of the entry
    	 * @return Key of the entry
    	 */
        K getKey();
        
        /**
         * Gets the value of the entry
         * @return Value of the entry
         */
        V getValue();
    }
    
    /**
     * Adds the entry with key K and value V to the priority queue
     * @param key Key of the entry
     * @param value Value of the entry
     * @return Entry that was replaced, or null
     */
    Entry<K, V> insert(K key, V value);
    
    /**
     * Returns, but does not remove, the entry with the smallest key
     * @return Entry with smallest key
     */
    Entry<K, V> min();
    
    /**
     * Removes and returns the entry with the smallest key
     * @return Entry with smallest key
     */
    Entry<K, V> deleteMin();
    
    /**
     * Returns the number of entries in the priority queue
     * @return number of entries
     */
    int size();
    
    /**
     * Returns true if the priority queue is empty; otherwise, returns false
     * @return True if empty, else false
     */
    boolean isEmpty();
    
}