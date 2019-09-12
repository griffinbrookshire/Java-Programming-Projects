package edu.ncsu.csc316.dsa.stack;

/**
 * Implements some behavior of a stack
 * @author griffin
 *
 * @param <E> a generic object
 */
public abstract class AbstractStack<E> implements Stack<E> {
	
	@Override
    public boolean isEmpty() {
        return size() == 0;
    }

}
