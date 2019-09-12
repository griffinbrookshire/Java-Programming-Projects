/**
 * 
 */
package edu.ncsu.csc216.transit.airport.travelers;

import java.awt.Color;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.security.SecurityArea;
import edu.ncsu.csc216.transit.simulation_utils.Reporter;

/**
 * POJO that represents an ordinary passenger in the airport
 * 
 * @author Griffin Brookshire
 */
public class OrdinaryPassenger extends Passenger {
	/** Maximum expected process time of a FastTrack passenger */
	public static final int MAX_EXPECTED_PROCESS_TIME = 150;
	/** Color of passenger */
	private Color color = null;

	/**
	 * Constructs an ordinary passenger with given arrival time and wait time
	 * 
	 * @param arrivalTime arrival time of passenger
	 * @param processTime process time of passenger
	 * @param r Reporter to log data
	 */
	public OrdinaryPassenger(int arrivalTime, int processTime, Reporter r) {
		super(arrivalTime, processTime, r);
		if (getProcessTime() < (MAX_EXPECTED_PROCESS_TIME + MIN_PROCESS_TIME) / 2) {
			color = new Color(255, 153, 153);
		} else {
			color = new Color(255, 0, 0);
		}
	}

	/**
	 * Gets the color of this passenger
	 * 
	 * @return color Passengers color
	 */
	@Override
	public Color getColor() {
		return this.color;
	}

	/**
	 * Puts this passenger in the correct line
	 * 
	 * @param t Transit group of passenger
	 */
	@Override
	public void getInLine(TransitGroup t) {
		setLineIndex(pickLine(t));
	}

	/**
	 * Picks the correct line for this passenger
	 * 
	 * @param t Transit Group of passenger
	 * @return index of the line
	 */
	private int pickLine(TransitGroup t) {
		SecurityArea s = (SecurityArea)t;
		return s.shortestRegularLine();
	}

}
