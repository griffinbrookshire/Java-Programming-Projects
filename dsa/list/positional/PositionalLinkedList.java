
package edu.ncsu.csc316.dsa.list.positional;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.ncsu.csc316.dsa.Position;

/**
 * 
 * Got this code from (rwhess) form has been filled out by both parties.
 * 
 * The PositionalLinkedList with positions put together to create a data structure
 * @author Ryan Hess (rwhess)
 *
 * @param <E> for any given Element
 */
public class PositionalLinkedList<E> implements PositionalList<E> {

	/** The front PositionalNode */
    private PositionalNode<E> front;
    /** The end PositionalNode */
    private PositionalNode<E> tail;
    /** The size of the list */
    private int size;

    /**
     * The constructor for the PositionalLinkedList which creates it to start
     */
    public PositionalLinkedList() {
        front = new PositionalNode<E>(null);
        tail = new PositionalNode<E>(null, null, front);
        front.setNext(tail);
        size = 0;
    }
    
    @Override
    public Iterator<E> iterator() {
        // we start at front.getNext() because front is a dummy/sentinel node
        return new ElementIterator(front.getNext());
    }

	@Override
	public Position<E> addAfter(Position<E> p, E value) {
		PositionalNode<E> p2 = validate(p);
		return addBetween(value, p2.getNext(), p2);
	}

	@Override
	public Position<E> addBefore(Position<E> p, E value) {
		PositionalNode<E> p2 = validate(p);
		return addBetween(value, p2, p2.getPrevious());
	}

	@Override
	public Position<E> addFirst(E value) {
		return addBetween(value, front.getNext(), front);
	}

	@Override
	public Position<E> addLast(E value) {
		return addBetween(value, tail, tail.getPrevious());
	}

	@Override
	public Position<E> after(Position<E> p) {
		PositionalNode<E> p2 = validate(p);
		return p2.getNext();
	}

	@Override
	public Position<E> before(Position<E> p) {
		PositionalNode<E> p2 = validate(p);
		return p2.getPrevious();
	}

	@Override
	public Position<E> first() {
		return front.getNext();
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Position<E> last() {
		return tail.getPrevious();
	}

	@Override
	public Iterable<Position<E>> positions() {
		return new PositionIterable();
	}

	@Override
	public E remove(Position<E> p) {
		PositionalNode<E> p2 = validate(p);
		p2.getPrevious().setNext(p2.getNext());
		p2.getNext().setPrevious(p2.getPrevious());
		size--;
		return p2.getElement();
	}

	@Override
	public E set(Position<E> p, E value) {
		PositionalNode<E> p2 = validate(p);
		E element = p2.getElement();
		p2.setElement(value);
		return element;
	}

	@Override
	public int size() {
		return size;
	}
	
	/**
	 * The actual position with and element and references to other positions
	 * @author Ryan Hess (rwhess)
	 *
	 * @param <E> for any acceptable Element
	 */
	private static class PositionalNode<E> implements Position<E> {
	
		/** The element within the position */
	    private E element;
	    /** The reference to the next position */
	    private PositionalNode<E> next;
	    /** The reference to the previous position */
	    private PositionalNode<E> previous;
	
	    /**
	     * The constructor for the PositionalNode setting the value
	     * @param value the value to be set
	     */
	    public PositionalNode(E value) {
	        this(value, null);
	    }
	
	    /**
	     * The constructor for the PositionalNode setting value and next
	     * @param value the value to be set
	     * @param next the next position reference to be set
	     */
	    public PositionalNode(E value, PositionalNode<E> next) {
	        this(value, next, null);
	    }
	
	    /**
	     * The constructor for the PositionalNode setting value, next, and prev
	     * @param value the value to be set
	     * @param next the next positional reference to be set
	     * @param prev the previous positional reference to be set
	     */
	    public PositionalNode(E value, PositionalNode<E> next, PositionalNode<E> prev) {
	        setElement(value);
	        setNext(next);
	        setPrevious(prev);
	    }
	
	    /**
	     * Setter for the previous field
	     * @param prev the previous position to be set
	     */
	    public void setPrevious(PositionalNode<E> prev) {
	        previous = prev;
	    }
	
	    /**
	     * Getter for the previous field
	     * @return the previous position
	     */
	    public PositionalNode<E> getPrevious() {
	        return previous;
	    }
	    
	    /**
	     * Setter for the next field
	     * @param next the next position to be set
	     */
	    public void setNext(PositionalNode<E> next) {
	        this.next = next;
	    }

	    /**
	     * The getter for the next field
	     * @return the next position
	     */
	    public PositionalNode<E> getNext() {
	        return next;
	    }
	
	    @Override
	    public E getElement() {
	        return element;
	    }
	    
	    /**
	     * Setter for the element of the position
	     * @param element the value to be set
	     */
	    public void setElement(E element) {
	        this.element = element;
	    }
 	}
	
	/**
	 * Allows to cast Position to be a PositionalNode safely
	 * @param p the position to cast
	 * @return the Node casted
	 */
	private PositionalNode<E> validate(Position<E> p) {
	    if (p instanceof PositionalNode) {
	        return (PositionalNode<E>) p;
	    }
	    throw new IllegalArgumentException("Position is not a valid positional list node.");
	}
	
	/**
	 * Helper method for adding, basically for functionality that would repeat
	 * @param value the value to add
	 * @param next the next to be set after adding
	 * @param prev the previous to be set after adding
	 * @return the new position
	 */
	private Position<E> addBetween(E value, PositionalNode<E> next, PositionalNode<E> prev) {
		PositionalNode<E> p = new PositionalNode<E>(value, next, prev);
		p.getPrevious().setNext(p);
		p.getNext().setPrevious(p);
		size++;
		return p;
	}
	
	/**
	 * The iterator for the PositionalLinkedList
	 * @author Ryan Hess (rwhess)
	 */
    private class PositionIterator implements Iterator<Position<E>> {

    	/** The current position */
        private Position<E> current;
        /** For the most recent position */
        private Position<E> recent;

        /**
         * The constructor for the PositionIterator, creating the start position
         * Using some implementation from the textbook page 287
         * @param start the beginning position
         */
        public PositionIterator(PositionalNode<E> start) {
        	
            current = first();
            recent = null;
        }

        @Override
        public boolean hasNext() {
        	PositionalNode<E> e = validate(current);
          	return e.getElement() != null;
        }
 
        /**
         * Using some implementation from the textbook page 287
         */
        @Override
        public Position<E> next() throws NoSuchElementException {
        	if (!hasNext()) {
        		throw new NoSuchElementException();
        	}
        	if (current.getElement() == null) { 
        		throw new NoSuchElementException();
        	}
    		recent = current;
            current = after(current);
            return recent;
        }

        /**
         * Using some implementation from the textbook page 287
         */
        @Override
        public void remove() throws IllegalStateException {
        	if (recent == null) { 
        		throw new IllegalStateException();
        	}
        	PositionalLinkedList.this.remove(recent);
        	recent = null;
        	
            // You should consider delegating to
            // the outer class's remove(position) method,
            // similar to:
            // PositionalLinkedList.this.remove(position);
        }
    }
    
    /**
     * The PositionIterable class which is a wrapper to return an Iterable object
     * @author Ryan Hess (rwhess)
     */
    private class PositionIterable implements Iterable<Position<E>> {
        
        @Override
        public Iterator<Position<E>> iterator() {
            return new PositionIterator(front.getNext());
        }
    }
    
    /**
     * Iterates actual elements by using the position iterator
     * @author Ryan Hess (rwhess)
     */
    private class ElementIterator implements Iterator<E> {

    	/** The positional iterator to use */
    	private Iterator<Position<E>> it;
    	
    	/**
    	 * The constructor for the ElementIterator setting start
    	 * @param start the PositionalNode to set the PositionalIterator to
    	 */
        public ElementIterator(PositionalNode<E> start) {
            it = new PositionIterator(start);
        }
        
        @Override
        public boolean hasNext() {
        	return it.hasNext();
        }

        @Override
        public E next() {
            return it.next().getElement();
        }

        @Override
        public void remove() {
        	it.remove();
        }
    }

	@Override
	public E getElement() {
		// TODO Auto-generated method stub
		return null;
	}
}