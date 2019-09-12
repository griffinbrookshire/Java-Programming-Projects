package edu.ncsu.csc316.dsa.queue;

/**
 * Defines some behavior of a queue
 * @author griffin
 *
 * @param <E> a generic object
 */
public abstract class AbstractQueue<E> implements Queue<E> {
	
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
    
}