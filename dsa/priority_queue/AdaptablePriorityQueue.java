package edu.ncsu.csc316.dsa.priority_queue;

/**
 * Defines behavior of an AdaptablePriorityQueue
 * @author griffin
 *
 * @param <K> Key of the entry
 * @param <V> Value of the entry
 */
public interface AdaptablePriorityQueue<K, V> extends PriorityQueue<K, V> {

	/**
	 * Removes an entry e from the priority queue
	 * @param entry Entry to remove
	 */
    void remove(Entry<K, V> entry);
    
    /**
     * Replaces the key of existing entry e
     * @param entry Entry to replace
     * @param key Key to replace with
     */
    void replaceKey(Entry<K, V> entry, K key);
    
    /**
     * Replaces the value of the existing entry e
     * @param entry Entry to replace
     * @param value Value to replace with
     */
    void replaceValue(Entry<K, V> entry, V value);
    
}