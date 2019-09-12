package edu.ncsu.csc316.dsa.set;

import java.util.Iterator;

import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.search_tree.RedBlackTreeMap;

//Remember that search trees are ordered, so our elements must be Comparable
/**
 * A tree implementation of a set
 * @author griffin
 *
 * @param <E> Element in the set
 */
public class TreeSet<E extends Comparable<E>> extends AbstractSet<E> {

	/** Tree containing the set */
	private Map<E, E> tree;

	/**
	 * Creates a new TreeSet
	 */
	public TreeSet() {
		tree = new RedBlackTreeMap<E, E>();
	}

	@Override
	public Iterator<E> iterator() {
		return tree.iterator();
	}

	@Override
	public void add(E value) {
		tree.put(value, value);
	}

	@Override
	public boolean contains(E value) {
		if (tree.get(value) != null) {
			return true;
		}
		return false;
	}

	@Override
	public E remove(E value) {
		return tree.remove(value);
	}

	@Override
	public int size() {
		return tree.size();
	}
}
