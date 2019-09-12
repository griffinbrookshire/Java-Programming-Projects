package edu.ncsu.csc316.dsa.map;

import java.util.Comparator;
import java.util.Iterator;

import edu.ncsu.csc316.dsa.list.ArrayBasedList;

/**
 * A sorted map implemented with a search table
 * @author griffin
 *
 * @param <K> Key of entry
 * @param <V> Value of entry
 */
public class SearchTableMap<K extends Comparable<K>, V> extends AbstractSortedMap<K, V> {

	/** Holds the entries */
    private ArrayBasedList<Entry<K, V>> list;

    /**
     * Creates a map
     */
    public SearchTableMap() {
        this(null);
    }
    
    /**
     * Creates a map with a comparator
     * @param compare Comparator to use
     */
    public SearchTableMap(Comparator<K> compare) {
        super(compare);
        list = new ArrayBasedList<Entry<K, V>>();
    }

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

    /**
     * Finds where an entry should go based on its key
     * @param min Min index to search from
     * @param max Max index to search to
     * @param key Key of entry
     * @return index where the entry should go
     */
    private int binarySearchHelper(int min, int max, K key) {
		if (min > max) {
			return -1 * (min + 1);
		}
		int mid = (max + min) / 2;
		if (compare(list.get(mid).getKey(), key) == 0) {
			return mid;
		} else if (compare(list.get(mid).getKey(), key) > 0) {
			return binarySearchHelper(min, mid - 1, key);
		} else {
			return binarySearchHelper(mid + 1, max, key);
		}
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public V get(K key) {
    	int index = lookUp(key);
    	if (index == -1) {
    		return null;
    	}
    	MapEntry<K, V> entry = new MapEntry<K, V>(key, list.get(index).getValue());
        return entry.getValue();
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
    public V put(K key, V value) {
    	int index = binarySearchHelper(0, list.size() - 1, key);
        if (index >= 0) {
        	return list.get(index).setValue(value);
        }
        list.add(-1 * (index + 1), new MapEntry<K, V>(key, value));
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