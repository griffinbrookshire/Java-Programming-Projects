package edu.ncsu.csc316.dsa.queue;

import java.util.NoSuchElementException;

/**
 * A circular array based queue class
 * @author griffin
 *
 * @param <E> a generic object
 */
public class ArrayBasedQueue<E> extends AbstractQueue<E> {

	/** Holds the data of the queue */
    private E[] data;
    /** The index of the front item in the queue */
    private int front;
    /** The index immediately past the rear item in the queue */
    private int rear;
    /** The size of the queue */
    private int size;
    /** The capacity of the array */
    private int capacity;
    /** The default capacity of the queue */
    private static final int DEFAULT_CAPACITY = 10;
    
    /**
     * Creates a new queue with a specified capacity
     * @param initialCapacity The required capacity of the array
     */
    @SuppressWarnings("unchecked")
    public ArrayBasedQueue(int initialCapacity)
    {
        data = (E[])(new Object[initialCapacity]);
        size = 0;
        capacity = initialCapacity;
        front = capacity - 1;
        rear = front;
    }
    
    /**
     * Creates a new queue with the default capacity
     */
    public ArrayBasedQueue()
    {
        this(DEFAULT_CAPACITY);
    }
    
    @SuppressWarnings("unchecked")
	private void ensureCapacity(int minCapacity) {
    	E[] temp = (E[])(new Object[minCapacity]);
    	int idxOfData = front; 
    	int idxOfTemp = minCapacity - 1;
    	for (int i = 0; i < size; i++) {
    		temp[idxOfTemp] = data[idxOfData];
    		idxOfTemp--;
    		idxOfData--;
    		if (idxOfData == -1) {
    			idxOfData = capacity - 1;
    		}
    	}
    	this.capacity = minCapacity;
    	this.front = minCapacity - 1;
    	this.rear = capacity - size - 1;
    	data = temp;
    }

	@Override
	public void enqueue(E value) {
		if (size == capacity) {
			ensureCapacity(capacity * 2);
		}
		if (rear == -1) { // wrap the rear pointer once you run out of space
			rear = capacity - 1;
		}
		//data[rear] = value;
		int avail = (front + size) % data.length;
		data[avail] = value;
		rear--;
		size++;
	}

	@Override
	public E dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		E removed = data[front];
		//front--;
		front = (front + 1) % data.length;
		size--;
		return removed;
	}

	@Override
	public E front() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return data[front];
	}

	@Override
	public int size() {
		return size;
	}
    
}