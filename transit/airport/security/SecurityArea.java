/**
 * 
 */
package edu.ncsu.csc216.transit.airport.security;

import java.util.ArrayList;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.travelers.Passenger;

/**
 * Holds the checkpoints and responsible for passenger delegation
 * 
 * @author Griffin Brookshire
 */
public class SecurityArea implements TransitGroup {

	/** Maximum number of checkpoints */
	private static final int MAX_CHECKPOINTS = 17;
	/** Minimum number of checkpoints */
	private static final int MIN_CHECKPOINTS = 3;
	/** Error message for incorrect number of checkpoints */
	private static final String ERROR_CHECKPOINTS = "Number of checkpoints must be at least 3 and at most 17.";
	/** Error message for incorrect index */
	private static final String ERROR_INDEX = "Index out of range for this security area";
	/** Largest index of FastTrack checkpoint */
	private int largestFastIndex;
	/** Index of the PreCheck checkpoint */
	private int tsaPreIndex;
	/** Initializes collection of checkpoints */
	private ArrayList<CheckPoint> checkpoints;

	/**
	 * Constructs the security area. Populates the security area with 
	 * the specified number of checkpoints
	 * 
	 * @param numCheckpoints number of checkpoints in the security area
	 * @throws IllegalArgumentException if number of checkpoints is not OK
	 */
	public SecurityArea(int numCheckpoints) {
		if (!numGatesOK(numCheckpoints)) {
			throw new IllegalArgumentException(ERROR_CHECKPOINTS);
		}
		checkpoints = new ArrayList<CheckPoint>(numCheckpoints);
		for (int i = 0; i < numCheckpoints; i++) {
			CheckPoint c = new CheckPoint();
			checkpoints.add(c);
		}
		double cp = numCheckpoints;
		int numFast = (int) Math.ceil(cp / 3.0);
		largestFastIndex = (numFast - 1);
		tsaPreIndex = numCheckpoints - 1;
	}

	/**
	 * Checks if number of checkpoints is within bounds
	 * 
	 * @param checks number of checkpoints
	 * @return true if number of checkpoints if OK, false if not OK
	 */
	private boolean numGatesOK(int checks) {
		return (checks <= MAX_CHECKPOINTS && checks >= MIN_CHECKPOINTS);
	}

	/**
	 * Adds passenger to specified line
	 * 
	 * @param idx index of checkpoints to add passenger
	 * @param p Passenger to add
	 */
	public void addToLine(int idx, Passenger p) {
		if (idx < 0 || idx > tsaPreIndex) {
			throw new IllegalArgumentException(ERROR_INDEX);
		} else {
			p.getInLine(this);
			checkpoints.get(idx).addToLine(p);
		}
	}

	/**
	 * Gets the index of the shortest Regular line
	 * 
	 * @return idx index of shortest Regular line
	 */
	public int shortestRegularLine() {
		int idx = largestFastIndex + 1;
		int temp;
		int min = Integer.MAX_VALUE;
		for (int i = largestFastIndex + 1; i < tsaPreIndex; i++) {
			temp = checkpoints.get(i).size();
			if (temp < min) {
				min = temp;
				idx = i;
			}
		}
		return idx;
	}

	/**
	 * Gets the index of the shortest FastTrack line
	 * 
	 * @return idx index of shortest FastTrack line
	 */
	public int shortestFastTrackLine() {
		int idx = 0;
		int temp;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < tsaPreIndex; i++) {
			temp = checkpoints.get(i).size();
			if (temp < min) {
				min = temp;
				idx = i;
			}
		}
		return idx;
	}

	/**
	 * Gets index of TSA line
	 * 
	 * @return index of TSA line
	 */
	public int shortestTSAPreLine() {
		int idx = 0;
		int temp;
		int min = Integer.MAX_VALUE;
		for (int i = largestFastIndex + 1; i <= tsaPreIndex; i++) {
			temp = checkpoints.get(i).size();
			if (temp < min) {
				min = temp;
				idx = i;
			}
		}
		if (checkpoints.get(tsaPreIndex).hasNext() && checkpoints.get(tsaPreIndex).size() > 2 * min) {
			return idx;
		}
		return tsaPreIndex;
	}

	/**
	 * Gets the length of the line at the specified index
	 * 
	 * @param idx index of line
	 * @return size of specified line
	 * @throws IllegalArgumentException if given index is out of range
	 */
	public int lengthOfLine(int idx) {
		if (idx < 0 || idx > checkpoints.size() - 1) {
			throw new IllegalArgumentException(ERROR_INDEX);
		}
		return checkpoints.get(idx).size();
	}

	/**
	 * When will the next passenger leave the group?
	 * @return departure time of the next passenger or Integer.MAX_VALUE if the group is empty
	 */
	@Override
	public int departTimeNext() {
		int minDepTime = Integer.MAX_VALUE;
		for (int i = 0; i < checkpoints.size(); i++) {
			if (checkpoints.get(i).hasNext()) {
				int pTime = checkpoints.get(i).nextToGo().getProcessTime();
				int aTime = checkpoints.get(i).nextToGo().getArrivalTime();
				if (pTime + aTime < minDepTime) {
					minDepTime = pTime + aTime;
				}
			}
		}
		return minDepTime;
	}

	/**
	 * Who will be the next passenger to leave the group?
	 * @return the next passenger
	 */
	@Override
	public Passenger nextToGo() {
		Passenger p = null;
		int minDepartTime = Integer.MAX_VALUE;
		int temp;
		for (int i = 0; i < checkpoints.size(); i++) {
			if (checkpoints.get(i).hasNext()) {
				temp = checkpoints.get(i).nextToGo().getArrivalTime() + checkpoints.get(i).nextToGo().getWaitTime() + checkpoints.get(i).nextToGo().getProcessTime();
				if (temp < minDepartTime) {
					minDepartTime = temp;
					p = checkpoints.get(i).nextToGo();
				}
			}
		}
		return p;
	}

	/**
	 * Removes the next passenger to go
	 * 
	 * @return the passenger who is removed
	 */
	@Override
	public Passenger removeNext() {
		return checkpoints.get(lineWithNextToClear()).removeFromLine();
	}

	/**
	 * Gets the index of the line that contains the passenger who is next to leave
	 * 
	 * @return index of line with passenger next to go
	 */
	private int lineWithNextToClear() {
		int idx = 0;
		int minDepartTime = Integer.MAX_VALUE;
		int temp;
		for (int i = 0; i < checkpoints.size(); i++) {
			if (checkpoints.get(i).hasNext()) {
				temp = checkpoints.get(i).nextToGo().getArrivalTime() + checkpoints.get(i).nextToGo().getWaitTime() + checkpoints.get(i).nextToGo().getProcessTime();
				if (temp < minDepartTime) {
					minDepartTime = temp;
					idx = i;
				}
			}
		}
		return idx;
	}

}
