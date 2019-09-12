/**
 * 
 */
package edu.ncsu.csc216.wolf_results.util;

/**
 * Represents a RaceTime or Pace
 * @author Sammy Penninger, Griffin Brookshire
 */
public class RaceTime {

	/** Hours of time */
	private int hours;
	/** Minutes of time */
	private int minutes;
	/** Seconds of time */
	private int seconds;

	/**
	 * Constructs a RaceTime from hours, mins, secs
	 * @param hours hours of run
	 * @param minutes minutes of run
	 * @param seconds seconds of run
	 */
	public RaceTime(int hours, int minutes, int seconds) {
		if (hours < 0 || minutes < 0 || minutes > 59 || seconds < 0 || seconds > 59) {
			throw new IllegalArgumentException();
		}
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
	}

	/**
	 * Constructs a RaceTime from a String
	 * @param time String representation of time
	 */
	public RaceTime(String time) {
		time = time.trim();
		String hour = null;
		String minute = null;
		String second = null;
		int colonCount = 0;
		for (int i = 0; i < time.length(); i++) {
			Character c = time.charAt(i);
			if (c == ':') {
				colonCount++;
			} else if (!Character.isDigit(c)) {
				throw new IllegalArgumentException("Invalid character");
			}
		}
		if (colonCount == 2) {
			int firstColonIndex = 0; // at this point the string can only contain digits and two colons
			int secondColonIndex = 0;
			boolean foundFirst = false;
			for (int i = 0; i < time.length(); i++) {
				Character c = time.charAt(i);
				if (c == ':' && !foundFirst) {
					firstColonIndex = i;
					foundFirst = true;
				}
				if (c == ':' && foundFirst) {
					secondColonIndex = i;
				}
			}
			hour = time.substring(0, firstColonIndex);
			minute = time.substring(firstColonIndex + 1, secondColonIndex);
			if (minute.length() != 2) {
				throw new IllegalArgumentException("Invalid minutes");
			}
			second = time.substring(secondColonIndex + 1);
		} else {
			throw new IllegalArgumentException();
		}
		try {
			int testHours = Integer.parseInt(hour);
			int testMinutes = Integer.parseInt(minute);
			int testSeconds = Integer.parseInt(second);
			if (testMinutes < 0 || testMinutes > 59 || testSeconds < 0 || testSeconds > 59) {
				throw new IllegalArgumentException("Minutes or Seconds not between 0 and 60");
			}
			this.hours = testHours;
			this.minutes = testMinutes;
			this.seconds = testSeconds;
		} catch (Exception e) {
			throw new IllegalArgumentException("Error parsing string to int");
		}
	}

	/**
	 * Gets the hours from the runners time
	 * @return hours
	 */
	public int getHours() {
		return this.hours;
	}

	/**
	 * Gets the minutes from the runners time
	 * @return minutes
	 */
	public int getMinutes() {
		return this.minutes;
	}

	/**
	 * Gets the seconds from the runners time
	 * @return seconds
	 */
	public int getSeconds() {
		return this.seconds;
	}

	/**
	 * Gets the number of seconds the runner ran
	 * @return seconds
	 */
	public int getTimeInSeconds() {
		int second = 0;
		second += getHours() * 60 * 60;
		second += getMinutes() * 60;
		second += getSeconds();
		return second;
	}

	/**
	 * Gets a String representation of the Race time
	 * @return the string representation of the Race time
	 */
	public String toString() {
		String hour = String.valueOf(getHours());
		String minute = String.valueOf(getMinutes());
		String second = String.valueOf(getSeconds());
		if (minute.length() == 1) {
			minute = '0' + minute;
		}
		if (second.length() == 1) {
			second = '0' + second;
		}
		return hour + ':' + minute + ':' + second;
	}

	/**
	 * Compares two RaceTimes, returns 1 if this RaceTime is greater than or equal to 'r', zero otherwise
	 * @param other RaceTime to compare to this
	 * @return one if this RaceTime is bigger or equal, zero if this RaceTime is smaller
	 */
	public int compareTo(RaceTime other) {
		if (this.getTimeInSeconds() > other.getTimeInSeconds()) {
			return 1;
		} else if (this.getTimeInSeconds() == other.getTimeInSeconds()) {
			return 0;
		}
		return -1;
	}

}
