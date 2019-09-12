package edu.ncsu.csc316.dsa.queue;

/**
 * Defines behavior for a queue
 * @author griffin
 *
 * @param <E> a generic object
 */
public interface Queue<E> {
	
	/**
	 * Adds an element to the front of the queue
	 * @param value The element to add
	 */
    void enqueue(E value);
    
    /**
     * Removes an element from the back of the queue
     * @return The element that was removed
     */
    E dequeue();
    
    /**
     * Tells the object that is at the front of the queue
     * @return The object at the front of the queue
     */
    E front();
    
    /**
     * Tells the size of the queue
     * @return The size of the queue
     */
    int size();
    
    /**
     * Tells if the queue is empty or not
     * @return True if empty, false otherwise
     */
    boolean isEmpty();
}
