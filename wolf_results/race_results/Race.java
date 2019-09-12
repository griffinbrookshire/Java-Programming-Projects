/**
 * 
 */
package edu.ncsu.csc216.wolf_results.race_results;

import java.time.LocalDate;
import java.util.Observable;

/**
 * This class is a representation of a Race in the WolfResultsManager application
 * @author Sammy Penninger, Griffin Brookshire
 */
public class Race extends Observable {

	/** The name of the race */
	private String name;
	/** The distance of the race */
	private double distance;
	/** The date of the race */
	private LocalDate date;
	/** The location of the race */
	private String location;
	/** Instance of RaceResultList, as a list */
	private RaceResultList results;

	/**
	 * The first constructor, which includes a list of race results
	 * @throws IllegalArgumentException if name is null, empty string, or all whitespace; 
	 * distance is non-positive; date is null; location is null; or results is null. 
	 * @param name the race name
	 * @param distance the race distance
	 * @param date the race date
	 * @param location the race location
	 * @param results the array list of race results
	 */
	public Race(String name, double distance, LocalDate date, String location, RaceResultList results) {
		if (name == null || name.trim().equals("") || distance <= 0 || date == null || location == null || results == null) {
			throw new IllegalArgumentException();
		}
		this.name = name.trim();
		this.distance = distance;
		this.date = date;
		this.location = location.trim();
		this.results = results;
	}

	/**
	 * The second constructor. Results should be assigned a new RaceResultList.
	 * @throws IllegalArgumentException as stated in the previous constructor
	 * @param name the race name
	 * @param distance the race distance
	 * @param date the race date
	 * @param location the race location
	 */
	public Race(String name, double distance, LocalDate date, String location) {
		if (name == null || name.trim().equals("") || distance <= 0 || date == null || location == null) {
			throw new IllegalArgumentException();
		}
		this.name = name.trim();
		this.distance = distance;
		this.date = date;
		this.location = location.trim();
		results = new RaceResultList();
	}

	/**
	 * Returns the race name
	 * @return the race name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the race distance
	 * @return the race distance
	 */
	public double getDistance() {
		return this.distance;
	}

	/**
	 * Returns the race date
	 * @return the race date
	 */
	public LocalDate getDate() {
		return this.date;
	}

	/**
	 * Returns the race location
	 * @return the race location
	 */
	public String getLocation() {
		return this.location;
	}

	/**
	 * Returns the list of race results
	 * @return the list of race results
	 */
	public RaceResultList getResults() {
		return this.results;
	}

	/**
	 * Add result to results. Observers of Race are notified of the change.
	 * @param i the individual result
	 */
	public void addIndividualResult(IndividualResult i) {
		results.addResult(i);
		// Observers of Race are notified of the change.
		this.setChanged();
		this.notifyObservers(this);
	}

	/**
	 * Set distance field to the parameter. Observers of Race are notified of the change.
	 * @throws IllegalArgumentException if distance is non-positive
	 * @param distance the race distance
	 */
	public void setDistance(double distance) {
		if (distance <= 0) {
			throw new IllegalArgumentException();
		}
		this.distance = distance;
		// Observers of Race are notified of the change.
		this.setChanged();
		this.notifyObservers(this);
	}

	/**
	 * The string representation of a Race is the name, distance, date, and location fields.
	 * @return the string representation of the race
	 */
	public String toString() {
		return getName() + " (" + getDistance() + " miles) on " + getDate() + " at " + getLocation();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		long temp;
		temp = Double.doubleToLongBits(distance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		//result = prime * result + ((results == null) ? 0 : results.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Race))
			return false;
		Race other = (Race) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (Double.doubleToLongBits(distance) != Double.doubleToLongBits(other.distance))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (results == null && other.results != null) {
			return false;
		}
		return true;
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
		return results.filter(minAge, maxAge, minPace, maxPace);
	}

}