/**
 * 
 */
package edu.ncsu.csc216.transit.simulation_utils;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.entrance.PreSecurity;
import edu.ncsu.csc216.transit.airport.security.SecurityArea;
import edu.ncsu.csc216.transit.airport.travelers.Passenger;

/**
 * EventCalendar determines which passenger is next to act
 * 
 * @author Griffin Brookshire
 */
public class EventCalendar {
	
	/** PreSecurity passengers */
	TransitGroup highPriority;
	/** SecurityArea passengers */
	TransitGroup lowPriority;
	
	/**
	 * Constructs an EventCalendat with given PreSecurity and SecurityArea
	 * 
	 * @param highPriority PreSecurity passengers
	 * @param lowPriority SecurityArea passengers
	 */
	public EventCalendar(TransitGroup highPriority, TransitGroup lowPriority) {
		this.highPriority = highPriority;
		this.lowPriority = lowPriority;
	}
	
	/**
	 * Determines which passenger is next to act
	 * 
	 * @return p Passenger that is next to act
	 */
	public Passenger nextToAct() {
		Passenger next = null;
		PreSecurity p = (PreSecurity) highPriority;
		int depTimeP = Integer.MAX_VALUE;
		if (p.hasNext()) {
			depTimeP = p.departTimeNext();
		}
		SecurityArea s = (SecurityArea) lowPriority;
		int depTimeS = Integer.MAX_VALUE;
		if (s.nextToGo() != null) {
			depTimeS = s.departTimeNext();
		}
		if (depTimeP <= depTimeS) {
			next = p.nextToGo();
		} else {
			next = s.nextToGo();
		}
		return next;
	}

}
