package edu.ncsu.csc316.dsa.disjoint_set;

import edu.ncsu.csc316.dsa.Position;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.hashing.LinearProbingHashMap;

/**
 * A disjoint set forest implemenation
 * @author griffin
 *
 * @param <E> Element in the map
 */
public class UpTreeDisjointSetForest<E> implements DisjointSet<E> {

	// We need a secondary map to quickly locate an entry within the forest of up-trees
	// NOTE: The textbook implementation does not include this
	//       functionality; instead, the textbook implementation leaves
	//       the responsibility of tracking positions to the client in a
	//       separate map structure
	/** Map to hold elements */
	private Map<E, UpTreeNode<E>> map;

	/**
	 * Creates a new Forest
	 */
	public UpTreeDisjointSetForest() {
		// Use an efficient map!
		map = new LinearProbingHashMap<E, UpTreeNode<E>>();
	}
	
	private UpTreeNode<E> validate(Position<E> p) {
        if(!(p instanceof UpTreeNode)) {
            throw new IllegalArgumentException("Position is not a valid up tree node.");
        }
        return (UpTreeNode<E>)p;
    }
	
	@Override
    public Position<E> makeSet(E value) {
		UpTreeNode<E> newNode = new UpTreeNode<E>(value);
		newNode.setParent(null);
		newNode.setCount(1);
		map.put(value, newNode);
		return newNode;
    }

    @Override
    public Position<E> find(E value) {
    	UpTreeNode<E> temp = map.get(value);
		while (temp.getParent() != null) {
			temp = temp.getParent();
		}
		return temp;
    }
    
//    /**
//     * Got code from Jenning's slides
//     */
//    private UpTreeNode<E> findHelper(UpTreeNode<E> current) {
//		if (current != current.getParent()) {
//			current.setParent(findHelper(current.getParent()));
//		}
//		return current.getParent();
//    }

    /**
     * Got code from Jenning's slides
     */
    @Override
    public void union(Position<E> s, Position<E> t) {
        // Instead of trusting the client to give us the roots
        // of two up-trees, we will verify by finding the root 
        // of the up-tree that contains the element in positions s and t
        UpTreeNode<E> a = validate(find(s.getElement()));
        UpTreeNode<E> b = validate(find(t.getElement()));
        if (a.getCount() >= b.getCount()) {
        	a.setCount(a.getCount() + b.getCount());
        	b.setParent(a);
        } else {
        	b.setCount(a.getCount() + b.getCount());
        	a.setParent(b);
        }
    }

    /**
     * A node in the tree 
     * @author griffin
     *
     * @param <E> Element in the node
     */
	private static class UpTreeNode<E> implements Position<E> {

		/** Element in the node */
		private E element;
		
		/** Parent of the node */
		private UpTreeNode<E> parent;
		
		/** Count */
		private int count;

		/**
		 * Creates a new node
		 * @param element Element in the node
		 */
		public UpTreeNode(E element) {
			setElement(element);
			setParent(this);
			setCount(1);
		}

		/**
		 * Sets the element in the node
		 * @param element Element to replace with
		 */
		public void setElement(E element) {
			this.element = element;
		}

		@Override
		public E getElement() {
			return element;
		}

		/**
		 * Sets the parent of the node
		 * @param parent Parent to set to
		 */
		public void setParent(UpTreeNode<E> parent) {
			this.parent = parent;
		}

		/**
		 * Gets the parent of the node
		 * @return Parent of the node
		 */
		public UpTreeNode<E> getParent() {
			return parent;
		}

		/**
		 * Sets the count of the node
		 * @param count Count of the node
		 */
		public void setCount(int count) {
			this.count = count;
		}

		/**
		 * Gets the count of the node
		 * @return Count of the node
		 */
		public int getCount() {
			return count;
		}
	}

}