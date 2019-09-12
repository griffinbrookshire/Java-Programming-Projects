package edu.ncsu.csc316.dsa.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A linked list class for general elements
 * @author griffin
 *
 * @param <E> a general element
 */
public class SinglyLinkedList<E> extends AbstractList<E> {

	/** the size of the list */
	private int size;
	/** the front of the list */
	private LinkedListNode<E> front;
	/** the back of the list */
	private LinkedListNode<E> tail;

	/**
	 * Constructs a list
	 */
	public SinglyLinkedList() {
		// Let front be a dummy (sentinel) node
		front = new LinkedListNode<E>(null);
		tail = null;
		size = 0;
	}

	@Override
	public void add(int index, E element) {
		checkIndexForAdd(index);
		LinkedListNode<E> toAdd = new LinkedListNode<E>(element);
		if (size == 0) {
			front = toAdd;
			tail = toAdd;
			size++;
			return;
		}
		if (index == 0) {
			toAdd.setNext(front);
			front = toAdd;
			size++;
			return;
		}
		if (index == size) {
			tail.next = toAdd;
			tail = toAdd;
			size++;
			return;
		}
		LinkedListNode<E> temp = front;
		for (int i = 0; i < index - 1; i++) {
			temp = temp.getNext();
		}
		toAdd.setNext(temp.getNext());
		temp.setNext(toAdd);
		size++;
	}

	@Override
	public void addLast(E element) {
		LinkedListNode<E> toAdd = new LinkedListNode<E>(element, null);
		if (size == 0) {
			front = toAdd;
			tail = toAdd;
			size++;
			return;
		}
		tail.next = toAdd;
		tail = toAdd;
		size++;
	}

	@Override
	public Iterator<E> iterator() {
	 // We need to tell the iterator to skip the dummy/sentinel node
	 return new ElementIterator(front); // was front.getNext()
	}

	@Override
	public E last() {
		return tail.getElement();
	}

	@Override
	public E remove(int index) {
		checkIndex(index);
		if (index == 0) {
			E removed = front.getElement();
			front = front.getNext();
			size--;
			return removed;
		}
		boolean resetTail = false;
		if (index == size - 1) {
			resetTail = true;
		}
		LinkedListNode<E> temp = front;
		for (int i = 0; i < index - 1; i++) {
			temp = temp.getNext();
		}
		E removed = temp.getNext().getElement();
		if (resetTail) {
			tail = temp;
		} else {
			temp.next = temp.getNext().getNext();
		}
		size--;
		return removed;
	}

	@Override
	public E set(int index, E element) {
		checkIndex(index);
		LinkedListNode<E> temp = front;
		for (int i = 0; i < index; i++) {
			temp = temp.getNext();
		}
		E oldData = temp.getElement();
		temp.setElement(element);
		return oldData;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public E get(int index) {
		checkIndex(index);
		LinkedListNode<E> temp = front;
		for (int i = 0; i < index; i++) {
			temp = temp.getNext();
		}
		return temp.data;
	}

	/**
	 * A node of the list
	 * @author griffin
	 *
	 * @param <E> a generic object
	 */
	@SuppressWarnings("hiding")
	private class LinkedListNode<E> {

		/** the data of the node */
		private E data;
		/** the next node */
		private LinkedListNode<E> next;

		/**
		 * Constructs a node
		 * @param element The element that the node holds
		 */
		public LinkedListNode(E element) {
			this.data = element;
		}

		/**
		 * Constructs a node and defines its next node
		 * @param element The element that the node holds
		 * @param next The next node
		 */
		public LinkedListNode(E element, LinkedListNode<E> next) {
			this.data = element;
			this.next = next;
		}

		/**
		 * Gets the next node
		 * @return The next node
		 */
		public LinkedListNode<E> getNext() {
			return next;
		}

		/**
		 * Gets the element of a node
		 * @return The element of the node
		 */
		public E getElement() {
			return data;
		}

		/**
		 * Sets the next node
		 * @param next The next node
		 */
		public void setNext(LinkedListNode<E> next) {
			this.next = next;
		}

		/**
		 * Sets a nodes element
		 * @param element The element that the node will hold
		 */
		public void setElement(E element) {
			this.data = element;
		}

	}

	/**
	 * Iterates through a linked list
	 * @author griffin
	 *
	 *Cited Noah Eggens (ndeggens) on 2/28/19 from Lab 2
	 *All Documents were submitted from both parties
	 */
	private class ElementIterator implements Iterator<E> {
   	 // Keep track of the next node that will be processed
   	 private LinkedListNode<E> current;
   	 // Keep track of the node that was processed on the last call to 'next'
   	 private LinkedListNode<E> previous;
   	 // Keep track of the previous-previous node that was processed
   	 // so that we can update 'next' links when removing
   	 private LinkedListNode<E> previousPrevious;
   	 /** Field that keeps track if a LinkedListNode is allowed to be removed **/
   	 private boolean removeOK = false;

   	 /**
   	  * Constructor for the ElementIteratro 
   	  * @param start is the beginning of the SinglyLinkedList
   	  */
   	 public ElementIterator(LinkedListNode<E> start) {
   	     current = start;
   	 }

   	 /**
   	  * Returns if the SinglyLinkedList has another Node in the List
   	  * @return true if the SinglyLinkedList has another node
   	  */
   	 public boolean hasNext() {
   	    return current != null;
   	 }

        /**
         * Cited Neil Towne (nstowne) on 2/28/19 from Lab 2
         * All Documents were submitted from both parties
         */
   	 public E next() {
            if (hasNext()) {
                previousPrevious = previous;
                previous = current;
                current = current.getNext();
                removeOK = true;
                return previous.data;
            }
            throw new NoSuchElementException();
        }
   	    
        /**
         * Cited Noah Eggens (ndeggens) on 2/28/19 from Lab 2
         * All Documents were submitted from both parties
         */
   	 public void remove() {
            if (removeOK) {
                if (previousPrevious == null) {
                    SinglyLinkedList.this.remove(0);
                    removeOK = false;
                } else {
                    previousPrevious.next = current;
                    previous = previousPrevious;
                    removeOK = false;
                    size--;
                }
            } else {
                throw new IllegalStateException();
            }
        }
   	}

}