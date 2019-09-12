package edu.ncsu.csc316.dsa.tree;

import edu.ncsu.csc316.dsa.Position;

/**
 * A binary tree of linked nodes
 * @author griffin
 *
 * @param <E> a generic object
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

	/** Root of the tree */
    private Node<E> root;
    
    /** Number of nodes */
    private int size;

    /**
     * Creates a binary tree
     */
    public LinkedBinaryTree() {
        root = null;
        size = 0;
    }

    /**
     * Validates a node
     * @param p Position of node
     * @return Node object of Position
     */
    protected Node<E> validate(Position<E> p) {
    	if (p == null) {
    		return null;
    	}
        if (!(p instanceof Node)) {
            throw new IllegalArgumentException("Position is not a valid linked binary node");
        }
        return (Node<E>) p;
    }

    @Override
    public Position<E> left(Position<E> p) {
        Node<E> node = validate(p);
        return node.getLeft();
    }

    @Override
    public Position<E> right(Position<E> p) {
        Node<E> node = validate(p);
        return node.getRight();
    }

    @Override
    public Position<E> addLeft(Position<E> p, E value) {
        Node<E> node = validate(p);
        if (left(node) != null) {
            throw new IllegalArgumentException("Node already has a left child.");
        }
        Node<E> newNode = createNode(value, node, null, null);
        node.setLeft(newNode);
        size++;
		return newNode;
    }

    @Override
    public Position<E> addRight(Position<E> p, E value) {
    	Node<E> node = validate(p);
        if (right(node) != null) {
            throw new IllegalArgumentException("Node already has a right child.");
        }
        Node<E> newNode = createNode(value, node, null, null);
        node.setRight(newNode);
        size++;
		return newNode;
    }

    @Override
    public Position<E> root() {
        return root;
    }

    @Override
    public Position<E> parent(Position<E> p) {
        Node<E> node = validate(p);
        return node.getParent();
    }

    @Override
    public Position<E> addRoot(E value) {
        if (root() != null) {
            throw new IllegalArgumentException("The tree already has a root.");
        }
        this.root = createNode(value, null, null, null);
        size++;
        return root;
    }

    @Override
    public E remove(Position<E> p) {
        if (numChildren(p) == 2){
            throw new IllegalArgumentException("The node has two children");
        }
        Node<E> node = validate(p);
        E removedElement = p.getElement();
        if (size() == 1) {
        	this.root = null;
        	size--;
        	return removedElement;
        }
        if (this.isRoot(p)) {
        	if (node.getLeft() != null) {
        		this.root = node.getLeft();
        		node.getLeft().setParent(null);
        	}
        	if (node.getRight() != null) {
        		this.root = node.getRight();
        		node.getRight().setParent(null);
        	}
        	size--;
        	return removedElement;
        }
        if (numChildren(p) == 1) {
        	if (node.getLeft() != null) { //check if the child is a left 
        		node.getLeft().setParent(node.getParent());
        		if (node.getParent().getLeft() == node) { //check if removed was a left
            		node.getParent().setLeft(node.getLeft());
            	} else {
            		node.getParent().setRight(node.getLeft());
            	}
        	}
        	if (node.getRight() != null) { //check if the child is a right
        		node.getRight().setParent(node.getParent());
        		if (node.getParent().getLeft() == node) { //check if removed was a left
            		node.getParent().setLeft(node.getRight());
            	} else {
            		node.getParent().setRight(node.getRight());
            	}
        	}
        } else {
        	if (node.getParent().getLeft() == node) { //check if removed was a left
        		node.getParent().setLeft(null);
        	} else {
        		node.getParent().setRight(null);
        	}
        }
        size--;
        return removedElement;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Creates a node
     * @param e Element to store
     * @param parent Parent node
     * @param left Left node
     * @param right Right node
     * @return Node created
     */
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        Node<E> newNode = new Node<E>(e);
        newNode.setParent(parent);
        newNode.setLeft(left);
        newNode.setRight(right);
        return newNode;
    }

    // setRoot is needed for a later lab...
    // ...but THIS DESIGN IS BAD! If a client arbitrarily changes
    // the root by using the method, the size may no longer be correct/valid.
    // Instead, the precondition for this method is that
    // it should *ONLY* be used when rotating nodes in 
    // balanced binary search trees. We could instead change
    // our rotation code to not need this setRoot method, but that
    // makes the rotation code messier. For the purpose of this lab,
    // we will sacrifice a stronger design for cleaner/less code.
    /**
     * Sets the root
     * @param p Position to set
     * @return Position of node
     */
    protected Position<E> setRoot(Position<E> p) {
        root = validate(p);
        return root;
    }

    /**
     * Node of tree
     * @author griffin
     *
     * @param <E> a generic object
     */
	public static class Node<E> extends AbstractNode<E> {
		
		/** Parent of this node */
	    private Node<E> parent;
	    
	    /** Left of this node */
	    private Node<E> left;
	    
	    /** Right of this node */
	    private Node<E> right;
	
	    /**
	     * Creates a node
	     * @param element Element to hold
	     */
	    public Node(E element) {
	        this(element, null);
	    }
	
	    /**
	     * Creates a node with a specified parent
	     * @param element Element to hold
	     * @param parent Parent of this node
	     */
	    public Node(E element, Node<E> parent) {
	        super(element);
	        setParent(parent);
	    }
	
	    /**
	     * Gets the left of this node
	     * @return Left node
	     */
	    public Node<E> getLeft() {
	        return left;
	    }
	
	    /**
	     * Gets the right of this node
	     * @return Right node
	     */
	    public Node<E> getRight() {
	        return right;
	    }
	
	    /**
	     * Sets the left of this node
	     * @param left Left of this node
	     */
	    public void setLeft(Node<E> left) {
	        this.left = left;
	    }
	
	    /**
	     * Sets the right of this node
	     * @param right Right of this node
	     */
	    public void setRight(Node<E> right) {
	        this.right = right;
	    }
	
	    /**
	     * Gets the parent of this node
	     * @return Parent node
	     */
	    public Node<E> getParent() {
	        return parent;
	    }
	
	    /**
	     * Sets the parent of this node
	     * @param parent Parent of this node
	     */
	    public void setParent(Node<E> parent) {
	        this.parent = parent;
	    }
	}
}