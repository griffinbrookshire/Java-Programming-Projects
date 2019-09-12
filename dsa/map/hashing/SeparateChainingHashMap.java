package edu.ncsu.csc316.dsa.map.hashing;

import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.list.SinglyLinkedList;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.UnorderedArrayMap;

/**
 * A hash map that uses separate chaining
 * @author griffin
 *
 * @param <K> Key of the entry
 * @param <V> Value of the entry
 */
public class SeparateChainingHashMap<K extends Comparable<K>, V> extends AbstractHashMap<K, V> {

	/** Table to use */
    private Map<K, V>[] table;
    
    /** Size of the table */
    private int size;
    
    /**
     * Creates a chaining map
     */
    public SeparateChainingHashMap() {
        this(AbstractHashMap.DEFAULT_CAPACITY);
    }
    
    /**
     * Creates a chaining map with the specified capacity
     * @param capacity Capacity of the map
     */
    public SeparateChainingHashMap(int capacity) {
        super(capacity);
        size = 0;
    }

    @Override
    public Iterable<Entry<K, V>> entrySet() {
        List<Entry<K, V>> list = new SinglyLinkedList<Entry<K, V>>();
        for(int i = 0; i < capacity(); i++) {
            if(table[i] != null) {
                // Each bucket contains a map, so include
                // all entries in the entrySet for the map
                // at the current bucket
                for(Entry<K, V> entry : table[i].entrySet()) {
                    list.addLast(entry);
                }
            }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void createTable(int capacity) {
        // Example -- change this to whatever map you'd like
        table = (UnorderedArrayMap<K, V>[])new UnorderedArrayMap[capacity];
        size = 0;
    }

    @Override
    public V bucketGet(int hash, K key) {
        // Get the bucket at the specified index in the hash table
        Map<K, V> bucket = table[hash];
        // If there is no map in the bucket, then the entry does not exist
        if(bucket == null) {
            return null;
        }
        // Otherwise, delegate to the existing map's get method to return the value
        return bucket.get(key);
    }

    /**
     * Got this code from textbook page 425
     */
    @Override
    public V bucketPut(int hash, K key, V value) {
        Map<K, V> bucket = table[hash];
        if (bucket == null) {
        	table[hash] = new UnorderedArrayMap<K, V>();
        	bucket = table[hash];
        }
        int oldSize = bucket.size();
        V toReturn = bucket.put(key, value);
        size += (bucket.size() - oldSize);
		return toReturn;
    }

    /**
     * Got this code from textbook page 425
     */
    @Override
    public V bucketRemove(int hash, K key) {
        Map<K, V> bucket = table[hash];
        if (bucket == null) {
        	return null;
        }
        int oldSize = bucket.size();
        V toReturn = bucket.remove(key);
        size -= (oldSize - bucket.size());
		return toReturn;
    }
    
    @Override
    public int size() {
        return size;
    }

    @Override
    protected int capacity() {
        return table.length;
    }
}