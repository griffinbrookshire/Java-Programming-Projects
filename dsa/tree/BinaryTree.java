package edu.ncsu.csc316.dsa.tree;

import edu.ncsu.csc316.dsa.Position;

/**
 * Defines behavior of a binary tree
 * @author griffin
 *
 * @param <E> a generic object
 */
public interface BinaryTree<E> extends Tree<E> {
	
	/**
	 * Gets the node to the left
	 * @param p Position of the node
	 * @return node to the left
	 */
    Position<E> left(Position<E> p);
    
    /**
     * Gets the node to the right
     * @param p Position of the node
     * @return node to the right
     */
    Position<E> right(Position<E> p);
    
    /**
     * Gets the sibling of this node
     * @param p Position of the node
     * @return sibling node
     */
    Position<E> sibling(Position<E> p);
    
    /**
     * Gets an iterator
     * @return iterable list
     */
    Iterable<Position<E>> inOrder();
}