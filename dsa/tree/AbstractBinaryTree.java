package edu.ncsu.csc316.dsa.tree;

import java.util.Iterator;

import edu.ncsu.csc316.dsa.Position;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.list.SinglyLinkedList;

/**
 * Defines data and behavior of a binary tree
 * @author griffin
 *
 * @param <E> a generic object
 */
public abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTreeCollection<E> {

    @Override
    public Iterable<Position<E>> inOrder() {
        List<Position<E>> traversal = new SinglyLinkedList<Position<E>>();

        if (!isEmpty()) {
            inOrderHelper(root(), traversal);
        }

        return traversal;
    }

    private void inOrderHelper(Position<E> p, List<Position<E>> traversal) {
        if (p == null) {
        	return;
        }
        inOrderHelper(left(p), traversal);
        traversal.addLast(p);
        inOrderHelper(right(p), traversal);
    }
    
    /**
     * Code from textbook page 320
     */
    @Override
    public Position<E> sibling(Position<E> p) {
		Position<E> parent = parent(p);
		if (parent == null) {
			return null;
		}
		if (p == left(parent)) {
			return right(parent);
		} else {
			return left(parent);
		}
    }
    
    private AbstractNode<E> validate(Position<E> p) {
        if(!(p instanceof AbstractNode)) {
            throw new IllegalArgumentException("Position is not a valid binary tree node");
        }
        return (AbstractNode<E>)(p);
    }
    
    /**
     * Code from textbook page 320
     */
    @Override
    public int numChildren(Position<E> p) {
		int count = 0;
		if (left(p) != null) {
			count++;
		}
		if (right(p) != null) {
			count++;
		}
		return count;
    }
    
    @Override
    public E set(Position<E> p, E value) {
        AbstractNode<E> node = validate(p);
        E original = node.getElement();
        node.setElement(value);
        return original;
    }
    
    @Override
    public Iterator<E> iterator() {
        return new ElementIterator(inOrder().iterator());
    }
    
    @Override
    public Iterable<Position<E>> children(Position<E> p) {
        AbstractNode<E> node = validate(p);
        List<Position<E>> returnList = new SinglyLinkedList<Position<E>>();
        if(left(node) != null) {
            returnList.addLast(left(node));
        }
        if(right(node) != null) {
            returnList.addLast(right(node));
        }
        return returnList;
    }
}