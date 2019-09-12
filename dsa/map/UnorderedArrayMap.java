package edu.ncsu.csc316.dsa.map;

import java.util.Iterator;

import edu.ncsu.csc316.dsa.list.ArrayBasedList;

/**
 * An unordered map implemented using an array
 * @author griffin
 *
 * @param <K> Key of entry
 * @param <V> Value of entry
 */
public class UnorderedArrayMap<K, V> extends AbstractMap<K, V> {

    // Using the adapter pattern to delegate to our existing
    // array-based list implementation
	/** Holds the values of the map */
    private ArrayBasedList<Entry<K, V>> list;
    
    /**
     * Creates a map
     */
    public UnorderedArrayMap() {
        list = new ArrayBasedList<Entry<K, V>>();
    }

    // LookUp is a core behavior of all maps
    // This lookup should perform a sequential search
    // and return the index where the entry
    // is located. If the entry is not in the map, return -1
    /**
     * Looks up the index of the element with the specified key
     * @param key Key to find
     * @return i the index of the element
     */
    private int lookUp(K key)
    {
    	for (int i = 0; i < list.size(); i++) {
    		if (list.get(i).getKey() == key) {
    			return i;
    		}
    	}
		return -1;
    }
    
    @Override
    public V get(K key) {
    	int index = lookUp(key);
    	if (index == -1) {
    		return null;
    	}
    	MapEntry<K, V> entry = new MapEntry<K, V>(key, list.get(index).getValue());
    	transpose(index);
        return entry.getValue();
    }
    
    @Override
    public V put(K key, V value) {
        int index = lookUp(key);
        if (index != -1) {
        	V replaced = list.get(index).setValue(value);
        	transpose(index);
        	return replaced;
        }
        list.addFirst(new MapEntry<K, V>(key, value));
        return null;
    }
    
    @Override
    public V remove(K key) {
       int index = lookUp(key);
       if (index == -1) {
    	   return null;
       }
       return list.remove(index).getValue();
    }

    @Override
    public int size() {
        return list.size();
    }
    
    private V transpose(int index)
    {
    	V value = list.get(index).getValue();
    	if (index == 0) {
    		return value;
    	}
    	Entry<K, V> replaced = list.set(index - 1, list.get(index));
    	list.set(index, replaced);
		return value;
    }
    
    @Override
    public Iterable<Entry<K, V>> entrySet() {
    	ArrayBasedList<Entry<K, V>> set = new ArrayBasedList<Entry<K, V>>();
        for (int i = 0; i < list.size(); i++) {
            set.addLast(list.get(i));
        }
        return set;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + "[");
        Iterator<Entry<K, V>> it = list.iterator();
        int i = 0;
        while(it.hasNext()) {
            sb.append(it.next().getKey());
            if (i == list.size() - 1) {
        		break;
        	}
            if(it.hasNext()) {
                sb.append(", ");
            }
            i++;
        }
        sb.append("]");
        return sb.toString();
    }
}