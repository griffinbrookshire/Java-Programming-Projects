/**
 * 
 */
package edu.ncsu.csc216.transit.simulation_utils;

import java.awt.Color;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.entrance.PreSecurity;
import edu.ncsu.csc216.transit.airport.entrance.Ticketing;
import edu.ncsu.csc216.transit.airport.security.SecurityArea;
import edu.ncsu.csc216.transit.airport.travelers.FastTrackPassenger;
import edu.ncsu.csc216.transit.airport.travelers.OrdinaryPassenger;
import edu.ncsu.csc216.transit.airport.travelers.Passenger;

/**
 * Sets up the Simulator and performs the steps
 * 
 * @author Griffin Brookshire
 */
public class Simulator {
	
	/** Total number of passengers in airport. */
	private int numPassengers;
	/** Passengers in ticketing */
	private TransitGroup inTicketing;
	/** Passengers in security */
	private TransitGroup inSecurity;
	/** Log to hold simulation data */
	private Reporter log;
	/** Passenger next to act */
	private Passenger currentPassenger = null;
	/** Tells which passenger is next to act */
	private EventCalendar e;

	/**
	 * Constructs a Simulator object with user-specified characteristics
	 * 
	 * @param numberOfCheckpoints number of checkpoints
	 * @param numberOfPassengers number of passengers
	 * @param trustPct percentage of trusted passengers
	 * @param fastPct percentage of fastTrack passengers
	 * @param ordPct percentage of ordinary passengers
	 */
	public Simulator(int numberOfCheckpoints, int numberOfPassengers, int trustPct, int fastPct, int ordPct) {
		checkParameters(numberOfCheckpoints, numberOfPassengers, trustPct, fastPct, ordPct);
		this.numPassengers = numberOfPassengers;
		this.log = new Log();
		this.inSecurity = new SecurityArea(numberOfCheckpoints);
		setUp(numPassengers, trustPct, fastPct);
		e = new EventCalendar(inTicketing, inSecurity);
	}
	
	/**
	 * Checks that the user inputed parameters are legal
	 * 
	 * @param numberOfCheckpoints number of checkpoints
	 * @param numberOfPassengers number of passengers
	 * @param trustPct percentage of trusted passengers
	 * @param fastPct percentage of fastTrack passengers
	 * @param ordPct percentage of ordinary passengers
	 * @throws IllegalArgumentException if inputs are illegal
	 */
	private void checkParameters(int numberOfCheckpoints, int numberOfPassengers, int trustPct, int fastPct, int ordPct) {
		if (numberOfPassengers < 1) {
			throw new IllegalArgumentException("There must be at least one passenger");
		}
		if (trustPct + fastPct + ordPct != 100 || trustPct < 0 || fastPct < 0 || ordPct < 0) {
			throw new IllegalArgumentException("Percents must sum to 100");
		}
		try {
			numberOfCheckpoints = (Integer) numberOfCheckpoints;
			numberOfPassengers = (Integer) numberOfPassengers;
			trustPct = (Integer) trustPct;
			fastPct = (Integer) fastPct;
			ordPct = (Integer) ordPct;
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Inputs must be integers");
		}
	}
	
	/**
	 * Sets up PreSecurity, Ticketing
	 * 
	 * @param numberOfPassengers the total number of passengers
	 * @param trustPct percent TrustedTraveler passengers
	 * @param fastPct percent FastTrack passengers
	 */
	private void setUp(int numberOfPassengers, int trustPct, int fastPct) {
		Ticketing.setDistribution(trustPct, fastPct);
		this.inTicketing = new PreSecurity(numberOfPassengers, log);
	}
	
	/**
	 * Gets the reporter from the simulator. 
	 * 
	 * @return log the reporter
	 */
	public Reporter getReporter() {
		return log;
	}
	
	/**
	 * Removes passenger from line if they are in line, removes from ticketing if not
	 */
	public void step() {
		Passenger p = e.nextToAct();
		if (p == null) {
			throw new IllegalStateException();
		}
		if (p.isWaitingInSecurityLine()) {
			currentPassenger = inSecurity.removeNext();
		} else {
			currentPassenger = inTicketing.removeNext();
			SecurityArea s = (SecurityArea) inSecurity;
			if (currentPassenger instanceof OrdinaryPassenger) {
				s.addToLine(s.shortestRegularLine(), currentPassenger);
			} else if (currentPassenger instanceof FastTrackPassenger) {
				s.addToLine(s.shortestFastTrackLine(), currentPassenger);
			} else {
				s.addToLine(s.shortestTSAPreLine(), currentPassenger);
			}
		}
	}
	
	/**
	 * Checks if more steps are needed
	 * 
	 * @return true if numPassengers does not equal 0, false if it does
	 */
	public boolean moreSteps() {
		return numPassengers != getReporter().getNumCompleted();
	}
	
	/**
	 * Gets the index of the checkpoint of the current passenger
	 * 
	 * @return index of checkpoint of currentPassenger
	 */
	public int getCurrentIndex() {
		return currentPassenger.getLineIndex();
	}
	
	/**
	 * Gets the color of the current passenger
	 * 
	 * @return color of currentPassenger
	 */
	public Color getCurrentPassengerColor() {
		return currentPassenger.getColor();
	}
	
	/**
	 * Gets if the current passenger has cleared security or not
	 * 
	 * @return true if currentPassenger has cleared security, false if not
	 */
	public boolean passengerClearedSecurity() { // currentPassenger must be past PreSecurity = either in line or cleared security
		if (currentPassenger == null || currentPassenger.isWaitingInSecurityLine()) {
			return false;
		}
		return true;
	}
	
 }
