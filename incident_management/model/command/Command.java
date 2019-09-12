/**
 * 
 */
package edu.ncsu.csc216.incident_management.model.command;

/**
 * Holds information about a command that changes the state of an incident
 * 
 * @author griffin
 */
public class Command {
	
	/** Awaiting caller message */
	public static final String OH_CALLER = "Awaiting Caller";
	/** Awaiting change message */
	public static final String OH_CHANGE = "Awaiting Change";
	/** Awaiting vendor message */
	public static final String OH_VENDOR = "Awaiting Vendor";
	/** Permanently solved message */
	public static final String RC_PERMANENTLY_SOLVED = "Permanently Solved";
	/** Workaround message */
	public static final String RC_WORKAROUND = "Workaround";
	/** Not solved message */
	public static final String RC_NOT_SOLVED = "Not Solved";
	/** Called closed message */
	public static final String RC_CALLER_CLOSED = "Caller Closed";
	/** Duplicate message */
	public static final String CC_DUPLICATE = "Duplicate";
	/** Unnecessary message */
	public static final String CC_UNNECESSARY = "Unnecessary";
	/** Not an incident message */
	public static final String CC_NOT_AN_INCIDENT = "Not an Incident";
	/** Id of the incident owner */
	private String ownerId;
	/** Description of the incident */
	private String note;
	/** CommandValue of command */
	private CommandValue commandValue;
	/** ResolutionCode of command */
	private ResolutionCode resCode;
	/** OnHoldReason of command */
	private OnHoldReason onHoldReas;
	/** CancellationCode of command */
	private CancellationCode cancCode;
	
	/**
	 * Creates a command
	 * 
	 * @param c CommandValue of command
	 * @param ownerId id of incident owner
	 * @param onHoldReason reason to put on hold
	 * @param resolutionCode reason for resolution
	 * @param cancellationCode reason for cancellation
	 * @param note any notes about incident
	 */
	public Command(CommandValue c, String ownerId, OnHoldReason onHoldReason, ResolutionCode resolutionCode, CancellationCode cancellationCode, String note) {
		boolean ownerIdNull = false;
		if (ownerId == null || ownerId.equals("")) {
			ownerIdNull = true;
		}
		if (c == null || (c.equals(CommandValue.INVESTIGATE) && ownerIdNull) || (c.equals(CommandValue.HOLD) && onHoldReason == null) || (c.equals(CommandValue.RESOLVE) && resolutionCode == null) || (c.equals(CommandValue.CANCEL) && cancellationCode == null) || (note == null || note.equals(""))) {
			throw new IllegalArgumentException();
		}
		this.ownerId = ownerId;
		this.note = note;
		this.commandValue = c;
		this.resCode = resolutionCode;
		this.onHoldReas = onHoldReason;
		this.cancCode = cancellationCode;
	}
	
	/**
	 * Gets the command value
	 * 
	 * @return command value
	 */
	public CommandValue getCommand() {
		return commandValue;
	}
	
	/**
	 * Gets the owner Id
	 * 
	 * @return owner Id
	 */
	public String getOwnerId() {
		return ownerId;
	}
	
	/**
	 * Gets the resolution code
	 * 
	 * @return resolution code
	 */
	public ResolutionCode getResolutionCode() {
		return resCode;
	}
	
	/**
	 * Gets the work note
	 * 
	 * @return note
	 */
	public String getWorkNote() {
		return note;
	}
	
	/**
	 * Gets the reason for being on hold
	 * 
	 * @return reason for on hold
	 */
	public OnHoldReason getOnHoldReason() {
		return onHoldReas;
	}
	
	/**
	 * Gets the cancellation code
	 * 
	 * @return cancellation code
	 */
	public CancellationCode getCancellationCode() {
		return cancCode;
	}

	/**
	 * Type of command
	 * 
	 * @author griffin
	 */
	public enum CommandValue {

		/** Investigate command*/
		INVESTIGATE, 
		/** Hold command */
		HOLD,
		/** Resolve command */
		RESOLVE,
		/** Confirm command */
		CONFIRM,
		/** Reopen command */
		REOPEN,
		/** Cancel command */
		CANCEL
		
	}
	
	/**
	 * Reason for on hold
	 * 
	 * @author griffin
	 */
	public enum OnHoldReason {

		/** Awaiting caller reason */
		AWAITING_CALLER,
		/** Awaiting change reason */
		AWAITING_CHANGE,
		/** Awaiting vendor reason */
		AWAITING_VENDOR
		
	}
	
	/**
	 * Reason for cancellation
	 * 
	 * @author griffin
	 */
	public enum CancellationCode {
		
		/** Duplicate code */
		DUPLICATE,
		/** Unnecessary code */
		UNNECESSARY,
		/** Not an incident code */
		NOT_AN_INCIDENT
		
	}
	
	/**
	 * Reason for resolution
	 * 
	 * @author griffin
	 */
	public enum ResolutionCode {
	
		/** Permanently solved code */
		PERMANENTLY_SOLVED,
		/** Workaround code */
		WORKAROUND,
		/** Not solved code */
		NOT_SOLVED,
		/** Caller closed code */
		CALLER_CLOSED
		
	}

}
