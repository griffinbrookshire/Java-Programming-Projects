/**
 * 
 */
package edu.ncsu.csc216.transit.airport.travelers;

import java.awt.Color;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.security.SecurityArea;
import edu.ncsu.csc216.transit.simulation_utils.Reporter;

/**
 * POJO that represents a FastTrack passenger in the airport
 * 
 * @author Griffin Brookshire
 */
public class FastTrackPassenger extends Passenger {
	/** Maximum expected process time of a FastTrack passenger */
	public static final int MAX_EXPECTED_PROCESS_TIME = 330;
	/** Color of passenger */
	private Color color = null;
	
	/**
	 * Constructs a FastTrack passenger with given arrival time and wait time
	 * 
	 * @param arrivalTime arrival time of passenger
	 * @param processTime process time of passenger
	 * @param log Reporter to log data
	 */
	public FastTrackPassenger(int arrivalTime, int processTime, Reporter log) {
		super(arrivalTime, processTime, log);
		if (getProcessTime() < (MAX_EXPECTED_PROCESS_TIME + MIN_PROCESS_TIME) / 2) {
			color = new Color(153, 153, 255);
		} else {
			color = new Color(0, 0, 255);
		}
	}
	
	/**
	 * Gets the color of this passenger
	 * 
	 * @return color Passengers color
	 */
	@Override
	public Color getColor() {
		return color;
	}

	/**
	 * Puts this passenger in the correct line
	 * 
	 * @param t TransitGroup that the passenger belongs to
	 */
	@Override
	public void getInLine(TransitGroup t) {
		setLineIndex(pickLine(t));
	}
	
	/**
	 * Picks the correct line for this passenger
	 * 
	 * @return index of the line
	 */
	private int pickLine(TransitGroup t) {
		SecurityArea s = (SecurityArea)t;
		return s.shortestFastTrackLine();
	}

}
