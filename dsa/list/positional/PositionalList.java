package edu.ncsu.csc316.dsa.list.positional;

import edu.ncsu.csc316.dsa.Position;

/**
 * A positional list class
 * @author griffin
 *
 * @param <E> a generic object
 */
public interface PositionalList<E> extends Iterable<E>, Position<E> {
	
	/**
	 * Adds a new element immediately after position p, and returns the position of the new element
	 * @param p The node to add after
	 * @param value The value of the node to add
	 * @return The position of the new element
	 */
    Position<E> addAfter(Position<E> p, E value);
    
    /**
     * Adds a new element immediately before position p, and returns the position of the new element
     * @param p The node to add before
     * @param value The value of the node to add
     * @return The position of the new element
     */
    Position<E> addBefore(Position<E> p, E value);
    
    /**
     * Adds a new element at the front of the list, and returns the position of the new element
     * @param value The value to add at the first element
     * @return The position of the new element
     */
    Position<E> addFirst(E value);
    
    /**
     * Adds a new element at the end of the list, and returns the position of the new element
     * @param value the value to add at the last element
     * @return The position of the new element
     */
    Position<E> addLast(E value);
    
    /**
     * Returns the position immediately after the given position in the list
     * @param p The position to check after
     * @return The position after the given one
     */
    Position<E> after(Position<E> p);
    
    /**
     * Returns the position immediately before the given position in the list
     * @param p The position to check before
     * @return The position before the given one
     */
    Position<E> before(Position<E> p);
    
    /**
     * Returns, but does not remove, the position of the first element in the list
     * @return The position of the first element in the list
     */
    Position<E> first();
    
    /**
     * Returns true if the list is empty; otherwise, returns false
     * @return True if empty, otherwise false
     */
    boolean isEmpty();
    
    /**
     * Returns, but does not remove, the position of the last element in the list
     * @return The position of the last element
     */
    Position<E> last();
    
    /**
     * Positions method
     * @return Iterable position
     */
    Iterable<Position<E>> positions();
    
    /**
     * Removes the element at the position p
     * @param p The position to remove at
     * @return The element removed
     */
    E remove(Position<E> p);
    
    /**
     * Sets the value at the specified position
     * @param p The position to set at
     * @param value The value to set it to
     * @return The value that was replaced
     */
    E set(Position<E> p, E value);
    
    /**
     * The size of the list
     * @return the size of the list
     */
    int size();

}
