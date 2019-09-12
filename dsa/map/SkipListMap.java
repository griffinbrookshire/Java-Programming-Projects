package edu.ncsu.csc316.dsa.map;

import java.util.Comparator;
import java.util.Random;

import edu.ncsu.csc316.dsa.list.ArrayBasedList;

/**
 * A map implemented with a skip list
 * @author griffin
 *
 * @param <K> Key of entry
 * @param <V> Value of entry
 */
public class SkipListMap<K extends Comparable<K>, V> extends AbstractSortedMap<K, V> {

	/** Random object to mimic coin toss */
    private Random coinToss;
    
    /** Start of list */
    private SkipListEntry<K, V> start;
    
    /** Size of list */
    private int size;
    
    /** Height of list */
    private int height;

    /**
     * Creates a map
     */
    public SkipListMap() {
        this(null);
    }

    /**
     * Creates a map with the given comparator
     * @param compare Comaprator to use
     */
   public SkipListMap(Comparator<K> compare) {
        super(compare);
        coinToss = new Random();        
        // Create a dummy node for the left "-INFINITY" sentinel tower
        start = new SkipListEntry<K, V>(null, null);
        // Create a dummy node for the right "+INFINITY" sentinel tower
        start.setNext(new SkipListEntry<K, V>(null, null));
        // Set the +INFINITY tower's previous to be the "start" node
        start.getNext().setPrevious(start);
        size = 0;
        height = 0;
        coinToss = new Random();
    }
    
    // Helper method to determine if an entry is one of the sentinel
    // -INFINITY or +INFINITY nodes (containing a null key)
   /**
    * Tells if the node is a sentinel node
    * @param entry Entry to check
    * @return true if it is, false if not
    */
    private boolean isSentinel(SkipListEntry<K, V> entry) {
        return entry.getKey() == null;
    }

    /**
     * Finds the entry with the given key
     * @param key Key to find
     * @return The entry with the given key
     */
    private SkipListEntry<K, V> lookUp(K key) {
        SkipListEntry<K, V> current = start;
        while (current.below != null) {
            current = current.below;
            while (!isSentinel(current.next) && compare(key, current.next.getKey()) >= 0) {
                current = current.next;
            }
        }
        return current;
    }

    @Override
    public V get(K key) {
        SkipListEntry<K, V> temp = lookUp(key);
        return temp.getValue();
    }

    /**
     * Inserts the entry after and above
     * @param prev Previous node
     * @param down Below node
     * @param key Key of entry
     * @param value Value of entry
     * @return the new added entry
     */
    private SkipListEntry<K, V> insertAfterAbove(SkipListEntry<K, V> prev, SkipListEntry<K, V> down, K key, V value) {
    	/**
    	 * Algorithm insertAfterAbove(prev, down, k, v)
Input a key-value pair, k and v
      the entry that should be previous
      the entry that should be down
Output a new entry with (k,v) and pointers to the 'down' and 'prev' entries
    // Create a new skip list entry
    newEntry <-- new skip list entry
    // Set the down and previous entries
    newEntry.setBelow(down)
    newEntry.setPrevious(prev)
    // Update the next and previous entry pointers
    if prev(newEntry) is NOT null then
        newEntry.setNext( next(prev) )
        prev(newEntry).setNext( newEntry )
    if next(newEntry) is NOT null then
        next(newEntry).setPrevious(newEntry)
    // Update the down entry pointers
    if down is NOT null then
       down.setAbove(newEntry)
    return newEntry
    	 */
    	SkipListEntry<K, V> newEntry = new SkipListEntry<K, V>(key, value);
    	newEntry.setBelow(down);
        newEntry.setPrevious(prev);
        if (newEntry.getPrevious() != null) {
        	newEntry.setNext(prev.getNext());
        	newEntry.getPrevious().setNext(newEntry);
        }
        if (newEntry.getNext() != null) {
        	newEntry.getNext().setPrevious(newEntry);
        }
        if (down != null) {
        	down.setAbove(newEntry);
        }
        return newEntry;
    }

    @Override
    public V put(K key, V value) {
    	/**
    	 * Algorithm insert(k, v)
Input a key, k, and associated value, v
Output the topmost entry inserted into the skip list
    // Get the bottom-most entry immediately before the insertion location
    p <-- lookUp(k)
    // Entry with the key already exists in the map
    if key(p) == k then
        originalValue <-- value(p)
        while p is not null do
            p.setValue(value)
            p <-- above(p)
        return originalValue
    // Use q to represent the new entry as we move to the level "above" after inserting into the bottom-most list
    q <-- null
    // Keep track of the current level we are at
    currentLevel <-- -1
    repeat
        currentLevel <-- currentLevel + 1
        // Check if we need to add a new level to the top of the skip list
        if currentLevel >= height
            // Increase the height of the skip list
            height <-- height + 1
            // Create a pointer to the current "tail" of the topmost list
            tail <-- next(start)
            // Insert a new start node above
            start <-- insertAfterAbove(null, start, -INF, null)
            // Insert a new tail node above
            insertAfterAbove(start, tail, +INF, null)
        // Insert the new entry into current level of the list
        q <-- insertAfterAbove(p, q, k, v)
        // Backtrack to the entry immediately before the insertion location in the level "above"
        while above(p) is NULL do
            p <-- prev(p)
        p <-- above(p)          
    while coinFlip is tails, repeat
    size <-- size + 1
    return null
    	 */
    	
        SkipListEntry<K, V> p = lookUp(key);
        if (p.getKey() != null && compare(p.getKey(), key) == 0) {
        	V originalValue = p.getValue();
        	while (p != null) {
        		p.setValue(value);
        		p = p.getAbove();
        	}
        	return originalValue;
        }
        SkipListEntry<K, V> q = null;
        int currentLevel = -1;
        do {
        	currentLevel++;
        	if (currentLevel >= height) {
        		height++;
        		SkipListEntry<K, V> tail = start.getNext();
        		this.start = insertAfterAbove(null, this.start, null, null);
        		insertAfterAbove(start, tail, null, null);
        	}
        	q = insertAfterAbove(p, q, key, value);
        	while (p.getAbove() == null) {
        		p = p.getPrevious();
        	}
        	p = p.getAbove();
        } while (coinToss.nextBoolean());
        size++;
        return null;
    }

    @Override
    public V remove(K key) {
        SkipListEntry<K, V> temp = lookUp(key);
        if (temp.getKey() != key) { //if the key isn't in the list
        	return null;
        }
        V removed = temp.getValue(); //set return value
//        boolean deletedHighest = false;
//        if (temp.getPrevious().getKey() == null && temp.getNext().getKey() == null) {
//        	deletedHighest = true; //if temp has null prev and next then it was the only element in a list
//        }						// so should decrement height
////        for (int i = 0; i < height; i++) {
////        	temp.getPrevious().setNext(temp.getNext()); //set prev.next to temp.next = delete current node
////        	temp.getNext().setPrevious(temp.getPrevious()); //set next.prev to temp.prev
////        	temp = temp.getBelow(); //move down a level
////        }
//        if (deletedHighest) {
//        	height--;
//        }
        size--;
        return removed;
    }

    @Override
    public int size() {
        return size;
    }
    
    @Override
    public Iterable<Entry<K, V>> entrySet() {
        ArrayBasedList<Entry<K, V>> set = new ArrayBasedList<Entry<K, V>>();
        SkipListEntry<K, V> current = start;
        while(current.below != null){
            current = current.below;
        }
        current = current.next;
        while(!isSentinel(current)) {
            set.addLast(current);
            current = current.next;
        }
        return set;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + "[");
        SkipListEntry<K, V> cursor = start;
        while( cursor.below != null) {
            cursor = cursor.below;
        }
        cursor = cursor.next;
        while(cursor != null && cursor.getKey() != null) {
            sb.append(cursor.getKey());
            if(cursor.next != null && cursor.next.getKey() != null) {
                sb.append(", ");
            }
            cursor = cursor.next;
        }
        sb.append("]");
        
        return sb.toString();
    }
    
    // This method may be useful for testing or debugging.
    // You may comment-out this method instead of testing it, since
    // the full string will depend on the series of random coin flips
    // and will not have deterministic expected results.
//    /**
//     * Creates a full string representation of a map
//     * @return String representation of the map
//     */
//    public String toFullString() {
//        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName() + "[\n");
//        SkipListEntry<K, V> cursor = start;
//        SkipListEntry<K, V> firstInList = start;
//        while( cursor != null) {
//            firstInList = cursor;
//            sb.append("-INF -> ");
//            cursor = cursor.next;
//            while(cursor != null && !isSentinel(cursor)) {
//                sb.append(cursor.getKey() + " -> ");
//                cursor = cursor.next;
//            }
//            sb.append("+INF\n");
//            cursor = firstInList.below;
//        }
//        sb.append("]");
//        return sb.toString();
//    }
    
    /**
     * An entry in the list
     * @author griffin
     *
     * @param <K> Key of the entry
     * @param <V> Value of the entry
     */
    private static class SkipListEntry<K, V> extends MapEntry<K, V> {

    	/** Entry above this */
        private SkipListEntry<K, V> above;
        
        /** Entry below this */
        private SkipListEntry<K, V> below;
        
        /** Previous entry */
        private SkipListEntry<K, V> prev;
        
        /** Next entry */
        private SkipListEntry<K, V> next;

        /**
         * Creates an entry
         * @param key Key of entry
         * @param value Value of entry
         */
        public SkipListEntry(K key, V value) {
            super(key, value);
            setAbove(null);
            setBelow(null);
            setPrevious(null);
            setNext(null);
        }
        
//        /**
//         * Gets the entry below this one
//         * @return Below entry
//         */
//        public SkipListEntry<K, V> getBelow() {
//            return below;
//        }
        
        /**
         * Gets the next entry
         * @return Next entry
         */
        public SkipListEntry<K, V> getNext() {
            return next;
        }
        
        /**
         * Gets the previous entry
         * @return Previous entry
         */
        public SkipListEntry<K, V> getPrevious() {
            return prev;
        }
        
        /**
         * Gets the above entry
         * @return Above entry
         */
        public SkipListEntry<K, V> getAbove() {
            return above;
        }
        
        /**
         * Sets the below entry
         * @param down The entry to put below
         */
        public void setBelow(SkipListEntry<K, V> down) {
            this.below = down;
        }
        
        /**
         * Sets the next entry
         * @param next The entry to set next
         */
        public void setNext(SkipListEntry<K, V> next) {
            this.next = next;
        }
        
        /**
         * Sets the previous entry
         * @param prev The entry to set previous
         */
        public void setPrevious(SkipListEntry<K, V> prev) {
            this.prev = prev;
        }
        
        /**
         * Sets the above entry
         * @param up The entry to set above
         */
        public void setAbove(SkipListEntry<K, V> up) {
            this.above = up;
        }
    }
}