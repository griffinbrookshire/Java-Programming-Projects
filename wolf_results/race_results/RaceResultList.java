/**
 * 
 */
package edu.ncsu.csc216.wolf_results.race_results;

import edu.ncsu.csc216.wolf_results.util.RaceTime;
import edu.ncsu.csc216.wolf_results.util.SortedLinkedList;

/**
 * List of race results
 * @author Sammy Penninger, Griffin Brookshire
 */
public class RaceResultList {
	/** Instance of the SortedLinkedList */
	private SortedLinkedList<IndividualResult> results;
	
	/**
	 * Constructs a RaceResultList
	 */
	public RaceResultList() {
		results = new SortedLinkedList<IndividualResult>();
	}
	
	/**
	 * Adds an existing IndividualResult to the list
	 * @param i IndividualResult to add
	 */
	public void addResult(IndividualResult i) {
		if (i == null) {
			throw new IllegalArgumentException();
		}
		results.add(i);
	}
	
	/**
	 * Creates and adds an IndividualResult
	 * @param race Race of runner 
	 * @param name Name of runner 
	 * @param age Age of runner 
	 * @param time Time of runner
	 */
	public void addResult(Race race, String name, int age, RaceTime time) {
		IndividualResult i = null;
		try {
			i = new IndividualResult(race, name, age, time);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		results.add(i);
	}
	
	/**
	 * Gets a result from the list
	 * @param idx index of result
	 * @return result at specified index
	 */
	public IndividualResult getResult(int idx) {
		if (idx < 0 || idx >= results.size()) {
			throw new IndexOutOfBoundsException();
		}
		return results.get(idx);
	}
	
	/**
	 * Number of results in the list
	 * @return size of list
	 */
	public int size() {
		return results.size();
	}
	
	/**
	 * Creates a 2-D String array of the list
	 * @return array representing the list
	 */
	public String[][] getResultsAsArray() {
		String[][] s = new String[results.size()][4];
		for (int i = 0; i < results.size(); i++) {
			s[i][0] = results.get(i).getName();
			s[i][1] = Integer.toString(results.get(i).getAge());
			s[i][2] = results.get(i).getTime().toString();
			s[i][3] = results.get(i).getPace().toString();
		}
		return s;
	}
	
	/**
	 * Returns list of results such that runners age is between minAge and maxAge (inclusive) 
	 * and runners pace is between minPace and maxPace (inclusive).
	 * @throws IllegalArgumentException if minPace or maxPace is not a valid RaceTime
	 * @param minAge the minimum age of a runner
	 * @param maxAge the maximum age of a runner
	 * @param minPace the minimum pace of a runner
	 * @param maxPace the maximum pace of a runner
	 * @return the filtered list of race results
	 */
	public RaceResultList filter(int minAge, int maxAge, String minPace, String maxPace) {
		RaceTime min = null;
		RaceTime max = null;
		try {
			min = new RaceTime(minPace);
			max = new RaceTime(maxPace);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		RaceResultList r = new RaceResultList();
		for (int i = 0; i < results.size(); i++) {
			if (results.get(i).getAge() >= minAge && results.get(i).getAge() <= maxAge && results.get(i).getPace().getTimeInSeconds() >= min.getTimeInSeconds() && results.get(i).getPace().getTimeInSeconds() <= max.getTimeInSeconds()) {
				r.addResult(results.get(i));
			}
		}
		return r;
	}

}