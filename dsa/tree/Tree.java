package edu.ncsu.csc316.dsa.tree;

import edu.ncsu.csc316.dsa.Position;

/**
 * Defines behavior for a Tree
 * @author griffin
 *
 * @param <E> a generic object
 */
public interface Tree<E> {
	
	/**
	 * Returns the root of the tree
	 * @return root of tree
	 */
    Position<E> root();
    
    /**
     * Returns the parent of the given node p
     * @param p Position of node
     * @return parent of node
     */
    Position<E> parent(Position<E> p);
    
    /**
     * Returns an iterable list
     * @param p Position of node
     * @return iterable list
     */
    Iterable<Position<E>> children(Position<E> p);
    
    /**
     * Returns the number of children of the given node p
     * @param p Position of node
     * @return number of children
     */
    int numChildren(Position<E> p);
    
    /**
     * Returns true if the node has one or more children
     * @param p Position of node
     * @return true if node has children, false otherwise
     */
    boolean isInternal(Position<E> p);
    
    /**
     * Returns true if the node has no children
     * @param p Position of node
     * @return true if leaf, false otherwise
     */
    boolean isLeaf(Position<E> p);
    
    /**
     * Returns true if the node is the root of the tree (has no parent)
     * @param p Position of node
     * @return true if root, false otherwise
     */
    boolean isRoot(Position<E> p);
    
    /**
     * Returns the number of entries in the tree
     * @return number of entries
     */
    int size();
    
    /**
     * Returns true if the tree is empty; otherwise, returns false
     * @return true if the tree is empty; otherwise, returns false
     */
    boolean isEmpty();
    
    // We will also add the following traversals to our Tree interface
    /**
     * Iterates in a preOrder fashion
     * @return iterable list
     */
    Iterable<Position<E>> preOrder();
    
    /**
     * Iterates in a postOrder fashion
     * @return iterable list
     */
    Iterable<Position<E>> postOrder();
    
    /**
     * Iterates in a levelOrder fashion
     * @return iterable list
     */
    Iterable<Position<E>> levelOrder();
}