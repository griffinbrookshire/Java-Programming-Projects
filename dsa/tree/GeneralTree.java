package edu.ncsu.csc316.dsa.tree;

import java.util.Iterator;
import edu.ncsu.csc316.dsa.Position;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.list.SinglyLinkedList;
import edu.ncsu.csc316.dsa.list.positional.PositionalLinkedList;
import edu.ncsu.csc316.dsa.list.positional.PositionalList;

/**
 * Defines data and behavior of a general tree
 * @author griffin
 *
 * @param <E> a generic object
 */
public class GeneralTree<E> extends AbstractTree<E> implements GeneralTreeCollection<E> {

	/** Root node */
    private Node<E> root;
    
    /** Number of nodes */
    private int size;
    
    /**
     * Creates a general tree
     */
    public GeneralTree() {
        root = null;
        size = 0;
    }
    
    @Override
    public Position<E> root() {
        return root;
    }

    @Override
    public Position<E> parent(Position<E> p) {
        return validate(p).getParent();
    }

    @Override
    public Iterable<Position<E>> children(Position<E> p) {
        Node<E> node = validate(p);
        List<Position<E>> ret = new SinglyLinkedList<Position<E>>();
        for(Position<Node<E>> n : node.getChildren().positions())
        {
            ret.addLast(n.getElement());
        }
        return ret;
    }

    @Override
    public int numChildren(Position<E> p) {
        Node<E> node = validate(p);
        return node.getChildren().size();
    }
    
    @Override
	public Position<E> addChild(Position<E> p, E value) {
	    Node<E> node = validate(p);
	    Node<E> newNode = createNode(value);
	    node.getChildren().addLast(newNode);
	    newNode.setParent(node);
	    size++;
	    return newNode;
	}

	@Override
	public E remove(Position<E> p) {
		Node<E> removedNode = validate(p);
		if (removedNode.getChildren().size() > 1) {
			throw new IllegalArgumentException();
		}
		E removedElement = p.getElement();
		if (size() == 1) {
			this.root = null;
			size--;
			return removedElement;
		}
		if (this.isRoot(p)) {
			this.root = removedNode.getChildren().first().getElement();
			size--;
			return removedElement;
		}
		if (isLeaf(p)) {
			removedNode.getParent().getChildren().remove(removedNode.getParent().getChildren().first());
			size--;
			return removedElement;
		}
		//set the parent of the removed nodes child to be its parent
		removedNode.getChildren().first().getElement().setParent(removedNode.getParent());
		size--;
		return removedElement;
	}

	@Override
    public Iterator<E> iterator() {
        return new ElementIterator(preOrder().iterator());
    }

	@Override
	public Position<E> addRoot(E value) {
	    if (root != null) {
	        throw new IllegalArgumentException("Tree already has a root");
	    }
	    this.root = createNode(value);
	    size = 1;
	    return root;
	}

	@Override
	public E set(Position<E> p, E value) {
	    Node<E> node = validate(p);
	    E original = node.getElement();
	    node.setElement(value);
	    return original;
	}

	private Node<E> validate(Position<E> p) {
	    if(!(p instanceof Node)) {
	        throw new IllegalArgumentException("Position is not a legal general tree node");
	    }
	    return (Node<E>)p;
	}

	/**
	 * Creates a node with the given element
	 * @param element Element held by the node
	 * @return Node holding the element
	 */
	public Node<E> createNode(E element) {
	    return new Node<E>(element);
	}

	@Override
    public int size() {
        return size;
    }

	/**
	 * A node of the tree
	 * @author griffin
	 *
	 * @param <E> a generic object
	 */
    private static class Node<E> extends AbstractNode<E> {

    	/** Parent of this node */
        private Node<E> parent;
        
        // A general tree node needs to maintain a list of children
        /** Children of this node */
        private PositionalList<Node<E>> children;
        
        /**
         * Creates a node
         * @param element Element held by this node
         */
        public Node(E element) {
            this(element, null);
        }
        
        /**
         * Creates a node with the given element and given parent
         * @param element Element held by this node
         * @param parent Parent of this node
         */
        public Node(E element, Node<E> parent) {
            super(element);
            setParent(parent);
            children = new PositionalLinkedList<Node<E>>();
        }
        
        /**
         * Sets the given node to be the parent of this node
         * @param p Parent node
         */
        public void setParent(Node<E> p) {
            parent = p;
        }
        
        /**
         * Gets the parent of this node
         * @return Parent of this node
         */
        public Node<E> getParent() {
            return parent;
        }
        
        /**
         * Gets the children of this node
         * @return List of children
         */
        public PositionalList<Node<E>> getChildren() {
            return children;
        }
    }
}