/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Interface for queues
 * @author ejwalsh
 * @param <E>
 *
 */
public interface Queue<E> {

	/**
	 * Method to add an element to the back of the queue
	 * @param element to add
	 */
	void enqueue(E element);
	
	/**
	 * Method to remove and return the front element of the queue
	 * @return E element removed
	 */
	E dequeue();
	
	/**
	 * Method to tell if a queue is empty
	 * @return true if empty false otherwise
	 */
	boolean isEmpty();
	
	/**
	 * Method to get the size of the queue
	 * @return size of queue
	 */
	int size();
	
	/**
	 * Method the set the queue's capacity
	 * @param capacity of queue
	 */
	void setCapacity(int capacity);
}
