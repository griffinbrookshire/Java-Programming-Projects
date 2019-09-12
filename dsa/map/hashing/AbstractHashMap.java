package edu.ncsu.csc316.dsa.map.hashing;

import java.util.Random;

import edu.ncsu.csc316.dsa.list.ArrayBasedList;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.AbstractMap;

/**
 * An abstract class that defines data and behavior for hash maps
 * @author griffin
 *
 * @param <K> Key of the entry
 * @param <V> Value of the entry
 */
public abstract class AbstractHashMap<K, V> extends AbstractMap<K, V> {

    // An initial capacity for the hash table
	/** Default capacity */
    protected static final int DEFAULT_CAPACITY = 17;
    
    // From our discussion in class, the expected number of probes
    // for separate chaining remains relatively the same no matter
    // what the load factor may be. However, for linear probing, to
    // reduce the chance of having large clusters, we will resize
    // when the load factor reaches 0.5
    /** Max load factor */
    private static final double MAX_LOAD_FACTOR = 0.5;
    
    // Define a default prime number for our compression strategy
    /** Default prime */
    protected static final int DEFAULT_PRIME = 109345121;
    
    // Alpha and Beta values for MAD compression
    // This implementation uses a variation of the MAD method
    // where h(k) = ( (alpha * f(k) + beta) % DEFAULT_PRIME) % capacity
    /** Alpha */
    private long alpha;
    /** Beta */
    private long beta;
    
    /**
     * Creates a hash map
     * @param capacity Capacity of the map
     */
    public AbstractHashMap(int capacity) {
        Random rand = new Random();
        // Generate a random alpha value (cannot be 0)
        alpha = rand.nextInt(DEFAULT_PRIME - 1) + 1;
        // Generate a random beta value
        beta = rand.nextInt(DEFAULT_PRIME);
        createTable(capacity);
    }
    
    /**
     * Creates a map with default capacity
     */
//    public AbstractHashMap() {
//        this(DEFAULT_CAPACITY);
//    }
    
    private int compress(K key) {
        return (int)((Math.abs(key.hashCode() * alpha + beta) % DEFAULT_PRIME) % capacity());
    }

    @Override
    public V put(K key, V value) {
        V ret = bucketPut(compress(key), key, value);
        if( (double)size() / capacity() > MAX_LOAD_FACTOR){
            resize(2 * capacity() + 1);
        }
        return ret;
    }
    
    @Override
    public V get(K key) {
        return bucketGet(compress(key), key);
    }

    @Override
    public V remove(K key) {
        return bucketRemove(compress(key), key);
    }
    
    private void resize(int newCapacity) {
        List<Entry<K, V>> list = new ArrayBasedList<Entry<K, V>>();
        for(Entry<K, V> entry : entrySet()) {
            list.addLast(entry);
        }
        createTable(newCapacity);
        for(Entry<K, V> entry : list) {
            put(entry.getKey(), entry.getValue());
        }
    }
    
    /**
     * Gets the capacity of the map
     * @return Capacity of the map
     */
    protected abstract int capacity();
    
    /**
     * Creates a table with the given capacity
     * @param capacity Capacity of table to create
     */
    protected abstract void createTable(int capacity);
    
    /**
     * Retrieve an entry from a bucket in the hash table
     * @param hash Hash of entry
     * @param key Key of entry to get
     * @return Value of entry with key
     */
    protected abstract V bucketGet(int hash, K key);
    
    /**
     * Add (or update) an entry in a bucket in the hash table
     * @param hash Hash of entry
     * @param key Key of entry to put
     * @param value Value of entry to put
     * @return Value of replaced entry
     */
    protected abstract V bucketPut(int hash, K key, V value);
    
    /**
     * Remove an entry from a bucket in the hash table
     * @param hash Hash of entry
     * @param key Key of entry to remove
     * @return Value of removed entry
     */
    protected abstract V bucketRemove(int hash, K key);
}