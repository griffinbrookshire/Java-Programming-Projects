/**
 * 
 */
package edu.ncsu.csc216.transit.simulation_utils;

import java.text.DecimalFormat;

import edu.ncsu.csc216.transit.airport.travelers.Passenger;

/**
 *  * Logs the data of all passengers and computes averages.
 * 
 * @author Griffin Brookshire
 */
public class Log implements Reporter {
	/** number of completed passengers */
	private int numCompleted;
	/** total wait time of all completed passengers */
	private int totalWaitTime;
	/** total process time of all completed passengers */
	private int totalProcessTime;

	/**
	 * How many passengers have completed all their activities?
	 * @return the number of passengers who have completed their activities
	 */
	@Override
	public int getNumCompleted() {
		return this.numCompleted;
	}

	/**
	 * Log the data for a passenger.
	 * @param p - passenger whose data is to be logged
	 */
	@Override
	public void logData(Passenger p) {
		numCompleted++;
		totalWaitTime = totalWaitTime + p.getWaitTime();
		totalProcessTime = totalProcessTime + p.getProcessTime();
	}

	/**
	 * How long did passengers have to wait before completing processing?
	 * @return average passenger waiting time (in minutes).
	 */
	@Override
	public double averageWaitTime() {
		double value = (totalWaitTime / numCompleted) / 60.0;
		value = Double.parseDouble(new DecimalFormat("##.##").format(value));
		return value;
	}

	/**
	 * How long did it take passengers to complete processing?
	 * @return average processing time
	 */
	@Override
	public double averageProcessTime() {
		double value = (totalProcessTime / numCompleted) / 60.0;
		value = Double.parseDouble(new DecimalFormat("##.##").format(value));
		return value;
	}

}
