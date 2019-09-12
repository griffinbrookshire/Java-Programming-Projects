package edu.ncsu.csc316.dsa.map.hashing;

import edu.ncsu.csc316.dsa.list.ArrayBasedList;

/**
 * Cited Noah Eggens (ndeggens) on 2/28/19 from Lab 2
 * All Documents were submitted from both parties
 * 
 * A hash map that uses linear probing
 * @author griffin
 *
 * @param <K> Key of the entry
 * @param <V> Value of the entry
 */
public class LinearProbingHashMap<K, V> extends AbstractHashMap<K, V> {

    // This time, our array is an array of TableEntry objects
    private TableEntry<K, V>[] table;
    private int size;

    /**
     * Constructor for LinearProbingHashMap
     */
    public LinearProbingHashMap() {
        this(AbstractHashMap.DEFAULT_CAPACITY);
    }
    
    /**
     * Constructor that defines how large list is
     * @param capacity is capacity of list
     */
    public LinearProbingHashMap(int capacity) {
        super(capacity);
        size = 0;
    }


    /**
     * Cited from Data Structures and Algorithms
     * Page: 427
     */
    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayBasedList<Entry <K, V>> buffer = new ArrayBasedList<Entry<K, V>>();
        for(int i = 0; i < table.length; i++) {
        	if(!isAvailable(i)) {
        		buffer.addLast(table[i]);
        	}
        }
        return buffer;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void createTable(int capacity) {
        table = (TableEntry<K, V>[]) new TableEntry[capacity];
        size = 0;
    }
    
    // Helper method to determine whether a bucket has an entry or not
    private boolean isAvailable(int index) {
        return (table[index] == null || table[index].isDeleted());
    }

    // Helper method to find the bucket for an entry;
    // If the entry *is* in the map, returns the index of the bucket
    // If the entry is *not* in the map, returns -(a + 1) to indicate 
    //     that the entry should be added at index a
    private int findBucket(int index, K key) {
       int available = -1;
       int ind = index;
       do {
    	   if(isAvailable(ind)) {
    		   
    		   if(available == -1) {
    			   available = ind;
    		   }
    		   
    		   if(table[ind] == null) {
    			   break;
    		   }
    	   }
    	   else if(table[ind].getKey().equals(key)) {
    		  return ind; 
    	   }
    	   
    	   ind = (ind + 1) % table.length;
    	   
       } while(ind != index);
       
       return -(available + 1);
    }    

    @Override
    public V bucketGet(int hash, K key) {
        int index = findBucket(hash, key);
        if(index < 0) {
        	return null;
        }
        else {
        	return table[index].getValue();
        }
    }
    


    /**
     * Cited from Data Structures and Algorithms
     * Page: 427
     */
    @Override
    public V bucketPut(int hash, K key, V value) {
        int index = findBucket(hash, key);
        if(index >= 0) {
        	return table[index].setValue(value);
        }
        
        table[-(index + 1)] = new TableEntry<K, V>(key , value);
        size++;
        return null;
        
    }

    /**
     * Cited from Data Structures and Algorithms
     * Page: 427
     */
    @Override
    public V bucketRemove(int hash, K key) {
        int index = findBucket(hash, key);
        if(index < 0) {
        	return null;
        }
        else {
        	V oldValue = table[index].getValue();
        	table[index].setDeleted(true);
        	table[index].setKey(null);
        	table[index].setValue(null);
        	size--;
        	return oldValue;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected int capacity() {
        return table.length;
    }
    
    /**
     * Private Class that creates a Table of the Entries
     * @author noaheggenschwiler
     *
     * @param <K> key
     * @param <V> value
     */
    private static class TableEntry<K, V> extends MapEntry<K, V> {

        private boolean isDeleted;

        public TableEntry(K key, V value) {
            super(key, value);
            setDeleted(false);
        }

        public boolean isDeleted() {
            return isDeleted;
        }

        public void setDeleted(boolean deleted) {
            isDeleted = deleted;
        }
    }
}