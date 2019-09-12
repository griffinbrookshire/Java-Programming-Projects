/**
 * 
 */
package edu.ncsu.csc216.wolf_results.util;

import java.io.Serializable;

/**
 * This class is an implementation of the List interface with an array data structure
 * Some of this class uses code from the Lab. The grow array method and the void add method use code from the lab.
 * CSC216-212-LL-01. Authors are Max Richgruber, Sean Snively, and Sammy Penninger.
 * @author Sammy Penninger, Griffin Brookshire
 */
public class ArrayList implements Serializable, List {
	/** A constant unique to this class */
	private static final long serialVersionUID = 1L;
	/** A constant that resizes the array list */
	private static final int RESIZE = 10;
	/** An array that is used throughout the class */
	private Object[] list;
	/** The size of the array */
	private int size;

	/**
	 * The parameterless constructor
	 */
	public ArrayList() {
		list = new Object[RESIZE];
		size = 0;
	}

	/**
	 * The constructor with parameters
	 * @param size the number of elements in the array
	 */
	public ArrayList(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException();
		}
		list = new Object[size];
		size = 0;
	}

	/**
	 * Determines if the element can be added
	 * @param element the element that is being checked
	 * @return true if the element can be added to the list, false otherwise
	 */
	public boolean add(Object element) {
		if(element == null) {
			throw new NullPointerException();
		}
		for(int i = 0; i < size; i++) {
			if(element.equals(list[i])) {
				throw new IllegalArgumentException();
			}
		}
		try {
			list[size] = element;
		} catch (ArrayIndexOutOfBoundsException e) {
			growArray();
			list[size] = element;
		}
		size++;
		return true;
	}

	/**
	 * Adds the element at the specified index
	 * @param index the index of the array
	 * @param element the element that is added to the specific index
	 *  @throws NullPointerException if the specified element is null and this
	 *             list does not permit null elements
	 * @throws IllegalArgumentException if some property of the specified
	 *             element prevents it from being added to this list
	 * @throws IndexOutOfBoundsException if the index is out of range
	 * Some of this code is from the lab. CSC216-212-LL-01: authors are Max Richgruber, Sean Snively, and Sammy Penninger.
	 */
	public void add(int index, Object element) {
		if(element == null) {
			throw new NullPointerException();
		}
		for(int i = 0; i < size; i++) {
			if(element.equals(list[i])) {
				throw new IllegalArgumentException();
			}
		}
		if(index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		if(index < size || size + 1 > list.length) {
			growArray();
		}
		Object[] temp = (Object[])(new Object[size + 1]);
		for(int i = 0; i < index; i++) {
			temp[i] = list[i];
		}
		temp[index] = element;
		for(int i = index + 1; i < temp.length; i++) {
			temp[i] = list[i - 1];
		}
		list = temp;
		size++;
	}

	/**
	 * Returns true if this list contains the specified element. More formally,
	 * returns true if and only if this list contains at least one element e
	 * @param o element whose presence in this list is to be tested
	 * @return true if this list contains the specified element, false otherwise
	 */
	public boolean contains(Object o) {
		if (o == null) {
			return false;
		}
		for (int i = 0; i < list.length; i++) {
			if (o.equals(list[i])) {
				return true;
			}
		}
		return false;
	}


	/**
	 * Returns the element at the specified position in the array list
	 * @param index the specified position
	 * @return the element at the specified position in the array list
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public Object get(int index) {
		if(index < 0 || index > size() - 1) {
			throw new IndexOutOfBoundsException();
		}
		return list[index];
	}

	/**
	 * Returns the index of the first occurrence of the specified element in
	 * this list, or -1 if this list does not contain the element.
	 * @param o the element to search for
	 * @return the index of the first occurrence of the specified element
	 */
	public int indexOf(Object o) {
		if(o == null) {
			throw new NullPointerException();
		}
		for (int i = 0; i < size; i++) {
			if (list[i].equals(o)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns true if this list contains no elements.
	 * @return true if this list contains no elements
	 */
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Removes the element at the specified position in this list (optional
	 * operation). Shifts any subsequent elements to the left (subtracts one
	 * from their indices). Returns the element that was removed from the list.
	 *
	 * @param index the index of the element to be removed
	 * @return the element previously at the specified position
	 * @throws IndexOutOfBoundsException if the index is out of the  range
	 */
	public Object remove(int index) {
		if(index < 0 || index > size() - 1) {
			throw new IndexOutOfBoundsException();
		}
		Object[] temp = (Object[])(new Object[size]);
		for(int i = 0; i < index; i++) {
			temp[i] = list[i];
		}
		Object element = list[index];
		for(int i = index; i < size - 1; i++) {
			temp[i] = list[i + 1];
		}
		temp[size - 1] = null;
		list = temp;
		size--;
		return element;
	}

	/**
	 *  Returns the number of elements in this list. If this list contains more
	 * than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
	 *
	 * @return the number of elements in this list
	 */
	public int size() {
		return size;
	}

	/**
	 * Grows the array past the original size
	 * This code is from the lab. CSC216-212-LL-01: authors are Max Richgruber, Sean Snively, and Sammy Penninger.
	 */
	private void growArray() {
		int temp;
		temp = list.length * 2;
		Object[] tempList = new Object[temp];
		for (int i = 0; i < list.length; i++) {
			tempList[i] = this.list[i];
		}
		list = tempList;
	}

}