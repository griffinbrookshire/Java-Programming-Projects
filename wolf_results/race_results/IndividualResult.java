/**
 * 
 */
package edu.ncsu.csc216.wolf_results.race_results;

import java.util.Observable;
import java.util.Observer;

import edu.ncsu.csc216.wolf_results.util.RaceTime;

/**
 * Represents an individual race result
 * @author Sammy Penninger, Griffin Brookshire
 */
public class IndividualResult implements Comparable<IndividualResult>, Observer {
	/** Name of the runner */
	private String name;
	/** Age of the runner */
	private int age;
	/** Pace of the runner */
	private RaceTime pace;
	/** Runners race time */
	private RaceTime time;
	/** The Race the runner was in */
	private Race race;

	/**
	 * Constructs a race result, sets the pace field based on the time and race distance, and adds an observer for Race
	 * @param race the race
	 * @param name the name of the runner
	 * @param age the age of the runner
	 * @param time the time of the race
	 * @throws IllegalArgumentException if race is null; name is null, an empty string, or all whitespace; age is negative; or time is null
	 */
	public IndividualResult(Race race, String name, int age, RaceTime time) {
		if (race == null || name == null || name.trim().equals("") || age < 0 || time == null) {
			throw new IllegalArgumentException();
		}
		this.race = race;
		this.name = name.trim();
		this.age = age;
		this.time = time;
		this.pace = getPaceFromData(time, race.getDistance());
		//this.race.addObserver(this);
		race.addObserver(this);
	}
	
	/**
	 * Gets the race the runner belongs to
	 * @return race
	 */
	public Race getRace() {
		return race;
	}
	
	/**
	 * Gets the runners name
	 * @return race name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the runners age
	 * @return age of runner
	 */
	public int getAge() {
		return age;
	}
	
	/**
	 * Gets the runners time
	 * @return runners time
	 */
	public RaceTime getTime() {
		return time;
	}
	
	/**
	 * Gets the runners pace
	 * @return runners pace
	 */
	public RaceTime getPace() {
		return pace;
	}
	
	/**
	 * Compares two individual results
	 * @param other another individual result
	 * @return 1 if this pace is smaller, 0 otherwise
	 */
	public int compareTo(IndividualResult other) {
		if (this.getPace().getTimeInSeconds() > other.getPace().getTimeInSeconds()) {
			return 1;
		} else if (this.getPace().getTimeInSeconds() == other.getPace().getTimeInSeconds()) {
			return 0;
		}
		return -1;
	}
	
	/**
	 * Creates a string representation of this individual result
	 * @return the string representation of this individual result
	 */
	public String toString() {
		String name1;
		String age1;
		String time1;
		String pace1;
		name1 = "IndividualResult [name=" + this.getName() + ", ";
		age1 = "age=" + this.getAge() + ", ";
		time1 = "time=" + this.getTime() + ", ";
		pace1 = "pace=" + this.getPace() + "]";
		return name1 + age1 + time1 + pace1;
	}
	
	/**
	 * Updates the GUI
	 * @param o Observable object
	 * @param e Object to observe
	 */
	public void update(Observable o, Object e) {
		// still needs to be implemented
		if (o instanceof Race) {
			this.pace = getPaceFromData(this.time, this.race.getDistance());
		}
	}
	
	private RaceTime getPaceFromData(RaceTime time, double distance) {
		double totalTime = time.getTimeInSeconds() + 0.0;
		double totalPace = totalTime / distance;
		int hours = (int)(totalPace / (60 * 60));
		int minutes = (int)(((totalPace % 3600)) / 60);
		int seconds = (int)(((totalPace % 3600)) % 60);
		return new RaceTime(hours, minutes, seconds);
	}

}