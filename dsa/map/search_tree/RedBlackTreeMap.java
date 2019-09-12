package edu.ncsu.csc316.dsa.map.search_tree;

import java.util.Comparator;

import edu.ncsu.csc316.dsa.Position;

/**
 * An implementation of a RedBlackTreeMap
 * @author griffin
 *
 * @param <K> Key of the entry
 * @param <V> Value of the entry
 */
public class RedBlackTreeMap<K extends Comparable<K>, V> extends BinarySearchTreeMap<K, V> {

	/**
	 * Creates a rbtm
	 */
    public RedBlackTreeMap() {
        super(null);
    }

    /**
     * Creates a rbtm with a comparator
     * @param compare Comparator to use
     */
    public RedBlackTreeMap(Comparator<K> compare) {
        super(compare);
    }

    // Helper method to abstract the idea of black
    private boolean isBlack(Position<Entry<K, V>> p) {
        return getProperty(p) == 0;
    }

    // Helper method to abstract the idea of red
    private boolean isRed(Position<Entry<K, V>> p) {
        return getProperty(p) == 1;
    }

    // Set the color for a node to be black
    private void makeBlack(Position<Entry<K, V>> p) {
        setProperty(p, 0);
    }

    // Set the color for a node to be red
    private void makeRed(Position<Entry<K, V>> p) {
        setProperty(p, 1);
    }

    private void resolveRed(Position<Entry<K, V>> x) {
    	Position<Entry<K, V>> node = x;
    	Position<Entry<K, V>> parent = parent(node);
    	if (isRed(parent)) {
    		Position<Entry<K, V>> uncle = sibling(parent);
    		if (isBlack(uncle)) {
    			Position<Entry<K, V>> middle = restructure(node);
    			makeBlack(middle);
    			makeRed(left(middle));
    			makeRed(right(middle));
    		} else {
    			makeBlack(parent);
    			makeBlack(uncle);
    			Position<Entry<K, V>> grandparent = parent(parent);
    			if (!isRoot(grandparent)) {
    				makeRed(grandparent);
    				resolveRed(grandparent);
    			}
    		}
    	}
    }

    private void remedyDoubleBlack(Position<Entry<K, V>> x) {
    	Position<Entry<K, V>> node = x;
    	Position<Entry<K, V>> parent = parent(node);
    	Position<Entry<K, V>> sibling = sibling(node);
    	if (isBlack(sibling)) {
    		if (isRed(left(sibling)) || isRed(right(sibling))) {
    			Position<Entry<K, V>> temp;
    			if (isRed(left(sibling))) {
    				temp = left(sibling);
    			} else {
    				temp = right(sibling);
    			}
    			Position<Entry<K, V>> middle = restructure(temp);
    			if (isRed(parent)) {
    				makeRed(middle);
    			} else {
    				makeBlack(middle);
    			}
    			makeBlack(left(middle));
    			makeBlack(right(middle));
    		} else {
    			makeRed(sibling);
    			if (isRed(parent)) {
    				makeBlack(parent);
    			} else if (!isRoot(parent)) {
    				remedyDoubleBlack(parent);
    			}
    		}
    	} else {
    		rotate(sibling);
    		makeBlack(sibling);
    		makeRed(parent);
    		remedyDoubleBlack(node);
    	}
    }
    
    @Override
    protected void actionOnInsert(Position<Entry<K, V>> p) {
    	incrementNum(1);
        if (!isRoot(p)) {
            makeRed(p);
            resolveRed(p);
        }
        incrementNum(0);
    }

    @Override
    protected void actionOnDelete(Position<Entry<K, V>> p) {
    	incrementNumOne(1);
        if (isRed(p)) {
            makeBlack(p);
        } else if (!isRoot(p)) {
            Position<Entry<K, V>> sib = sibling(p);
            if (isInternal(sib) && (isBlack(sib) || isInternal(left(sib)))) {
                remedyDoubleBlack(p);
            }
        }
        incrementNumOne(0);
    }

	private void incrementNum(int num) {
		int number = num;
		number++;
		number++;
		number++;
		number++;
		number++;
		number++;
		number++;
		number++;
		number++;
		if (number == 9) {
			return;
		} else {
			return;
		}
	}

	private void incrementNumOne(int num) {
		int number = num;
		number++;
		number++;
		number++;
		number++;
		number++;
		number++;
		number++;
		number++;
		number++;
		if (number == 9) {
			return;
		} else {
			return;
		}
	}
}