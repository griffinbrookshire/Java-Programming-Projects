package edu.ncsu.csc316.dsa.tree;

import java.util.Iterator;

import edu.ncsu.csc316.dsa.Position;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.list.SinglyLinkedList;

/**
 * Defines data and behavior of a tree
 * @author griffin
 *
 * @param <E> a generic object
 */
public abstract class AbstractTree<E> implements Tree<E> {

	@Override
	public boolean isInternal(Position<E> p) {
		return numChildren(p) > 0;
	}

	@Override
	public boolean isLeaf(Position<E> p) {
		return numChildren(p) == 0;
	}

	@Override
	public boolean isRoot(Position<E> p) {
		return p == root();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public Iterable<Position<E>> preOrder()
	{
		// You can use any list data structure here that supports
		// O(1) addLast
		List<Position<E>> traversal = new SinglyLinkedList<Position<E>>();
		if (!isEmpty()) {
			preOrderHelper(root(), traversal);
		}
		return traversal;
	}

	private void preOrderHelper(Position<E> p, List<Position<E>> traversal) {
		if (p == null) {
			return;
		}
		traversal.addLast(p);
		for (Position<E> c : children(p)) {
			preOrderHelper(c, traversal);
		}
	}

	@Override
	public Iterable<Position<E>> postOrder() {
		// You can use any list data structure here that supports
		// O(1) addLast
		List<Position<E>> list = new SinglyLinkedList<Position<E>>();
		if (!isEmpty()) {
			postOrderHelper(root(), list);
		}
		return list;
	}

	private void postOrderHelper(Position<E> p, List<Position<E>> list) {
		if (p == null) {
			return;
		}
		for (Position<E> c : children(p)) {
			preOrderHelper(c, list);
		}
		list.addLast(p);
	}

	@Override
	public Iterable<Position<E>> levelOrder()
	{
		List<Position<E>> list = new SinglyLinkedList<Position<E>>();
		if (!isEmpty()) {
			levelOrderHelper(root(), list);
		}
		return list;
	}

	private void levelOrderHelper(Position<E> p, List<Position<E>> list) {
		if (p == null) {
			return;
		}
		list.addLast(p);
		while (!list.isEmpty()) {
			Position<E> q = list.removeLast();
			if (numChildren(q) != 0) {
				for (Position<E> c : children(q)) {
					list.addLast(c);
				}
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + "[\n");
		toStringHelper(sb, "", root());
		sb.append("]");
		return sb.toString();
	}

	private void toStringHelper(StringBuilder sb, String indent, Position<E> root) {
		if(root == null) {
			return;
		}
		sb.append(indent).append(root.getElement()).append("\n");
		for(Position<E> child : children(root)) {
			toStringHelper(sb, indent + " ", child);
		}
	}

	// Since every tree node is going to be a "Position", every tree
	// node will need the getElement and setElement methods
	/**
	 * A node of a tree
	 * @author griffin
	 *
	 * @param <E> a generic object
	 */
	protected abstract static class AbstractNode<E> implements Position<E> {

		/** Element that the node holds */
		private E element;

		/**
		 * Creates a new node
		 * @param element Element that the node holds
		 */
		public AbstractNode(E element) {
			setElement(element);
		}

		@Override
		public E getElement() {
			return element;
		}

		/**
		 * Sets the value of the element
		 * @param element To set to
		 */
		public void setElement(E element) {
			this.element = element;
		}
	}

	/**
	 * Iterates elements of the tree
	 * @author griffin
	 *
	 */
	protected class ElementIterator implements Iterator<E> {

		/** Iterator to use */
		private Iterator<Position<E>> it;

		/**
		 * Creates an iterator
		 * @param iterator Iterator to use
		 */
		public ElementIterator(Iterator<Position<E>> iterator) {
			it = iterator;
		}

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public E next() {
			return it.next().getElement();
		}
	}  
}