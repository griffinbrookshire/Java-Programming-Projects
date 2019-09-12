/**
 * 
 */
package edu.ncsu.csc216.transit.airport.travelers;

import java.awt.Color;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.simulation_utils.Reporter;

/**
 * Represents a Passenger in the airport. POJO that holds data of generic passenger.
 * 
 * @author Griffin Brookshire
 */
public abstract class Passenger {
	/** minimum time to process any passenger */
	public static final int MIN_PROCESS_TIME = 20;
	/** time that the passenger joins a security line */
	private int arrivalTime;
	/** time the passenger will spend waiting in a security line */
	private int waitTime = 0;
	/** time that the passenger spends at the front of the security line */
	private int processTime;
	/** index of the security checkpoint line of the passenger */
	private int lineIndex = -1;
	/** tells if the passenger has joined a line yet */
	private boolean waitingProcessing = false;
	/** reporting mechanism for passenger */
	private Reporter myLog;

	/**
	 * Constructs a generic Passenger with given arrivalTime and processTime.
	 * 
	 * @param arrivalTime time that the passenger joins a security line
	 * @param processTime time spent at front of the security line
	 * @param log Reporter used to log data
	 */
	public Passenger(int arrivalTime, int processTime, Reporter log) {
		this.arrivalTime = arrivalTime;
		if (processTime < MIN_PROCESS_TIME) {
			throw new IllegalArgumentException();
		} else {
			this.processTime = processTime;
		}
		myLog = log;
	}

	/**
	 * Gets the arrival time
	 * 
	 * @return arrivalTime time that the passenger joins a security line
	 */
	public int getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * Gets the wait time
	 * 
	 * @return waitTime time the passenger will spend in a security line
	 */
	public int getWaitTime() {
		return waitTime;
	}

	/**
	 * Sets the wait time
	 * 
	 * @param waitTime time the passenger will spend in a security line
	 */
	public void setWaitTime(int waitTime) {
		waitingProcessing = true;
		this.waitTime = waitTime;
	}

	/**
	 * Gets the process time
	 * 
	 * @return waitTime time spent at front of the security line
	 */
	public int getProcessTime() {
		return processTime;
	}

	/**
	 * Gets the lineIndex
	 * 
	 * @return lineIndex index of the security checkpoint line of the passenger
	 */
	public int getLineIndex() {
		return lineIndex;
	}

	/**
	 * Gets waitingProcessing
	 * 
	 * @return waitingProcessing tells if the passenger has joined a line yet
	 */
	public boolean isWaitingInSecurityLine() {
		return waitingProcessing;
	}
	
	/**
	 * Logs the passengers data
	 */
	public void clearSecurity() {
		waitingProcessing = false;
		myLog.logData(this);
	}

	/**
	 * Sets the lineIndex
	 * 
	 * @param lineIndex index of the checkpoint in the security area
	 */
	protected void setLineIndex(int lineIndex) {
		this.lineIndex = lineIndex;
	}

	/**
	 * Puts this passenger in the correct line
	 * 
	 * @param t Transit group of passenger
	 */
	public abstract void getInLine(TransitGroup t);
	
	/**
	 * Gets the color of this passenger
	 * 
	 * @return color Passengers color
	 */
	public abstract Color getColor();

}
