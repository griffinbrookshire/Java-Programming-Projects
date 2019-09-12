package edu.ncsu.csc316.dsa.map;

import java.util.Iterator;

/**
 * Details functionality and data for a general Map
 * @author griffin
 *
 * @param <K> Key of entry
 * @param <V> Value of Entry
 */
public abstract class AbstractMap<K, V> implements Map<K, V> {

    @Override
	public boolean isEmpty() {
	    return size() == 0;
	}

	@Override
	public Iterator<K> iterator() {
	    return new KeyIterator(entrySet().iterator());
	}

	@Override
	public Iterable<V> values() {
	    return new ValueIterable();
	}

	/**
	 * An entry in a map
	 * @author griffin
	 *
	 * @param <K> Key of entry
	 * @param <V> Value of entry
	 */
    protected static class MapEntry<K, V> implements Entry<K, V> {

    	/** Key of entry */
        private K key;
        
        /** Value of entry */
        private V value;

        /**
         * Creates an entry
         * @param key Key of entry
         * @param value Value of entry
         */
        public MapEntry(K key, V value) {
            setKey(key);
            setValue(value);
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        /**
         * Sets the key to the given value
         * @param key The key to set to
         * @return the new key
         */
        public K setKey(K key) {
            this.key = key;
            return this.key;
        }

        @Override
        public V setValue(V value) {
            V original = this.value;
            this.value = value;
            return original;
        }
    }

    /**
     * Iterates to a key
     * @author griffin
     *
     */
    protected class KeyIterator implements Iterator<K> {

    	/** Holds the iterator */
        private Iterator<Entry<K, V>> it;
        
        /**
         * Creates the iterator
         * @param iterator Iterator to use
         */
        public KeyIterator(Iterator<Entry<K, V>> iterator) {
            it = iterator;
        }
        
        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public K next() {
            return it.next().getKey();
        }
        
        @Override
        public void remove() {
            it.remove();
        }
    }
    
    /**
     * Iterates to a value
     * @author griffin
     *
     */
    protected class ValueIterator implements Iterator<V> {
    	
    	/** Holds the iterator */
    	private Iterator<Entry<K, V>> it;
        
    	/**
         * Creates the iterator
         * @param iterator Iterator to use
         */
        public ValueIterator(Iterator<Entry<K, V>> iterator) {
            it = iterator;
        }

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public V next() {
			return it.next().getValue();
		}
		
		@Override
	    public void remove() {
			it.remove();
	    }
    }

    /**
     * Values that can be iterated to
     * @author griffin
     *
     */
    private class ValueIterable implements Iterable<V> {
    	
        @Override
        public Iterator<V> iterator() {
            return new ValueIterator(entrySet().iterator());
        }
    }
    
}