package edu.ncsu.csc316.dsa.map.search_tree;

import java.util.Comparator;

import edu.ncsu.csc316.dsa.Position;

/**
 * An implementation of a Splay Tree Map
 * @author griffin
 *
 * @param <K> Key of the entry
 * @param <V> Value of the entry
 */
public class SplayTreeMap<K extends Comparable<K>, V> extends BinarySearchTreeMap<K, V> {

	/**
	 * Creates a tree map
	 */
    public SplayTreeMap() {
        super(null);
    }

    /**
     * Creates a tree map with a comparator
     * @param compare Comparator to use
     */
    public SplayTreeMap(Comparator<K> compare) {
        super(compare);
    }

    private void splay(Position<Entry<K, V>> x) {
    	Position<Entry<K, V>> node = x;
    	while (!isRoot(node)) {
    		Position<Entry<K, V>> parent = parent(node);
    		Position<Entry<K, V>> grandparent = parent(parent);
    		if (grandparent == null) {
    			rotate(node);
    		} else if ((node.equals(left(parent)) && parent.equals(left(grandparent))) || (node.equals(right(parent)) && parent.equals(right(grandparent)))) {
    			rotate(parent);
    			rotate(node);
    		} else {
    			rotate(node);
    			rotate(node);
    		}
    	}
    }

    @Override
    protected void actionOnAccess(Position<Entry<K, V>> p) {
        // If p is a dummy/sentinel node, move to the parent
        if(isLeaf(p)) {
            p = parent(p);
        }
        if(p != null) {
            splay(p);
        }
    }

    @Override
    protected void actionOnInsert(Position<Entry<K, V>> node) {
        splay(node);
    }

    @Override
    protected void actionOnDelete(Position<Entry<K, V>> p) {
        if(!isRoot(p)) {
            splay(parent(p));
        }
    }
}