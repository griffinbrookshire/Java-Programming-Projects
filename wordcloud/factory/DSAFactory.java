package edu.ncsu.csc316.wordcloud.factory;

import edu.ncsu.csc316.dsa.list.ArrayBasedList;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.SearchTableMap;
import edu.ncsu.csc316.dsa.map.UnorderedArrayMap;

/**
 * Factory for creating new data structure and algorithm instances
 * 
 * @author Dr. King
 *
 */
public class DSAFactory {

	/**
	 * Returns a data structure that implements an unordered map
	 * 
	 * @param <K> - the key type
	 * @param <V> - the value type
	 * @return a data structure that implements an unordered map
	 */
	public static <K, V> Map<K, V> getUnorderedMap() {
		// return getUnorderedLinkedMap();
		return getUnorderedArrayMap();
	}

	/**
	 * Returns a data structure that implements an ordered map
	 * 
	 * @param <K> - the key type
	 * @param <V> - the value type
	 * @return a data structure that implements an ordered map
	 */
//	public static <K extends Comparable<K>, V> Map<K, V> getOrderedMap() {
//		return getSkipListMap();
//	}

	/**
	 * Returns a data structure that implements an index-based list
	 * 
	 * @param <E> - the element type
	 * @return an index-based list
	 */
	public static <E> List<E> getIndexedList() {
		return getArrayBasedList();
	}

	/**
	 * Returns a data structure that implements an positional list
	 * 
	 * @param <E> - the element type
	 * @return a positional list
	 */
//	public static <E> PositionalList<E> getPositionalList() {
//		return getPositionalLinkedList();
//	}

	/**
	 * Returns a comparison based sorter
	 * 
	 * @param <E> - the element type
	 * @return a comparison based sorter
	 */
//	public static <E extends Comparable<E>> Sorter<E> getComparisonSorter() {
//		return getMergeSorter();
//	}

	/**
	 * Returns a non-comparison based sorter
	 * 
	 * @param <E> - the element type
	 * @return a non-comparison based sorter
	 */
//	public static <E extends Identifiable> Sorter<E> getNonComparisonSorter() {
//		return getRadixSorter();
//	}

	/**
	 * Returns a data structure that implements a stack
	 * 
	 * @param <E> - the element type
	 * @return a stack
	 */
//	public static <E> Stack<E> getStack() {
//		return getLinkedStack();
//	}

	/**
	 * Returns a data structure that implements a queue
	 * 
	 * @param <E> - the element type
	 * @return a stack
	 */
//	public static <E> Queue<E> getQueue() {
//		return getArrayBasedQueue();
//	}

	/**
	 * Returns an unordered array-based map
	 * 
	 * @return an unordered array-based map
	 */
	private static <K, V> UnorderedArrayMap<K, V> getUnorderedArrayMap() {
		return new UnorderedArrayMap<K, V>();
	}

	/**
	 * Returns an unordered linked map
	 * 
	 * @return an unordered linked map
	 */
//	private static <K, V> UnorderedLinkedMap<K, V> getUnorderedLinkedMap() {
//		return new UnorderedLinkedMap<K, V>();
//	}

	/**
	 * Returns a search table
	 * 
	 * @param <K> Key of entry
	 * @param <V> Value of entry
	 * @return a search table
	 */
	public static <K extends Comparable<K>, V> SearchTableMap<K, V> getSearchTableMap() {
		return new SearchTableMap<K, V>();
	}

//	/**
//	 * Returns a search table
//	 * 
//	 * @return a search table
//	 */
//	public static <K extends Comparable<K>, V> SearchTableMap<K, V> getSearchTableMapFreq() {
//		return new SearchTableMap<K, V>((Comparator<K>) new WordComparator());
//	}

	/**
	 * Returns a skip list map
	 * 
	 * @return a skip list map
	 */
//	private static <K extends Comparable<K>, V> SkipListMap<K, V> getSkipListMap() {
//		return new SkipListMap<K, V>();
//	}

	/**
	 * Returns an array-based list
	 * 
	 * @return an array-based list
	 */
	private static <E> ArrayBasedList<E> getArrayBasedList() {
		return new ArrayBasedList<E>();
	}

//	/**
//	 * Returns a singly linked list with front pointer
//	 * 
//	 * @return a singly linked list with front pointer
//	 */
//	private static <E> SinglyLinkedList<E> getSinglyLinkedList() {
//		return new SinglyLinkedList<E>();
//	}

	/**
	 * Returns a positional linked list with a front pointer
	 * 
	 * @return a positional linked list with a front pointer
	 */
//	private static <E> PositionalLinkedList<E> getPositionalLinkedList() {
//		return new PositionalLinkedList<E>();
//	}

	/**
	 * Returns a mergesorter
	 * 
	 * @return a mergesorter
	 */
//	private static <E extends Comparable<E>> Sorter<E> getMergeSorter() {
//		return new MergeSorter<E>();
//	}

	/**
	 * Returns a quicksorter
	 * 
	 * @return a quicksorter
	 */
//	private static <E extends Comparable<E>> Sorter<E> getQuickSorter() {
//		return new QuickSorter<E>();
//	}
//
//	/**
//	 * Returns an insertion sorter
//	 * 
//	 * @return an insertion sorter
//	 */
//	private static <E extends Comparable<E>> Sorter<E> getInsertionSorter() {
//		return new InsertionSorter<E>();
//	}
//
//	/**
//	 * Returns a selection sorter
//	 * 
//	 * @return a selection sorter
//	 */
//	private static <E extends Comparable<E>> Sorter<E> getSelectionSorter() {
//		return new SelectionSorter<E>();
//	}
//
//	/**
//	 * Returns a bubble sorter
//	 * 
//	 * @return a bubble sorter
//	 */
//	private static <E extends Comparable<E>> Sorter<E> getBubbleSorter() {
//		return new BubbleSorter<E>();
//	}
//
//	/**
//	 * Returns a counting sorter
//	 * 
//	 * @return a counting sorter
//	 */
//	private static <E extends Identifiable> Sorter<E> getCountingSorter() {
//		return new CountingSorter<E>();
//	}

	/**
	 * Returns a radix sorter
	 * 
	 * @return a radix sorter
	 */
//	private static <E extends Identifiable> Sorter<E> getRadixSorter() {
//		return new RadixSorter<E>();
//	}

	/**
	 * Returns a linked stack
	 * 
	 * @return a linked stack
	 */
//	private static <E> LinkedStack<E> getLinkedStack() {
//		return new LinkedStack<E>();
//	}

	/**
	 * Returns a linked queue
	 * 
	 * @return a linked queue
	 */
//	private static <E> ArrayBasedQueue<E> getArrayBasedQueue() {
//		return new ArrayBasedQueue<E>();
//	}
}