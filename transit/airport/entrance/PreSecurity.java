/**
 * 
 */
package edu.ncsu.csc216.transit.airport.entrance;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.travelers.Passenger;
import edu.ncsu.csc216.transit.airport.travelers.PassengerQueue;
import edu.ncsu.csc216.transit.simulation_utils.Reporter;

/**
 * PreSecurity represents passengers in the terminal who 
 * have not yet joined a security checkpoint line.
 * 
 * @author Griffin Brookshire
 */
public class PreSecurity implements TransitGroup {
	
	/** Holds the passengers in PreSecurity */
	private PassengerQueue outsideSecurity;
	
	/**
	 * Constructs the PreSecurity which holds the passengers before they enter the security area
	 * 
	 * @param numPassengers total number of passengers
	 * @param log logs data from passengers
	 */
	public PreSecurity(int numPassengers, Reporter log) {
		try {
			numPassengers = (Integer) numPassengers;
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("There must be at least one passenger");
		}
		if (numPassengers <= 0) {
			throw new IllegalArgumentException("Number of passengers must be positive.");
		}
		outsideSecurity = new PassengerQueue();
		for (int i = 0; i < numPassengers; i++) {
			outsideSecurity.add(Ticketing.generatePassenger(log));
		}
	}
	
	/**
	 * Gets the depart time of the next passenger to go
	 * 
	 * @return time next passenger will leave
	 */
	@Override
	public int departTimeNext() {
		return outsideSecurity.front().getArrivalTime();
	}
	
	/**
	 * Gets the next passenger to go
	 * 
	 * @return next passenger to go
	 */
	@Override
	public Passenger nextToGo() {
		return outsideSecurity.front();
	}
	
	/**
	 * Tells if presecurity has another passenger
	 * 
	 * @return true if more passengers exist, false if empty
	 */
	public boolean hasNext() {
		return !outsideSecurity.isEmpty();
	}
	
	/**
	 * Removes the next passenger to go when its time for her/him to go
	 * 
	 * @return Passenger that was removed
	 */
	@Override
	public Passenger removeNext() {
		return outsideSecurity.remove();
	}
}
