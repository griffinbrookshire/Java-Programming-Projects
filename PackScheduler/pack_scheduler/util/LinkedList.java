/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Class for a linked list iterator
 * @author ejwalsh
 * @param <E>
 *
 */
public class LinkedList<E> extends AbstractSequentialList<E>{
	private ListNode front;
	private ListNode back;
	private int size;
	
	/**
	 * Constructor for LinkedList
	 */
	public LinkedList() {
		front = new ListNode(null);
		back = new ListNode(null, front, null);
		front.next = back;
		size = 0;
	}

	@Override
	public ListIterator<E> listIterator(int idx) {
		LinkedListIterator lli = new LinkedListIterator(idx);
		return lli;
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractSequentialList#add(int, java.lang.Object)
	 */
	@Override
	public void add(int index, E element) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		for (int i = 0; i < size; i++) {
			if (this.get(i).equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		LinkedListIterator lLI = new LinkedListIterator(index);
		lLI.add(element);
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractSequentialList#set(int, java.lang.Object)
	 */
	@Override
	public E set(int index, E element) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		for (int i = 0; i < size; i++) {
			if (this.get(i).equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		LinkedListIterator lLI = new LinkedListIterator(index);
		E setter = lLI.next(); 
		lLI.set(element);
		return setter;
	}

	@Override
	public int size() {
		return size;
	}
	
	private class ListNode {
		public E data;
		private ListNode next;
		private ListNode prev;
		
		public ListNode(E data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}
		public ListNode(E data, ListNode prev, ListNode next) {
			this.data = data;
			this.next = next;
			this.prev = prev;
		}
	}
	
	private class LinkedListIterator implements ListIterator<E> {
		
		public ListNode previous;
		public ListNode next;
		public int nextIndex;
		public int previousIndex;
		private ListNode lastRetrieved;
		
		
		public LinkedListIterator(int index) {   
			if (index < 0  || index > size) {
				throw new IndexOutOfBoundsException();
			}
			this.previous = front;
			this.next = previous.next;
			this.previousIndex = index - 1;
			this.nextIndex = index;
			if (index != 0) {
				for (int i = 0; i < index; i++) {
					this.previous = next;
					this.next = next.next;
				}  
			}
			this.lastRetrieved = null;
		}

		@Override
		public void add(E element) {
			if(element == null) {
				throw new NullPointerException();
			} else if (front == null) {
				front = new ListNode(element);
			} else {
				ListNode toBeAdded = new ListNode(element);
				next.prev = toBeAdded;
				toBeAdded.next = next;
				toBeAdded.prev = previous;
				previous.next = toBeAdded;
				this.lastRetrieved = null;
				size++;
			}
			
		}

		@Override
		public boolean hasNext() {
			if (next.data == null) {
				return false;
			}
			return true;
		}

		@Override
		public boolean hasPrevious() {
			if (previous.data == null) {
				return false;
			}
			return true;
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			this.lastRetrieved = next;
			E nextUp = next.data;
			this.previousIndex++;
            this.nextIndex++;
			this.next = next.next; 
			return nextUp;
		}

		@Override
		public int nextIndex() {
			if (next == null) {
				return size; 
			}
			return nextIndex - 1;
		}

		@Override
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			this.lastRetrieved = previous;
			this.previous = previous.prev;
			this.previousIndex--;
            this.nextIndex--;
			return previous.data;
		}

		@Override
		public int previousIndex() {
			if (previous == null) {
                return -1;
            }
			return previousIndex;
		}

		@Override
		public void remove() {
			if (lastRetrieved == null) {
				throw new IllegalArgumentException();
			}
			lastRetrieved.prev.next = next;
			next = next.next;
			size--;
		}

		@Override
		public void set(E e) {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			} else if (e == null) {
				throw new NullPointerException();
			}
			lastRetrieved.data = e;
		}
	}

}
