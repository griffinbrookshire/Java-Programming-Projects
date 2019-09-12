package edu.ncsu.csc316.dsa.disjoint_set;

import edu.ncsu.csc316.dsa.Position;

/**
 * Defines behavior of a DisjointSet
 * @author griffin
 *
 * @param <E> Element in the set
 */
public interface DisjointSet<E> {
	
	/**
	 * Creates a disjoint set that contains the element e, then returns the position of the set
	 * @param value Value to make the set 
	 * @return Position of the set
	 */
    Position<E> makeSet(E value);
    
    /**
     * Returns the position of the identifier for the disjoint set that contains the element
     * @param value Value to find
     * @return Position of the value
     */
    Position<E> find(E value);
    
    /**
     * Merges the disjoint sets that contain positions s and t
     * @param s Set to merge
     * @param t Set to merge
     */
    void union(Position<E> s, Position<E> t);
}