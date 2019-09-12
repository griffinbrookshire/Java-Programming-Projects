/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Collection of elements E in a stack data structure
 * 
 * @author griffin
 * @param <E> object type for stack
 */
public interface Stack<E> {
	
	/**
	 * Adds the element to the top of the stack
	 * @param element E element to be added to stack
	 * @throws IllegalArgumentException if stack is at capacity before method is called
	 */
	void push(E element);
	
	/**
	 * Removes and returns the element at the top of the stack
	 * @return element E element removed
	 */
	E pop();
	
	/**
	 * Returns true if the stack is empty
	 * @return true if empty, false otherwise
	 */
	boolean isEmpty();
	
	/**
	 * Returns the number of elements in the stack
	 * @return elements number of elements
	 */
	int size();
	
	/**
	 * Sets the stack’s capacity
	 * @param capacity max number of elements that can be added
	 * @throws IllegalArgumentException if parameter is negative or less than current size
	 */
	void setCapacity(int capacity);

}
