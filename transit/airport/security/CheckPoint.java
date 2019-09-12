/**
 * 
 */
package edu.ncsu.csc216.transit.airport.security;

import edu.ncsu.csc216.transit.airport.travelers.Passenger;
import edu.ncsu.csc216.transit.airport.travelers.PassengerQueue;

/**
 * A security CheckPoint in the SecurityArea. Holds passengers in a PassengerQueue 
 * until they are processed
 * 
 * @author Griffin Brookshire
 */
public class CheckPoint {
	/** time the last passenger in line will leave the line */
	private int timeWhenAvailable;
	/** holds the passengers at this checkpoint */
	private PassengerQueue line;

	/**
	 * Constructs a CheckPoint and initializes a PassengerQueue that represents the line for the checkpoint.
	 */
	public CheckPoint() {
		line = new PassengerQueue();
	}

	/**
	 * Gets the size of the line.
	 * 
	 * @return number of passengers in line at the checkpoint
	 */
	public int size() {
		return line.size();
	}

	/**
	 * Removes the passenger at the front of the line.
	 * 
	 * @return Passenger that was removed
	 */
	public Passenger removeFromLine() {
		Passenger p = line.remove();
		p.clearSecurity();
		return p;
	}

	/**
	 * Tells if there is another passenger in the line or not.
	 * 
	 * @return true if there is another passenger in line, false if not
	 */
	public boolean hasNext() {
		return !(line.isEmpty());
	}

	/**
	 * Gets the time next passenger will depart
	 * 
	 * @return time before the next passenger will clear security
	 */
	public int departTimeNext() {
		if (line.isEmpty()) {
			return Integer.MAX_VALUE;
		}
		return line.front().getProcessTime();
	}

	/**
	 * Gets the next passenger to go
	 * 
	 * @return Passenger at the front of the line
	 */
	public Passenger nextToGo() {
		return line.front();
	}

	/**
	 * Adds a passenger to the end of checkpoint line, sets its waitTime, change arrivalTime, processTime.
	 * 
	 * @param p Passenger being added to line
	 */
	public void addToLine(Passenger p) {
		if (!hasNext()) {
			p.setWaitTime(0);
			timeWhenAvailable = p.getArrivalTime() + p.getProcessTime();
		} else if (p.getArrivalTime() < timeWhenAvailable) {
			p.setWaitTime(timeWhenAvailable - p.getArrivalTime());
			timeWhenAvailable += p.getProcessTime();
		} else {
			p.setWaitTime(0);
			timeWhenAvailable += p.getProcessTime();
		}
		line.add(p);
	}

}
