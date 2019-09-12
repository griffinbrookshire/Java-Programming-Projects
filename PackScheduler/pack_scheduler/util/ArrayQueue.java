/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;
/**
 * ArrayQueue is a queue operated by an array.
 * 
 * @author ejwalsh
 *
 * @param <E> object type
 */
public class ArrayQueue<E> implements Queue<E> {
	
	/** Holds elements */
	private ArrayList<E> al;
	
	/** Capacity of list */
	private int capacity;
	
	/**
	 * Constructs an ArrayQueue
	 * @param capacity the capacity of the list
	 */
	public ArrayQueue (int capacity) {
		al = new ArrayList<E>();
		this.setCapacity(capacity);
	}
	
	/**
	 * Adds an element to the queue
	 * @param element E element to add
	 */
	@Override
	public void enqueue(E element) {
		if (al.size() == capacity) {
			throw new IllegalArgumentException();
		}
		al.add(al.size(), element);
	}

	/**
	 * Removes an element from the queue
	 * @return E object that was removed
	 */
	@Override
	public E dequeue() {
		E elem = al.get(0);
		al.remove(0);
		return elem;
	}

	/**
	 * Checks if the queue is empty
	 * @return true if queue is empty otherwise false
	 */
	@Override
	public boolean isEmpty() {
		return al.isEmpty();
	}

	/**
	 * Returns the size of the queue
	 * @return size of queue
	 */
	@Override
	public int size() {
		return al.size();
	}

	/**
	 * Sets the capacity of the queue
	 * @param cap the cap to set to
	 */
	@Override
	public void setCapacity(int cap) {
		if(cap < 0 || cap < al.size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = cap;	
	}

}
