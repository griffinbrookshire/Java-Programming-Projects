package edu.ncsu.csc316.dsa.stack;

/**
 * Defines behavior of the Stack
 * @author griffin
 *
 * @param <E> a generic object
 */
public interface Stack<E> {
	
	/**
	 * Pushes an object to the top of the stack
	 * @param value The element to push
	 */
    void push(E value);
    
    /**
     * Pops an element off the top of the stack
     * @return The element that was popped
     */
    E pop();
    
    /**
     * Returns the element at the top of the list
     * @return The next element
     */
    E top();
    
    /**
     * Tells the size of the stack
     * @return The size of the stack
     */
    int size();
    
    /**
     * Checks if the stack is empty
     * @return True if the stack is empty, false if not
     */
    boolean isEmpty();
}
