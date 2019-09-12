package edu.ncsu.csc316.dsa.set;

/**
 * Defines behavior for a Set
 * @author griffin
 *
 * @param <E> Element in the set
 */
public interface Set<E> extends Iterable<E> {
	
	/**
	 * Adds the element e to the set (if e is not already present in the set)
	 * @param value Value to add
	 */
    void add(E value);
    
    /**
     * Returns true if the set contains the element e; otherwise, returns false
     * @param value Value to test for
     * @return True if contains the value, false otherwise
     */
    boolean contains(E value);
    
    /**
     * Removes and returns the element e from the set (if e is present in the set)
     * @param value Value to remove
     * @return Removed value
     */
    E remove(E value);
    
    /**
     * Updates the current set to also include all elements contained in the set T (also called “union”)
     * @param other Set to add
     */
    void addAll(Set<E> other);
    
    /**
     * Updates the current set to keep only those elements that are also elements in T (also called “intersection”)
     * @param other Set to retain
     */
    void retainAll(Set<E> other);
    
    /**
     * Updates the current set to remove any of the elements that are contained in T (also called “subtraction”)
     * @param other Set to remove
     */
    void removeAll(Set<E> other);
    
    /**
     * Size of the set
     * @return Number of elements in the set
     */
    int size();
    
    /**
     * Tells if the set is empty
     * @return True if empty, false otherwise
     */
    boolean isEmpty();
}