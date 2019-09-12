package edu.ncsu.csc316.dsa.map;

import java.util.Iterator;

/**
 * Defines behavior for a Map
 * @author griffin
 *
 * @param <K> Key of entry
 * @param <V> Value of entry
 */
public interface Map<K, V> extends Iterable<K> {
	
	/**
	 * Returns an iterable list
	 * @return an iterable list
	 */
    Iterable<Entry<K, V>> entrySet();
    
    /**
     * Gets the value at the give key
     * @param key The key to find
     * @return value at key
     */
    V get(K key);
    
    /**
     * True if map is empty, false otherwise
     * @return true, false
     */
    boolean isEmpty();
    
    /**
     * Returns an iterator
     * @return an iterator
     */
    Iterator<K> iterator();
    
    /**
     * Puts an entry with given key and value
     * @param key Key to give
     * @param value Value to give
     * @return value of replaced entry
     */
    V put(K key, V value);
    
    /**
     * Removes an element at the key
     * @param key Key to find
     * @return value of the removed entry
     */
    V remove(K key);
    
    /**
     * Gets size of map
     * @return size of map
     */
    int size();
    
    /**
     * Returns an iterable list of entries
     * @return iterable list
     */
    Iterable<V> values();
    
    /**
     * Defines behavior for an entry
     * @author griffin
     *
     * @param <K> Key of entry
     * @param <V> Value of entry
     */
    interface Entry<K, V> {
    	
    	/**
    	 * Gets the key of the entry
    	 * @return key of entry
    	 */
        K getKey();
        
        /**
         * Gets the value of the entry
         * @return value of entry
         */
        V getValue();
        
        /**
         * Sets the key of the entry to given key
         * @param key Key to set to
         * @return old key
         */
        K setKey(K key);
        
        /**
         * Sets the value of the entry to given value
         * @param value Value to set to
         * @return old value
         */
        V setValue(V value);
    }
}