/**
 * 
 */
package edu.ncsu.csc216.wolf_results.manager;

import java.util.Observable;
import java.util.Observer;

import edu.ncsu.csc216.wolf_results.model.io.WolfResultsReader;
import edu.ncsu.csc216.wolf_results.race_results.RaceList;

/**
 * WolfResultsManager is a singleton that maintains the RaceList, reads from, and writes to the *.md files for the WolfResults application. 
 * The WolfResultsManager has a RaceList , the last used filename, and if the list has changed.
 * @author Sammy Penninger, Griffin Brookshire
 */
public class WolfResultsManager extends Observable implements Observer {
	/** The name of the file */
	private String filename;
	/** A boolean variable that determines if the list of Races has changed */
	private boolean changed;
	/** Instance variable of the WolfResultsManager class */
	private static WolfResultsManager instance;
	/** An array list of races */
	private RaceList list;

	/**
	 * Gets an instance of the WolfResultsManager class
	 * @return the instance
	 */
	public static WolfResultsManager getInstance() {
		if (instance == null) {
			instance = new WolfResultsManager();
		}
		return instance;
	}

	/**
	 * The private constructor for the WolfResultsManager class
	 */
	private WolfResultsManager() {
		list = new RaceList();
		list.addObserver(this);
		notifyObservers(this);
	}

	/**
	 * Constructs a new list
	 */
	public void newList() {
		list = new RaceList();
		list.addObserver(this);
		notifyObservers(this);
		setChanged(false);
		

	}

	/**
	 * Determines if the list of Races has changed
	 * @return true if the list has changed, false otherwise
	 */
	public boolean isChanged() {
		return changed;
	}

	/**
	 * Sets the boolean variable to the appropriate value based on the lists status
	 * @param value the boolean value that is changed
	 */
	private void setChanged(boolean value) {
		this.changed = value;
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Gets the name of the file
	 * @return the file name
	 */
	public String getFilename() {
		return this.filename;
	}

	/**
	 * Sets the name of the file
	 * @param filename the name of the file
	 */
	public void setFilename(String filename) {
		if (filename == null || filename.trim().equals("")) {
			throw new IllegalArgumentException();
		}
		this.filename = filename.trim();
	}

	/**
	 * Loads the file that is passed in
	 * @param filename the file that is loaded
	 */
	public void loadFile(String filename) {
		list = WolfResultsReader.readRaceListFile(filename);
		list.addObserver(this);
		setFilename(filename);
		setChanged(true);
	}

	/**
	 * Saves the file that is passed in
	 * @param filename the file that is saved
	 */
	public void saveFile(String filename) {
		this.filename = filename.trim();
		this.changed = false;
	}

	/**
	 * Gets the list of races
	 * @return raceList the list of races
	 */
	public RaceList getRaceList() {
		return list;
	}

	/**
	 * Propagates the notification of the change to its Observers
	 * @param o the Observable
	 * @param object the Object
	 */
	public void update(Observable o, Object object) {
		this.setChanged(true);
	}

}