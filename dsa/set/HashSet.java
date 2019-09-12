package edu.ncsu.csc316.dsa.set;

import java.util.Iterator;

import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.hashing.LinearProbingHashMap;

//Since our hash map uses linear probing, the entries are not ordered.
//As a result, we do not restrict our hash set to use Comparable elements.
//This also gives you an option if you need a set to manage elements
//  that are *NOT* Comparable (versus a TreeSet)
/**
 * A hash table implementation of a set
 * @author griffin
 *
 * @param <E> Element in the set
 */
public class HashSet<E> extends AbstractSet<E> {

	/** Map that holds the elements */
	private Map<E, E> map;

	/**
	 * Creates a new HashSet
	 */
	public HashSet() {
		map = new LinearProbingHashMap<E, E>();
	}

	@Override
	public Iterator<E> iterator() {
		return map.iterator();
	}

	@Override
	public void add(E value) {
		map.put(value, value);
	}

	@Override
	public boolean contains(E value) {
		if (map.get(value) != null) {
			return true;
		}
		return false;
	}

	@Override
	public E remove(E value) {
		return map.remove(value);
	}

	/**
	 * Size of the set
	 * @return Number of elements in the hash map
	 */
	public int size() {
		return map.size();
	}
}