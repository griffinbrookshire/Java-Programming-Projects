package edu.ncsu.csc316.dsa.list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A list made of an array
 * @author griffin
 *
 * @param <E> a generic object
 */
public class ArrayBasedList<E> extends AbstractList<E> {

	/** the size of the list */
	private int size;
	/** the array that holds the lists data */
	private E[] data;
	/** the default cap of a list */
	private static final int DEFAULT_CAPACITY = 10;

	/**
	 * Constructs a list with default cap
	 */
	public ArrayBasedList(){
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructs a list with specified cap
	 * @param initialCapacity Specified cap
	 */
	@SuppressWarnings("unchecked")
	public ArrayBasedList(int initialCapacity) {
		data = (E[])(new Object[initialCapacity]);
		this.size = 0;
	}

	/**
	 * Ensures there is room for more elements
	 * @param minCapacity Min cap to check for
	 */
	private void ensureCapacity(int minCapacity) {
		int oldCapacity = data.length;
		if (minCapacity > oldCapacity) {
			int newCapacity = (oldCapacity * 2) + 1;
			if (newCapacity < minCapacity) {
				newCapacity = minCapacity;
			}
			data = Arrays.copyOf(data, newCapacity);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void add(int index, E value) {
		checkIndexForAdd(index);
		ensureCapacity(size + 1);
		E[] temp = (E[])(new Object[size + 1]);
		for(int i = 0; i < index; i++) {
			temp[i] = data[i];
		}
		temp[index] = value;
		for(int i = index + 1; i < temp.length; i++) {
			temp[i] = data[i - 1];
		}
		data = temp;
		size++;
	}

	@Override
	public E get(int index) {
		checkIndex(index);
		return data[index];
	}

	@Override
	public E remove(int index) {
		checkIndex(index);
		E removed = data[index];
		for (int i = index; i < data.length - 1; i++) {
			data[i] = data[i + 1];
		}
		size--;
		return removed;
	}

	@Override
	public E set(int index, E value) {
		checkIndex(index);
		E replaced = data[index];
		data[index] = value;
		return replaced;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<E> iterator() {
		return new ElementIterator();
	}

	/**
	 * Iterates through an array based list
	 * @author griffin
	 *
	 */
	private class ElementIterator implements Iterator<E> {

		/** position of the iterator */
		private int position;
		/** tells if we can remove */
		private boolean removeOK;

		/**
		 * Constructs an iterator
		 */
		public ElementIterator() {
			position = -1;
			removeOK = false;
		}
		
		/**
		 * Tells if there is a next element
		 */
		public boolean hasNext() {
			return position < size - 1;
		}

		/**
		 * Gets the next element
		 */
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			removeOK = true;
			position++;
			return data[position];
		}

		/**
		 * Removes the current element
		 */
		public void remove() {
			if (!removeOK) {
				throw new IllegalStateException();
			}
			int index = position;
			if (index == size - 1) {
				size--;
				return;
			}
			for (int i = index ; i < data.length - 1; i++) {
				data[i] = data[i + 1];
			}
			size--;
		}
	}
}