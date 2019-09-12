package edu.ncsu.csc216.pack_scheduler.course.validator;


/**
 * Exception thrown when state machine makes an illegal state transition
 * 
 * @author noaheggenschwiler
 *
 */
public class InvalidTransitionException extends Exception {
	/**
	 * ID Used for Serialization
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor for the InvalidTransitionException 
	 * @param message is the message of Exception
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}
	
	/**
	 * Constructor for the InvalidTransitionException with custom message of
	 * Invalid FSM Transition 
	 */
	public InvalidTransitionException() {
		
		this("Invalid FSM Transition.");	
	}
}
