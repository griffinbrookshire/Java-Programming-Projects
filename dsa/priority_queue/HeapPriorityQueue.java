package edu.ncsu.csc316.dsa.priority_queue;

import java.util.Comparator;

import edu.ncsu.csc316.dsa.list.ArrayBasedList;

/**
 * An implementation of a heap priority queue
 * @author griffin
 *
 * @param <K> Key of the entry
 * @param <V> Value of the entry
 */
public class HeapPriorityQueue<K extends Comparable<K>, V> extends AbstractPriorityQueue<K, V> {

    // Remember that heaps can be easily implemented using an internal array representation
    // versus a linked representation.
	/** List of the entries */
    protected ArrayBasedList<Entry<K, V>> list;

    /**
     * Creates a new HPQ with given comparator
     * @param comparator Comparator to use
     */
    public HeapPriorityQueue(Comparator<K> comparator) {
        super(comparator);
        list = new ArrayBasedList<Entry<K, V>>();
    }

    /**
     * Creates a new HPQ using natural order
     */
    public HeapPriorityQueue() {
        this(null);
    }
    
    //////////////////////////////////////////////////
    // Convenience methods to help abstract the math
    // involved in jumping to parent or children
    //////////////////////////////////////////////////
    
    /**
     * Gets the parent of the entry at index
     * @param index Index to get parent of 
     * @return Parent at index
     */
    protected int parent(int index) {
        return (index - 1) / 2;
    }

    /**
     * Gets the left of the entry at the index
     * @param index Index to get the left of 
     * @return Left of the index
     */
    protected int left(int index) {
        return 2 * index + 1;
    }

    /**
     * Gets the right of the entry at the index
     * @param index Index to get the right of 
     * @return Right of the index
     */
    protected int right(int index) {
        return 2 * index + 2;
    }

    /**
     * True if index has left, else false
     * @param index Index to check if has left
     * @return True if yes, else false
     */
    protected boolean hasLeft(int index) {
        return left(index) < list.size();
    }

    /**
     * True if index has right, else false
     * @param index Index to check if has right
     * @return True if yes, else false
     */
    protected boolean hasRight(int index) {
        return right(index) < list.size();
    }

    //////////////////////////////////////////
    // ADT Operations
    //////////////////////////////////////////
    
    /**
     * Got this code from textbook page 378
     */
    @Override
    public Entry<K, V> insert(K key, V value) {
        Entry<K, V> temp = createEntry(key, value);
        list.addLast(temp);
        upHeap(list.size() - 1);
		return temp;
    }

    /**
     * Got this code from textbook page 378
     */
    @Override
    public Entry<K, V> min() {
        if (list.isEmpty()) {
            return null;
        }
		return list.get(0);
    }

    /**
     * Got this code from textbook page 378
     */
    @Override
    public Entry<K, V> deleteMin() {
        if (list.isEmpty()) {
            return null;
        }
        Entry<K, V> a = list.get(0);
        swap(0, list.size() - 1);
        list.remove(list.size() - 1);
        downHeap(0);
		return a;
    }

    @Override
    public int size() {
        return list.size();
    }

    //////////////////////////////////////////////
    // Bubbling Operations (up heap, down heap)
    //////////////////////////////////////////////
    
    /**
     * Moves the entry at index higher, if necessary to restore heap property
     * 
     * Got this code from textbook page 378
     * 
     * @param index Index to move
     */
    protected void upHeap(int index) {
        while (index > 0) {
        	int p = parent(index);
        	if (compare(list.get(index).getKey(), list.get(p).getKey()) >= 0) {
        		break;
        	}
        	swap(index, p);
        	index = p;
        }
    }

    /**
     * Moves the entry at index lower, if necessary to restore the heap property.
     * 
     * Got this code from textbook page 378
     * 
     * @param index Index to move
     */
    protected void downHeap(int index) {
        while (hasLeft(index)) {
        	int leftIndex = left(index);
        	int smallChild = leftIndex;
        	if (hasRight(index)) {
        		int rightIndex = right(index);
        		if (compare(list.get(leftIndex).getKey(), list.get(rightIndex).getKey()) > 0) {
        			smallChild = rightIndex;
        		}
        	}
        	if (compare(list.get(smallChild).getKey(), list.get(index).getKey()) >= 0) {
        		break;
        	}
        	swap(index, smallChild);
        	index = smallChild;
        }
    }
    
    /**
     * Swaps the entries at indexes
     * @param index1 First index
     * @param index2 Second index
     */
    protected void swap(int index1, int index2) {
        Entry<K, V> temp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, temp);
    }
}