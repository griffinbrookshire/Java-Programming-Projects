/**
 * 
 */
package edu.ncsu.csc216.wolf_results.race_results;

import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;

import edu.ncsu.csc216.wolf_results.util.ArrayList;

/**
 * A RaceList has an ArrayList of Races that will hold Race objects.
 * @author Sammy Penninger, Griffin Brookshire
 */
public class RaceList extends Observable implements Observer {
	
	/** Holds the races */
	private ArrayList races;
	
	/**
	 * Constructs the ArrayList
	 */
	public RaceList() {
		races = new ArrayList();
		notifyObservers(this);
	}

	/**
	 * Adds a race to the ArrayList. 
	 * Observer is added for the race. Observers of RaceList are notified of the change.
	 * @param race the Race that is added to the ArrayList
	 * @throws IllegalArgumentException if race is null.
	 */
	public void addRace(Race race) {
		if (race == null) {
			throw new IllegalArgumentException();
		}
		races.add(race);
		race.addObserver(this); //Observer is added for the race.
		setChanged();
		notifyObservers(this); //Observers of RaceList are notified of the change.
	}
	
	/**
	 * The method constructs a Race using the provided parameters and adds race to list
	 * Observer is added for the race. Observers of RaceList are notified of the change.
	 * @param name the name of the race
	 * @param distance the distance of the race
	 * @param date the date of the race
	 * @param location the location of the race
	 * @throws IllegalArgumentException if Race constructor throws exception
	 */
	public void addRace(String name, double distance, LocalDate date, String location) {
		addRace(new Race(name, distance, date, location));
		}
	
	
	/**
	 * Removes race at index. Observers of RaceList are notified of the change.
	 * @param index the index of the array list
	 */
	public void removeRace(int index) {
		if (index < 0 || index > races.size()) {
			throw new IndexOutOfBoundsException();
		}
		races.remove(index);
		//Observers of RaceList are notified of the change.
		this.setChanged();
		this.notifyObservers(this);
	}
	
	/**
	 * Gets race at index 
	 * @param index the index of the race
	 * @throws IndexOutOfBoundsException if index is out of bounds
	 * @return the race at the given index
	 */
	public Race getRace(int index) {
		if (index < 0 || index > races.size()) {
			throw new IndexOutOfBoundsException();
		}
		return (Race) races.get(index);
	}
	
	/**
	 * Returns number of races in list.
	 * @return the number of races in list
	 */
	public int size() {
		return races.size();
	}
	
	/**
	 * This method is called if the Race that the RaceList is observing notified its observers of a change. 
	 * If the Observable is an instance of Race, then the observers of the RaceList should be updated. 
	 * The current instance is passed to notifyObservers().
	 * @param o the Observed object
	 * @param arg the Object that is updated
	 */
	public void update(Observable o, Object arg) {
		setChanged();
		notifyObservers(arg);
	}
	
	
}