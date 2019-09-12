/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * ArrayStack is a stack of elements
 * @author griffin
 * @param <E> object type
 */
public class ArrayStack<E> implements Stack<E> {
	
	/** ArrayList to delegate tasks to */
	private ArrayList<E> list;
	/** Number of elements in the stack */
	private int size;
	/** Maximum elements can be added to stack */
	private int capacity;
	
	/**
	 * Constructs an ArrayStack object
	 * @param capacity of stack
	 */
	public ArrayStack(int capacity) {
		list = new ArrayList<E>();
		this.capacity = capacity;
	}

	@Override
	public void push(E element) {
		if (size == capacity) {
			throw new IllegalArgumentException();
		}
		size++;
		list.add(0, element);
	}

	@Override
	public E pop() {
		if (size == 0) {
			throw new EmptyStackException();
		}
		size--;
		return list.remove(0);
	}

	@Override
	public boolean isEmpty() {
		return (size == 0);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

}
