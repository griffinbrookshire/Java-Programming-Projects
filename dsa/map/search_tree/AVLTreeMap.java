package edu.ncsu.csc316.dsa.map.search_tree;

import java.util.Comparator;

import edu.ncsu.csc316.dsa.Position;

/**
 * An implementation of an AVL Tree Map
 * @author griffin
 *
 * @param <K> Key of the entry
 * @param <V> Value of the entry
 */
public class AVLTreeMap<K extends Comparable<K>, V> extends BinarySearchTreeMap<K, V> {

	/**
	 * Creates a tree map
	 */
    public AVLTreeMap() {
        super(null);
    }

    /**
     * Creates a tree map with a comparator
     * @param compare Comparator to use
     */
    public AVLTreeMap(Comparator<K> compare) {
        super(compare);
    }

    // Helper method to trace upward from position p checking to make
    // sure each node on the path is balanced. If a rebalance is necessary,
    // trigger a trinode resturcturing
    private void rebalance(Position<Entry<K, V>> x) {
        int oldHeight = 0;
        int newHeight = 0;
        do {
        	oldHeight = getProperty(x);
        	if (!isBalanced(x)) {
        		Position<Entry<K, V>> child = tallerChild(x);
        		Position<Entry<K, V>> grandChild = tallerChild(child);
        		x = restructure(grandChild);
        		recomputeHeight(left(x));
        		recomputeHeight(right(x));
        	}
        	recomputeHeight(x);
        	newHeight = getProperty(x);
        	x = parent(x);
        } while (oldHeight != newHeight && x != null);
    }
    
    // Returns the child of p that has the greater height
    // If both children have the same height, use the child that 
    // matches the parent's orientation
    private Position<Entry<K, V>> tallerChild(Position<Entry<K, V>> x) {
		if (getProperty(left(x)) > getProperty(right(x))) {
			return left(x);
		}
		if (getProperty(left(x)) < getProperty(right(x))) {
			return right(x);
		}
		if (isRoot(x)) {
			return left(x);
		}
		if (x.equals(left(parent(x)))) {
			return left(x);
		}
		return right(x);
    }   

    private boolean isBalanced(Position<Entry<K, V>> p) {
        return Math.abs(getProperty(left(p)) - getProperty(right(p))) <= 1;
    }
    
    private void recomputeHeight(Position<Entry<K, V>> p) {
        int h = 1 + Math.max(getProperty(left(p)), getProperty(right(p)));
        setProperty(p, h);
    }

    @Override
    protected void actionOnInsert(Position<Entry<K, V>> node) {
        rebalance(node);
    }

    @Override
    protected void actionOnDelete(Position<Entry<K, V>> node) {
        if(!isRoot(node))
        {
            rebalance(parent(node));
        }
    }
}