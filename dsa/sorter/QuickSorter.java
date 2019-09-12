package edu.ncsu.csc316.dsa.sorter;

import java.util.Comparator;
import java.util.Random;

/**
 * A comparison-based sorting algorithm
 * @author griffin
 * @param <E> generic object
 */
public class QuickSorter<E extends Comparable<E>> extends AbstractComparisonSorter<E> {

	/** Selects the first element */
	public static final PivotSelector FIRST_ELEMENT_SELECTOR = new FirstElementSelector();
	/** Selects the last element */
	public static final PivotSelector LAST_ELEMENT_SELECTOR = new LastElementSelector();
	/** Selects the middle element */
	public static final PivotSelector MIDDLE_ELEMENT_SELECTOR = new MiddleElementSelector();
	/** Selects a random element */
	public static final PivotSelector RANDOM_ELEMENT_SELECTOR = new RandomElementSelector();

	/** Holds the current selector */
	private PivotSelector selector;

	/**
	 * Creates a QuickSorter object with the specified comparator and selector
	 * @param comparator Comparator to use
	 * @param selector Selector to use
	 */
	public QuickSorter(Comparator<E> comparator, PivotSelector selector) {
		super(comparator);
		setSelector(selector);
	}

	/**
	 * Creates a QuickSorter object with the specified comparator
	 * @param comparator Comparator to use
	 */
	public QuickSorter(Comparator<E> comparator) {
		this(comparator, null);
	}    

	/**
	 * Creates a QuickSorter object with the specified selector
	 * @param selector Selector to use
	 */
	public QuickSorter(PivotSelector selector) {
		this(null, selector);
	}

	/**
	 * Creates a QuickSorter object with default comparator and selector
	 */
	public QuickSorter() {
		this(null, null);
	}

	private void setSelector(PivotSelector selector) {
		if(selector == null) {
			selector = new RandomElementSelector();
		}
		this.selector = selector;
	}

	@Override
	public void sort(E[] array) {
		quickSort(array, 0, array.length - 1);
	}
	
	private void quickSort(E[] array, int low, int high) {
		if (low < high) {
			int pivotLocation = partition(array, low, high);
			quickSort(array, low, pivotLocation - 1);
			quickSort(array, pivotLocation + 1, high);
		}
	}
	
	private int partition(E[] array, int low, int high) {
		int pivotIndex = selector.selectPivot(low, high);
		swap(array, pivotIndex, high);
		return partitionHelper(array, low, high);
	}
	
	private int partitionHelper(E[] array, int low, int high) {
		E pivot = array[high];
		int index = low;
		for (int j = low; j <= high - 1; j++) {
			if (compare(array[j], pivot) <= 0) {
				swap(array, index, j);
				index++;
			}
		}
		swap(array, index, high);
		return index;
	}
	
	private void swap(E[] array, int low, int high) {
		E temp = array[low];
		array[low] = array[high];
		array[high] = temp;
	}

	/**
	 * Defines behavior for selectors
	 * @author griffin
	 *
	 */
	private interface PivotSelector {
		
		/**
		 * Returns the index of the selected pivot element
		 * @param low - the lowest index to consider
		 * @param high - the highest index to consider
		 * @return the index of the selected pivot element
		 */
		int selectPivot(int low, int high);
	}

	/**
	 * Selects the first element given
	 * @author griffin
	 *
	 */
	private static class FirstElementSelector implements PivotSelector {

		/**
		 * Creates a FirstElementSelector
		 */
		public FirstElementSelector() {
			/**
			 * Nothing to put here
			 */
		}

		@Override
		public int selectPivot(int low, int high) {
			return low;
		}
	}

	/**
	 * Selects the middle element
	 * @author griffin
	 *
	 */
	private static class MiddleElementSelector implements PivotSelector {

		/**
		 * Creates a MiddleElementSelector
		 */
		public MiddleElementSelector() {
			/**
			 * Nothing to put here
			 */
		}

		@Override
		public int selectPivot(int low, int high) {
			return (low + high) / 2;
		}
	}

	/**
	 * Selects the last element
	 * @author griffin
	 *
	 */
	private static class LastElementSelector implements PivotSelector {

		/**
		 * Creates a LastElementSelector
		 */
		public LastElementSelector() {
			/**
			 * Nothing to put here
			 */
		}

		@Override
		public int selectPivot(int low, int high) {
			return high;
		}
	}

	/**
	 * Selects a random elemenet
	 * @author griffin
	 *
	 */
	private static class RandomElementSelector implements PivotSelector {

		/**
		 * Creates a RandomElementSelector
		 */
		public RandomElementSelector() {
			/**
			 * Nothing to put here
			 */
		}

		@Override
		public int selectPivot(int low, int high) {
			Random rand = new Random();
			return rand.nextInt(high - low) + low;
		}
	}
}