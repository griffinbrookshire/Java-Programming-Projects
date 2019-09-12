/**
 * 
 */
package edu.ncsu.csc216.wolf_results.util;

/**
 * This class is an implementation of the SortedList interface with a linked data structure
 * @author Sammy Penninger, Griffin Brookshire
 * @param <E> object type of list
 */
public class SortedLinkedList <E extends Comparable<E>> implements SortedList<E> {
	/** Instance of the front Node */
	private Node front;
	/** Size of the list */
	private int size;

	/**
	 * Constructs an empty SortedLinkedList
	 */
	public SortedLinkedList() {
		front = null;
		size = 0;
	}

	/**
	 * Gets the size of the list
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Returns true if this list contains no elements.
	 *
	 * @return true if this list contains no elements
	 */
	@Override
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Returns true if this list contains the specified element. More formally,
	 * returns true if and only if this list contains at least one element a
	 * such that (o==null ? a==null : o.equals(a)).
	 *
	 * @param element element whose presence in this list is to be tested
	 * @return true if this list contains the specified element
	 */
	@Override
	public boolean contains(E element) {
		Node test = front;
		for(int i = 0; i < size; i++) {
			if(element.equals(test.data)) {
				return true;
			}
			test = test.next;
		}
		return false;
	}

	/**
	 * Adds the specified element to list in sorted order
	 *
	 * @param element element to be appended to this list
	 * @return true (as specified by {@link Collection#add})
	 * @throws NullPointerException if elemenet is null
	 * @throws IllegalArgumentException if list already contains element
	 */
	@Override
	public boolean add(E element) {
		if (element == null) {
			throw new NullPointerException(); // test if null
		}
		if(this.contains(element)) {
			throw new IllegalArgumentException(); // test if duplicate
		}
		Node toAdd = new Node(element);
		if (front == null) {
			front = toAdd;
			size++;
			return true; // adds to an empty list
		} else {
			if (element.compareTo(front.data) < 0) {
				toAdd.next = front;
				front = toAdd;
				size++;
				return true; // adds to front of list
			} else {
				Node getToLoc = front;
				while (true) {
					if (getToLoc.next != null) {
						if (element.compareTo(getToLoc.next.data) < 0) {
							toAdd.next = getToLoc.next;
							getToLoc.next = toAdd;
							size++;
							return true; // adds to middle of list
						} else {
							getToLoc = getToLoc.next;
						}
					} else {
						getToLoc.next = toAdd;
						size++;
						return true; // adds to end of list
					}
				}
			}
		}
	}

	/**
	 * Gets the object at the index
	 * @param index the index of the list
	 * @return the object at the index
	 */
	@Override
	public E get(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		Node a = front;
		for(int i = 0; i < index; i++) {
			a = a.next;
		}
		return a.data;
	}

	/**
	 * 
	 * Removes and returns the object at the index
	 * @param index the index of the list
	 * @return the object at the index
	 */
	@Override
	public E remove(int index) {
		E element;
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		if(index == 0) {
			element = front.data;
			front = front.next;
			size--;
			return element;
		}
		Node a = front;
		for(int i = 0; i < index - 1; i++) {
			a = a.next;
		}
		element = a.next.data;
		a.next = a.next.next;
		size--;
		return element;
	}

	/**
	 * Gets the index of the object
	 * @param element the element
	 * @return the index of the element, or -1 if the element cannot be found
	 */
	@Override
	public int indexOf(E element) {
		Node a = front;
		for(int i = 0; i < size; i++) {
			if (a.data.equals(element)) {
				return i;
			}
			a = a.next;
		}
		return -1;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((front == null) ? 0 : front.hashCode());
		result = prime * result + size;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SortedLinkedList))
			return false;
		SortedLinkedList<?> other = (SortedLinkedList<?>) obj;
		if (front == null) {
			if (other.front != null)
				return false;
		} else if (!front.equals(other.front))
			return false;
		if (front != null && other.front != null && front.hashCode() != other.front.hashCode())
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	/**
	 * String representation of the list
	 * @return the string representation of the list
	 */
	@Override
	public String toString() {
		if (size == 0) {
			return "[]";
		}
		String list = "[";
		for (int i = 0; i < size; i++) {
			if (i != size - 1) {
				list = list + this.get(i) + ", ";
			} else {
				list = list + this.get(i) + "]";
			}
		}
		return list;
	}


	/**
	 * This inner class holds the objects that make up the SortedLinkedList
	 * @author Sammy Penninger, Griffin Brookshire
	 */
	private class Node {
		/** Object that the node holds */
		private E data;
		/** Pointer to the next element in the list */
		private Node next;

		/** 
		 * Constructor for node with no pointer
		 * @param data object the node represents
		 */
		public Node(E data) {
			this.data = data;
			this.next = null;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((data == null) ? 0 : data.hashCode());
			result = prime * result + ((next == null) ? 0 : next.hashCode());
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			Node other = (Node) obj;
			if (data != null && !data.equals(other.data))
				return false;
			if (next == null) {
				if (other.next != null)
					return false;
			} else if (!next.equals(other.next))
				return false;
			return true;
		}
	}

}