package edu.ncsu.csc316.dsa.tree;

import edu.ncsu.csc316.dsa.Position;

/**
 * Defines behavior for a general tree collection
 * @author griffin
 *
 * @param <E> a generic object
 */
public interface GeneralTreeCollection<E> extends Tree<E>, Iterable<E> {
	
	/**
	 * Adds a root
	 * @param value Value of node
	 * @return Position of node
	 */
    Position<E> addRoot(E value);
    
    /**
     * Adds a child
     * @param p Position of node to add child to
     * @param value Value of node
     * @return Position of child
     */
    Position<E> addChild(Position<E> p, E value);
    
    /**
     * Removes a node at the position
     * @param p Position of node to remove
     * @return Value of removed node
     */
    E remove(Position<E> p);
    
    /**
     * Sets the value of the node
     * @param p Position of node to set
     * @param value Value to set to
     * @return old value of node
     */
    E set(Position<E> p, E value);
}