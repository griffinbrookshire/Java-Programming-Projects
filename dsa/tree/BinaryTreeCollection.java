package edu.ncsu.csc316.dsa.tree;

import edu.ncsu.csc316.dsa.Position;

/**
 * Defines behavior for a binary tree collection
 * @author griffin
 *
 * @param <E> a generic object
 */
public interface BinaryTreeCollection<E> extends BinaryTree<E>, Iterable<E> {
	
	/**
	 * Adds a root to the tree
	 * @param value Value of the root
	 * @return Position of the root
	 */
    Position<E> addRoot(E value);
    
    /**
     * Adds a node to the left
     * @param p Position of node
     * @param value Value of node
     * @return Position of node
     */
    Position<E> addLeft(Position<E> p, E value);
    
    /**
     * Adds a node to the right
     * @param p Position of node
     * @param value Value of node
     * @return Position of node
     */
    Position<E> addRight(Position<E> p, E value);
    
    /**
     * Removes the node at the position
     * @param p Position of node
     * @return Element of removed node
     */
    E remove(Position<E> p);
    
    /**
     * Sets the value of the node
     * @param p Position to set
     * @param value Value to set to
     * @return Value that was replaced
     */
    E set(Position<E> p, E value);
}