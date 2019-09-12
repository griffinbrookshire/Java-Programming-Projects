package edu.ncsu.csc316.dsa.stack;

import java.util.EmptyStackException;

import edu.ncsu.csc316.dsa.list.SinglyLinkedList;

/**
 * A stack of linked nodes
 * @author griffin
 *
 * @param <E> a generic object
 */
public class LinkedStack<E> extends AbstractStack<E> {
	
	/** Holds the elements in the stack */
	private SinglyLinkedList<E> list;
	
	/**
	 * Constructs a LinkedStack object
	 */
	public LinkedStack() {
		list = new SinglyLinkedList<E>();
	}

	@Override
	public void push(E value) {
		list.addFirst(value);
	}

	@Override
	public E pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return list.removeFirst();
	}

	@Override
	public E top() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return list.first();
	}

	@Override
	public int size() {
		return list.size();
	}

}
