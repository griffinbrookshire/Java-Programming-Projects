package edu.ncsu.csc316.dsa.map;

import java.util.Iterator;

import edu.ncsu.csc316.dsa.Position;
import edu.ncsu.csc316.dsa.list.positional.PositionalLinkedList;
import edu.ncsu.csc316.dsa.list.positional.PositionalList;

/**
 * An unordered map implemented using a linked list
 * @author griffin
 *
 * @param <K> Key of entry
 * @param <V> Value of entry
 */
public class UnorderedLinkedMap<K, V> extends AbstractMap<K, V> {

	/** Holds the list */
    private PositionalList<Entry<K, V>> list;
    
    /**
     * Creates a linked map
     */
    public UnorderedLinkedMap() {
        this.list = new PositionalLinkedList<Entry<K, V>>();
    }
    
    private Position<Entry<K, V>> lookUp(K key)
    {
    	Iterator <Position<Entry<K, V>>> iter = list.positions().iterator(); //starts at first node
    	if (this.size() == 0) {
    		return null;
    	}
    	Position<Entry<K, V>> temp = list.first(); //start at front
    	for (int i = 0; i <= list.size(); i++) {
    		if (temp.getElement().getKey() == key) {
    			return temp;
    		} else if (i != list.size()){
    			temp = iter.next(); // if not, iterate
    		}
    	}
    	return null;
    }

    @Override
    public V get(K key) {
        Position<Entry<K, V>> p = lookUp(key);
        if (p == null) {
    		return null;
    	}
        moveToFront(p);
        return p.getElement().getValue();
    }
    
    private void moveToFront(Position<Entry<K, V>> position) {
    	Entry<K, V> removed = list.remove(position);
    	list.addFirst(removed);
    }

    @Override
    public V put(K key, V value) {
        Position<Entry<K, V>> p = lookUp(key);
        if (p == null) {
        	list.addFirst(new MapEntry<K, V>(key, value));
    		return null;
    	}
        p.getElement().setValue(value);
        moveToFront(p);
		return p.getElement().getValue();
    }
    
    @Override
    public V remove(K key) {
       Position<Entry<K, V>> p = lookUp(key);
       if (p == null) {
    	   return null;
       }
       return list.remove(p).getValue();
    }
    
    @Override
    public int size() {
        return list.size();
    }
    
    @Override
    public Iterable<Entry<K, V>> entrySet() {
        PositionalList<Entry<K, V>> set = new PositionalLinkedList<Entry<K, V>>();
        for(Entry<K, V> m : list) {
            set.addLast(m);
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