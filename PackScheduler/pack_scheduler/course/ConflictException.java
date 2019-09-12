/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Class for conflict exception that has the constructors for the exception
 * @author Margarette
 *
 */
public class ConflictException extends Exception {

	/**
	 * ID used for serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for conflict exception that changes the message
	 * @param message the message that is shown when there is a conflict exception
	 */
	public ConflictException(String message) {
		super(message);
	}
	
	/**
	 * Conflict exception constructor that has the 
	 * default message "Schedule conflict"
	 */
	public ConflictException() {
		this("Schedule conflict."); 
	}
	
}
