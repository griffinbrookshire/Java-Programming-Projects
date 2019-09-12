package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Abstract list class that mimics the one located in the Java api
 * Used similarly to an ArrayList
 * @author wshenry
 *
 * @param <E> object for which the LinkedList is created for
 */
public class LinkedAbstractList<E> extends AbstractList<E> {
	/** Pointer to the front of the list */
	private ListNode front;
	/** Pointer to the back of the list */
	private ListNode back;
	/** Size of the list */
	private int size;
	/** Capacity of the list */
	private int capacity;
	
	/**
	 * Constructor for the Linked List
	 * @param capacity max capacity for the list
	 */
	public LinkedAbstractList(int capacity) {
		front = null;
		size = 0;
		setCapacity(capacity);
		back = null;
	}
	
	/**
	 * Setter for the capacity
	 * @param capacity new capacity
	 */
	public void setCapacity(int capacity) {
		if(capacity < 0 || capacity < size) {
			throw new IllegalArgumentException("Invalid enrollment cap");
		}
		this.capacity = capacity;
	}
	
	/**
	 * Gets the object in a specific index of the list
	 * @param idx index of the element
	 * @return the element at the index
	 * @throws IndexOutOfBoundsException if the index is out of range for the list
	 */
	@Override
	public E get(int idx) {
		if(idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}
//		if (idx ==size) {
//			return back.data;
//		}
		if (idx == 0) {
			return front.data;
		}
		ListNode a = front;
		for(int i = 0; i < idx; i++) {
			a = a.next;
		}
		return a.data;
	}
	
	/**
	 * Adds an element to a specific index of the list
	 * @param idx index of the added element
	 * @param element element which one wishes to add
	 * @throws IllegalArgumentException if either the list is at capacity or the element is a duplicate
	 * @throws NullPointerException if the element is null
	 * @throws IndexOutOfBoundsException if the given index is out of range of the list
	 */
	@Override
	public void add(int idx, E element) {
		if(size == capacity) {
			throw new IllegalArgumentException();
		}
		if(element == null) {
			throw new NullPointerException();
		}
		if(idx < 0 || idx > size) {
			throw new IndexOutOfBoundsException();
		}
		ListNode test = front;
		for(int i = 0; i < size; i++) {
			if(element.equals(test.data)) {
				throw new IllegalArgumentException();
			}
			test = test.next;
		}
		if (idx == 0 && size == 0) {
			ListNode n = new ListNode(element, front);
			front = n;
			back = n;
		} else if (idx == 0) {
			ListNode n = new ListNode(element, front);
			front = n;
		} else if (idx == size) {
			ListNode n = new ListNode(element);
			back.next = n;
			back = n;
		} else {
			ListNode a = front;
			for(int i = 0; i < idx - 1; i++) {
				a = a.next;
			}
			ListNode n = new ListNode(element, a.next);
			a.next = n;
		}
		size++;
	}
	
	/**
	 * Returns the element that is removed at the specified index
	 * @param idx index of the removed element
	 * @return the removed object
	 * @throws IndexOutOfBoundsException if the index is out of range for the list
	 */
	@Override
	public E remove(int idx) {
		E element;
		if(idx < 0 || idx > size) {
			throw new IndexOutOfBoundsException();
		}
		if(idx == 0) {
			element = front.data;
			front = front.next;
			size--;
			return element;
		}
		ListNode a = front;
		for(int i = 0; i < idx; i++) {
			a = a.next;
		}
		element = a.data;
		a = a.next;
		size--;
		return element;
	}
	
	/**
	 * Sets the element at the index to a new element
	 * @param idx index of the element to be set
	 * @param element new element
	 * @return the old element of the list in the index that is being set
	 * @throws IllegalArgumentException if the element is a duplicate
	 * @throws NullPointerException if the element is null
	 * @throws IndexOutOfBoundsException if the given index is out of range of the list
	 */
	@Override
	public E set(int idx, E element) {
		E old;
		if(element == null) {
			throw new NullPointerException();
		}
		for(int i = 0; i < size; i++) {
			if(element.equals(this.get(i))) {
				throw new IllegalArgumentException();
			}
		}
		if(idx < 0 || idx > size) {
			throw new IndexOutOfBoundsException();
		}
		ListNode a = front;
		if(idx == 0) {
			old = a.data;
			front = new ListNode(element, front.next);
			return old;
		}
		for(int i = 0; i < idx - 1; i++) {
			a = a.next;
		}
		old = a.data;
		a.data = element;
		return old;
	}
	
	/**
	 * Getter for the size of the list
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Inner class for the LinkedList, ListNode holds the information for the LinkedList
	 * @author wshenry
	 *
	 */
	private class ListNode {
		/** Object that the node holds */
		private E data;
		/** Pointer to the next element in the list */
		private ListNode next;
		
		/** 
		 * Constructor for node with no pointer
		 * @param data object the node represents
		 */
		ListNode(E data) {
			this.data = data;
			this.next = null;
		}
		
		/**
		 * Constructor for node with pointer
		 * @param data object the node represents
		 * @param next node which the pointer points to
		 */
		ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
	}
	
	/**
	 * Gets the capacity of the lsit
	 * @return capacity of the list
	 */
	public int getCapacity() {
		return capacity;
	}

}
