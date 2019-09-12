/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * LinkedStack is a Stack of LinkedNodes
 * 
 * @author griffin
 * @param <E> object type
 *
 */
public class LinkedStack<E> implements Stack<E> {
	
	/** LinkedAbstractList to delegate to */
	private LinkedAbstractList<E> list;
	
	/** Number of elements in stack */
	private int size;
	
	/**
	 * Constructs an LinkedStack object
	 * @param capacity of stack
	 */
	public LinkedStack(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
	}

	/**
	 * Adds the specified element to the stack
	 * @param element the element being added to the stack
	 */
	@Override
	public void push(E element) {
		list.add(0, element);
		size++;
	}

	/**
	 * Removes (pops) the element at the front of the stack
	 * @throws EmptyStackException if the stack is empty 
	 * @return the new stack after the element at the front has been removed
	 */
	@Override
	public E pop() {
		if (size == 0) {
			throw new EmptyStackException();
		}
		size--;
		return list.remove(0);
	}

	/**
	 * Determines if the stack is empty
	 * @return true if the size is zero, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return (size == 0);
	}

	/**
	 * Gets the size of the stack
	 * @return the size of the stack
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Sets the capacity of the stack
	 * @param capacity the upper boundary (maximum) of the size of the stack
	 */
	@Override
	public void setCapacity(int capacity) {
		list.setCapacity(capacity);
	}

}
