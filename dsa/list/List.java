package edu.ncsu.csc316.dsa.list;

/**
 * Defines behavior of a list
 * @author griffin
 *
 * @param <E> a genereic object
 */
public interface List<E> extends Iterable<E> {
	
	/**
	 * Adds an element to a certain index
	 * @param index Index to add at
	 * @param value Value to add
	 */
    void add(int index, E value);
    
    /**
     * Adds an element to the beginning of a list
     * @param value Value to add
     */
    void addFirst(E value);
    
    /**
     * Adds an element to the end of a list
     * @param value Value to add
     */
    void addLast(E value);
    
    /**
     * Gets the first element of a list
     * @return First element
     */
    E first();
    
    /**
     * Gets the element at a specific index
     * @param index Index to get at
     * @return element at the index
     */
    E get(int index);
    
    /**
     * Checks if the list is empty
     * @return true if it is empty, false if not
     */
    boolean isEmpty();
    
    /**
     * Gets the last element in a list
     * @return the last element
     */
    E last();
    
    /**
     * Removes the element at the given index
     * @param index Index to remove at
     * @return the element that was removed
     */
    E remove(int index);
    
    /**
     * Removes the first element in a list
     * @return first element in a list
     */
    E removeFirst();
    
    /**
     * Removes the last element in a list
     * @return the last element in a list
     */
    E removeLast();
    
    /**
     * Sets the element at the specified index to the given value
     * @param index Index to set at
     * @param value Value to set to
     * @return The value that was replaced
     */
    E set(int index, E value);
    
    /**
     * Gets the size of the list
     * @return the size of the list
     */
    int size();
}