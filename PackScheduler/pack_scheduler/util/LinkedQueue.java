/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * LinkedQueue is a Queue of LinkedNodes
 * 
 * @author ejwalsh
 * @param <E> object type
 *
 */
public class LinkedQueue<E> implements Queue<E> {

	/** Holds elements */
	private LinkedAbstractList<E> lal;
	
	/**
	 * Constructs a LinkedQueue with specified capacity
	 * @param cap capacity of queue
	 */
	public LinkedQueue (int cap) {
		lal = new LinkedAbstractList<E>(cap);
	}
	
	@Override
	public void enqueue(E element) {
		lal.add(lal.size(), element);
	}

	@Override
	public E dequeue() {
		if (lal.isEmpty()) {
			throw new NoSuchElementException();
		}
		E elem = lal.get(0);
		lal.remove(0);
		return elem;
	}

	@Override
	public boolean isEmpty() {
		return lal.isEmpty();
	}

	@Override
	public int size() {
		return lal.size();
	}

	@Override
	public void setCapacity(int capacity) {
		lal.setCapacity(capacity);
		
	}

}
