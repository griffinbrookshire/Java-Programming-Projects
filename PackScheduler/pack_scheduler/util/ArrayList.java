package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;
/**
 * Custom ArrayList class that extends the Abstract List
 * @author wshenry
 * @author noaheggenschwiler
 * @param <E>
 */
@SuppressWarnings("unchecked")
public class ArrayList<E> extends AbstractList<E> {
	/** Initial Size of ArrayList when constructed **/
	public static final int INIT_SIZE = 10;
	
	/** Private list of type element **/
	private E[] list;
	
	/** Private field of size of Array **/
	private int size;
	
	/**
	 * Constructor method for the ArrayList
	 * Sets the list size to 0
	 * Creates an arraylist of length 10
	 */
	public ArrayList() {
		//E item = (E)(new Object()); Ignoring for the moment
		list = (E[])(new Object[INIT_SIZE]);
		size = 0;
	}
	
	/**
	 * Add method for ArrayList
	 * @param index is index of ArrayList that element should be added
	 * @param element is the element that will be added at the given index
	 * @throws NullPointerException if element trying to be added is null
	 * @throws IllegalArgumentException if element to be added is a duplicate
	 * @throws IndexOutOfBoundsException if index is less that 0 or greater than size
	 */
	public void add(int index, E element) {
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
		E[] temp = (E[])(new Object[size + 1]);
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
	 * Method that will double the size of ArrayList if ArrayList is full
	 */
	private void growArray() {
		E[] temp = (E[])(new Object[size * 2]);
		for(int i = 0; i < size; i++) {
			temp[i] = list[i];
		}
		list = temp;
	}
	
	/**
	 * Remove method will remove the value at the specified index of the Array
	 * @param index is the index that contains the object that will be removed
	 * @throws IndexOutOfBoundsException if index is less that 0 or greater than size
	 * @return element returns the element that is being removed from the list
	 */
	public E remove(int index) {
		if(index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		E[] temp = (E[])(new Object[size]);
		for(int i = 0; i < index; i++) {
			temp[i] = list[i];
		}
		E element = list[index];
		for(int i = index; i < size - 1; i++) {
			temp[i] = list[i + 1];
		}
		temp[size - 1] = null;
		list = temp;
		size--;
		return element;
	}
	
	/**
	 * Sets an element at a specified index
	 * @param index is index of array
	 * @param element is the element that will be set
	 * @return the element originally at the index
	 */
	public E set(int index, E element) {
		if(element == null) {
			throw new NullPointerException();
		}
		for(int i = 0; i < size; i++) {
			if(element.equals(list[i])) {
				throw new IllegalArgumentException();
			}
		}
		if(index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		if(index < size || size + 1 > list.length) {
			growArray();
		}
		E original = list[index];
		list[index] = element;
		return original;
	}
	
	/**
	 * Gets the element at a specified index of the array
	 * @param index is index of array that will be returned
	 * @return E at specified index
	 */
	@Override
	public E get(int index) {
		if(index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		return list[index];
	}
	
	/**
	 * Size of the array
	 * @return size of array
	 */
	@Override
	public int size() {
		return size;
	}

	
}
