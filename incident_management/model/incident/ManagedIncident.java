/**
 * 
 */
package edu.ncsu.csc216.incident_management.model.incident;

import java.util.ArrayList;

import edu.ncsu.csc216.incident.xml.Incident;
import edu.ncsu.csc216.incident.xml.WorkNotes;
import edu.ncsu.csc216.incident_management.model.command.Command;
import edu.ncsu.csc216.incident_management.model.command.Command.CancellationCode;
import edu.ncsu.csc216.incident_management.model.command.Command.CommandValue;
import edu.ncsu.csc216.incident_management.model.command.Command.OnHoldReason;
import edu.ncsu.csc216.incident_management.model.command.Command.ResolutionCode;

/**
 * Represents an incident that has been dealt with
 * 
 * @author griffin
 */
public class ManagedIncident implements IncidentState {

	/** Inquiry incident type */
	public static final String C_INQUIRY = "Inquiry";
	/** Software incident type */
	public static final String C_SOFTWARE = "Software";
	/** Hardware incident type */
	public static final String C_HARDWARE = "Hardware";
	/** Network incident type */
	public static final String C_NETWORK = "Network";
	/** Database incident type */
	public static final String C_DATABASE = "Database";
	/** Urgent priority message */
	public static final String P_URGENT = "Urgent";
	/** High priority message */
	public static final String P_HIGH = "High";
	/** Medium priority message */
	public static final String P_MEDIUM = "Medium";
	/** Low priority message */
	public static final String P_LOW = "Low";
	/** Id of the incident */
	private int incidentId;
	/** Caller of the incident */
	private String caller;
	/** Category of incident */
	private Category category;
	/** Current state for the incident of type IncidentState */
	private IncidentState state;
	/** Priority of the incident */
	private Priority priority;
	/** Owner of the incident */
	private String owner = null;
	/** Incident’s name information from when the incident is created */
	private String name;
	/** On hold reason for the incident*/
	private OnHoldReason onHoldReason = null;
	/** Change request information for the incident */
	private String changeRequest = null;
	/** Resolution code for the incident */
	private ResolutionCode resolutionCode = null;
	/** Cancellation code for the incident */
	private CancellationCode cancellationCode = null;
	/** Collection of incident notes */
	private ArrayList<String> notes = new ArrayList<String>();
	/** String representation of New state */
	public static final String NEW_NAME = "New";
	/** String representation of In Progress state */
	public static final String IN_PROGRESS_NAME = "In Progress";
	/** String representation of On Hold state */
	public static final String ON_HOLD_NAME = "On Hold";
	/** String representation of Resolved state */
	public static final	String RESOLVED_NAME = "Resolved";
	/** String representation of Closed state */
	public static final String CLOSED_NAME = "Closed";
	/** String representation of Canceled state */
	public static final String CANCELED_NAME = "Canceled";
	/** A static field that keeps track of the id value that should be given to the next ManagedIncident created */
	private static int counter = 0;
	/** Instance of NewState */
	private final NewState newState = new NewState();
	/** Instance of InProgressState */
	private final InProgressState inProgressState = new InProgressState();
	/** Instance of OnHoldState */
	private final OnHoldState onHoldState = new OnHoldState();
	/** Instance of ResolvedState */
	private final ResolvedState resolvedState = new ResolvedState();
	/** Instance of ClosedState */
	private final ClosedState closedState = new ClosedState();
	/** Instance of CancelledState */
	private final CanceledState canceledState = new CanceledState();

	/**
	 * Creates a ManagedIncident with the specified details
	 * 
	 * @param caller Caller of the incident
	 * @param category Category of incident
	 * @param priority Priority of the incident
	 * @param name Incident’s name information from when the incident is created
	 * @param workNote Notes regarding the incident
	 */
	public ManagedIncident(String caller, Category category, Priority priority, String name, String workNote) {
		if (caller == null || category == null || priority == null || name == null || workNote == null || caller.equals("") || workNote.equals("") || name.equals("")) {
			throw new IllegalArgumentException();
		}
		this.caller = caller;
		this.category = category;
		this.priority = priority;
		this.name = name;
		notes.add(workNote);
		this.state = newState;
		this.incidentId = counter;
		incrementCounter();
	}

	/**
	 * Creates a managed incident from an incident
	 * 
	 * @param i incident to become managed
	 */
	public ManagedIncident(Incident i) {
		if (i.getCaller() == null || i.getCategory() == null || i.getPriority() == null || i.getName() == null || i.getWorkNotes() == null || i.getCaller().equals("") || i.getCategory().equals("") || i.getPriority().equals("") || i.getName().equals("")) {
			throw new IllegalArgumentException();
		}
		if (i.getCancellationCode() != null) {
			this.cancellationCode = this.getCancellationCodeObject(i.getCancellationCode());
		}
		if (i.getOnHoldReason() != null) {
			this.onHoldReason = this.getOnHoldReasonObject(i.getOnHoldReason());
		}
		if (i.getResolutionCode() != null) {
			this.resolutionCode = this.getResolutionCodeObject(i.getResolutionCode());
		}
		if (i.getChangeRequest() != null) {
			this.changeRequest = i.getChangeRequest();
		}
		this.caller = i.getCaller();
		this.category = this.getCategoryObject(i.getCategory());
		this.priority = this.getPriorityObject(i.getPriority());
		this.notes = (ArrayList<String>) i.getWorkNotes().getNotes();
		this.name = i.getName();
		state = this.getStateObject(i.getState());
		this.incidentId = i.getId();
		this.owner = i.getOwner();
		incrementCounter();
	}

	/**
	 * Changes a String representation of a category to a Category object
	 * 
	 * @param categoryString String rep of category
	 * @throws IllegalArgumentException if parameter is not a category
	 * @return Category object
	 */
	private Category getCategoryObject(String categoryString) {
		if (categoryString.equals(C_INQUIRY)) {
			return Category.INQUIRY;
		}
		if (categoryString.equals(C_SOFTWARE)) {
			return Category.SOFTWARE;
		}
		if (categoryString.equals(C_HARDWARE)) {
			return Category.HARDWARE;
		}
		if (categoryString.equals(C_NETWORK)) {
			return Category.NETWORK;
		}
		if (categoryString.equals(C_DATABASE)) {
			return Category.DATABASE;
		}
		throw new IllegalArgumentException();
	}

	/**
	 * Changes a String representation of a priority to a Priority object
	 * 
	 * @param priorityString String rep of priority
	 * @throws IllegalArgumentException if parameter is not a category
	 * @return Priority object
	 */
	private Priority getPriorityObject(String priorityString) {
		if (priorityString.equals(P_URGENT)) {
			return Priority.URGENT;
		}
		if (priorityString.equals(P_HIGH)) {
			return Priority.HIGH;
		}
		if (priorityString.equals(P_MEDIUM)) {
			return Priority.MEDIUM;
		}
		if (priorityString.equals(P_LOW)) {
			return Priority.LOW;
		}
		throw new IllegalArgumentException();
	}
	
	/**
	 * Changes a String to OnHoldReason
	 * 
	 * @param onHold String reason
	 * @return OnHoldReason object
	 * @throws IllegalArgumentException if String is not an OnHoldReason
	 */
	private OnHoldReason getOnHoldReasonObject(String onHold) {
		if (onHold.equals(Command.OH_CALLER)) {
			return OnHoldReason.AWAITING_CALLER;
		} else if (onHold.equals(Command.OH_CHANGE)) {
			return OnHoldReason.AWAITING_CHANGE;
		} else if (onHold.equals(Command.OH_VENDOR)) {
			return OnHoldReason.AWAITING_VENDOR;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Changes a String to a ResolutionCode
	 * 
	 * @param resCode String code
	 * @return ResolutionCode object
	 * @throws IllegalArgumentException if String is not a ResolutionCode
	 */
	private ResolutionCode getResolutionCodeObject(String resCode) {
		if (resCode.equals(Command.RC_CALLER_CLOSED)) {
			return ResolutionCode.CALLER_CLOSED;
		}
		if (resCode.equals(Command.RC_NOT_SOLVED)) {
			return ResolutionCode.NOT_SOLVED;
		}
		if (resCode.equals(Command.RC_PERMANENTLY_SOLVED)) {
			return ResolutionCode.PERMANENTLY_SOLVED;
		}
		if (resCode.equals(Command.RC_WORKAROUND)) {
			return ResolutionCode.WORKAROUND;
		}
		throw new IllegalArgumentException();
	}

	/**
	 * Changes a String to a CancellationCode
	 * 
	 * @param canCode String code
	 * @return CancellationCode object
	 * @throws IllegalArgumentException if String is not a CancellationCode
	 */
	private CancellationCode getCancellationCodeObject(String canCode) {
		if (canCode.equals(Command.CC_DUPLICATE)) {
			return CancellationCode.DUPLICATE;
		}
		if (canCode.equals(Command.CC_NOT_AN_INCIDENT)) {
			return CancellationCode.NOT_AN_INCIDENT;
		}
		if (canCode.equals(Command.CC_UNNECESSARY)) {
			return CancellationCode.UNNECESSARY;
		}
		throw new IllegalArgumentException();
	}
	
	/**
	 * Increments the counter
	 */
	public static void incrementCounter() {
		counter++;
	}

	/**
	 * Gets the incident id
	 * 
	 * @return incident id
	 */
	public int getIncidentId() {
		return incidentId;
	}

	/**
	 * Gets the change request
	 * 
	 * @return change request
	 */
	public String getChangeRequest() {
		return changeRequest;
	}

	/**
	 * Gets the incident category
	 * 
	 * @return category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * Gets the category as a string
	 * 
	 * @return category as a string
	 */
	public String getCategoryString() {
		if (category.equals(Category.INQUIRY)) {
			return C_INQUIRY;
		}
		if (category.equals(Category.SOFTWARE)) {
			return C_SOFTWARE;
		}
		if (category.equals(Category.HARDWARE)) {
			return C_HARDWARE;
		}
		if (category.equals(Category.NETWORK)) {
			return C_NETWORK;
		}
		if (category.equals(Category.DATABASE)) {
			return C_DATABASE;
		}
		return null;
	}

	/**
	 * Gets the priority as a string
	 * 
	 * @return priority
	 */
	public String getPriorityString() {
		if (priority.equals(Priority.URGENT)) {
			return P_URGENT;
		}
		if (priority.equals(Priority.HIGH)) {
			return P_HIGH;
		}
		if (priority.equals(Priority.MEDIUM)) {
			return P_MEDIUM;
		}
		if (priority.equals(Priority.LOW)) {
			return P_LOW;
		}
		return null;
	}

	/**
	 * Gets the on hold reason as a string
	 * 
	 * @return on hold reason
	 */
	public String getOnHoldReasonString() {
		if (onHoldReason == null) {
			return null;
		}
		if (onHoldReason.equals(OnHoldReason.AWAITING_CALLER)) {
			return Command.OH_CALLER;
		}
		if (onHoldReason.equals(OnHoldReason.AWAITING_CHANGE)) {
			return Command.OH_CHANGE;
		}
		if (onHoldReason.equals(OnHoldReason.AWAITING_VENDOR)) {
			return Command.OH_VENDOR;
		}
		return null;
	}

	/**
	 * Gets the cancellation code as a string
	 * 
	 * @return cancellation code
	 */
	public String getCancellationCodeString() {
		if (cancellationCode == null) {
			return null;
		}
		if (cancellationCode.equals(CancellationCode.DUPLICATE)) {
			return Command.CC_DUPLICATE;
		}
		if (cancellationCode.equals(CancellationCode.NOT_AN_INCIDENT)) {
			return Command.CC_NOT_AN_INCIDENT;
		}
		if (cancellationCode.equals(CancellationCode.UNNECESSARY)) {
			return Command.CC_UNNECESSARY;
		}
		return null;
	}

	/**
	 * Gets the current state
	 * 
	 * @return state current state of incident
	 */
	public IncidentState getState() {
		return state;
	}

	/**
	 * Sets the current state
	 * 
	 * @param state
	 */
	private IncidentState getStateObject(String state) {
		if (state.equals(NEW_NAME)) {
			return newState;
		}
		if (state.equals(IN_PROGRESS_NAME)) {
			return inProgressState;
		}
		if (state.equals(ON_HOLD_NAME)) {
			return onHoldState;
		}
		if (state.equals(RESOLVED_NAME)) {
			return resolvedState;
		}
		if (state.equals(CLOSED_NAME)) {
			return closedState;
		}
		if (state.equals(CANCELED_NAME)) {
			return canceledState;
		}
		throw new IllegalArgumentException();
	}

	/**
	 * Gets the resolution code
	 * 
	 * @return resolution code
	 */
	public ResolutionCode getResolutionCode() {
		return resolutionCode;
	}

	/**
	 * Gets the resolution code as a string
	 * 
	 * @return resolution code
	 */
	public String getResolutionCodeString() {
		if (resolutionCode == null) {
			return null;
		}
		if (resolutionCode.equals(ResolutionCode.CALLER_CLOSED)) {
			return Command.RC_CALLER_CLOSED;
		}
		if (resolutionCode.equals(ResolutionCode.NOT_SOLVED)) {
			return Command.RC_NOT_SOLVED;
		}
		if (resolutionCode.equals(ResolutionCode.PERMANENTLY_SOLVED)) {
			return Command.RC_PERMANENTLY_SOLVED;
		}
		if (resolutionCode.equals(ResolutionCode.WORKAROUND)) {
			return Command.RC_WORKAROUND;
		}
		return null;
	}

	/**
	 * Gets the incident owner
	 * 
	 * @return owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Gets the incident name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the incident caller
	 * 
	 * @return caller
	 */
	public String getCaller() {
		return caller;
	}

	/**
	 * Gets the collection of notes
	 * 
	 * @return notes
	 */
	public ArrayList<String> getNotes() {
		return notes;
	}

	/**
	 * Gets the notes as a string
	 * 
	 * @return notes as a string
	 */
	public String getNotesString() {
		String notesString = "";
		for (int i = 0; i < notes.size(); i++) {
			if (notes.size() == 1) {
				return notes.get(0);
			} else {
				notesString = notesString + notes.get(i) + "\n" + "-------" + "\n";
			}
		}
		return notesString;
	}

	/**
	 * Updates the incident
	 * 
	 * @param c command for the incident
	 */
	public void update(Command c) {
		this.state.updateState(c);
	}

	/**
	 * Gets an incident from an xml
	 * 
	 * @return incident
	 */
	public Incident getXMLIncident() {
		Incident i = new Incident();
		i.setId(this.getIncidentId()); // all have id
		i.setCaller(this.getCaller()); // all have caller
		i.setCategory(this.getCategoryString()); // all have category
		i.setState(this.getStateName()); // all have state
		i.setPriority(this.getPriorityString()); // all have priority
		i.setName(this.getName()); // all have name
		if (this.getOwner() != null && !(this.getOwner().equals(""))) {
			i.setOwner(this.getOwner());
		}
		if (this.getChangeRequest() != null && !(this.getChangeRequest().equals(""))) {
			i.setChangeRequest(this.getChangeRequest());
		}
		if (this.getOnHoldReasonString() != null && !(this.getOnHoldReasonString().equals(""))) {
			i.setOnHoldReason(this.getOnHoldReasonString());
		}
		if (this.getResolutionCodeString() != null && !(this.getResolutionCodeString().equals(""))) {
			i.setResolutionCode(this.getResolutionCodeString());
		}
		if (this.getCancellationCodeString() != null && !(this.getCancellationCodeString().equals(""))) {
			i.setCancellationCode(this.getCancellationCodeString());
		}
		WorkNotes w = new WorkNotes();
		w.getNotes().addAll(this.getNotes());
		i.setWorkNotes(w);
		return i;
	}

	/**
	 * Sets the counter
	 * 
	 * @param count int to set at
	 */
	public static void setCounter(int count) {
		counter = count;
	}

	/**
	 * Update the ManagedIncident based on the given Command.
	 * An UnsupportedOperationException is thrown if the CommandValue
	 * is not a valid action for the given state.
	 * 
	 * @param c Command describing the action that will update the ManagedIncident's
	 * state.
	 * @throws UnsupportedOperationException if the CommandValue is not a valid action
	 * for the given state.
	 */
	@Override
	public void updateState(Command c) {
		this.state.updateState(c);
	}

	/**
	 * Returns the name of the current state as a String.
	 * 
	 * @return the name of the current state as a String.
	 */
	@Override
	public String getStateName() {
		if (this.getState() instanceof NewState) {
			return newState.getStateName();
		}
		if (this.getState() instanceof InProgressState) {
			return inProgressState.getStateName();
		}
		if (this.getState() instanceof OnHoldState) {
			return onHoldState.getStateName();
		}
		if (this.getState() instanceof ResolvedState) {
			return resolvedState.getStateName();
		}
		if (this.getState() instanceof ClosedState) {
			return closedState.getStateName();
		}
		if (this.getState() instanceof CanceledState) {
			return canceledState.getStateName();
		}
		throw new IllegalStateException();
	}

	/**
	 * New incident
	 * 
	 * @author griffin
	 */
	public class NewState implements IncidentState {

		/**
		 * Update the ManagedIncident based on the given Command.
		 * An UnsupportedOperationException is thrown if the CommandValue
		 * is not a valid action for the given state.  
		 * 
		 * @param c Command describing the action that will update the ManagedIncident's
		 * state.
		 * @throws UnsupportedOperationException if the CommandValue is not a valid action
		 * for the given state.
		 */
		public void updateState(Command c) {
			if (c == null) {
				throw new IllegalArgumentException();
			}
			if (c.getCommand().equals(CommandValue.INVESTIGATE)) {
				state = inProgressState;
				owner = c.getOwnerId();
				notes.add(c.getWorkNote());
			} else if (c.getCommand().equals(CommandValue.CANCEL)) {
				state = canceledState;
				cancellationCode = c.getCancellationCode();
				notes.add(c.getWorkNote());
			} else {
				throw new UnsupportedOperationException();
			}
		}

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			return NEW_NAME;
		}
	}

	/**
	 * Incident is In Progress
	 * 
	 * @author griffin
	 */
	public class InProgressState implements IncidentState {

		/**
		 * Update the ManagedIncident based on the given Command.
		 * An UnsupportedOperationException is thrown if the CommandValue
		 * is not a valid action for the given state.
		 * 
		 * @param c Command describing the action that will update the ManagedIncident's
		 * state.
		 * @throws UnsupportedOperationException if the CommandValue is not a valid action
		 * for the given state.
		 */
		public void updateState(Command c) {
			if (c == null) {
				throw new IllegalArgumentException();
			}
			if (c.getCommand().equals(CommandValue.HOLD)) {
				if (c.getOnHoldReason() == null) {
					throw new IllegalArgumentException();
				}
				state = onHoldState;
				onHoldReason = c.getOnHoldReason();
				notes.add(c.getWorkNote());
			} else if (c.getCommand().equals(CommandValue.RESOLVE)) {
				state = resolvedState;
				resolutionCode = c.getResolutionCode();
				notes.add(c.getWorkNote());
			} else if (c.getCommand().equals(CommandValue.CANCEL)) {
				state = canceledState;
				cancellationCode = c.getCancellationCode();
				notes.add(c.getWorkNote());
			} else {
				throw new UnsupportedOperationException();
			}
		}

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			return IN_PROGRESS_NAME;
		}
	}

	/**
	 * Incident is On Hold
	 * 
	 * @author griffin
	 */
	public class OnHoldState implements IncidentState {

		/**
		 * Update the ManagedIncident based on the given Command.
		 * An UnsupportedOperationException is thrown if the CommandValue
		 * is not a valid action for the given state.
		 * 
		 * @param c Command describing the action that will update the ManagedIncident's
		 * state.
		 * @throws UnsupportedOperationException if the CommandValue is not a valid action
		 * for the given state.
		 */
		public void updateState(Command c) {
			if (c == null) {
				throw new IllegalArgumentException();
			}
			if (c.getCommand().equals(CommandValue.REOPEN)) {
				state = inProgressState;
				notes.add(c.getWorkNote());
				if (onHoldReason.equals(OnHoldReason.AWAITING_CHANGE)) {
					changeRequest = c.getWorkNote();
				}
				onHoldReason = null;
			} else if (c.getCommand().equals(CommandValue.RESOLVE)) {
				state = resolvedState;
				notes.add(c.getWorkNote());
				if (onHoldReason.equals(OnHoldReason.AWAITING_CHANGE)) {
					changeRequest = c.getWorkNote();
				}
				resolutionCode = c.getResolutionCode();
				onHoldReason = null;
			} else if (c.getCommand().equals(CommandValue.CANCEL)) {
				state = canceledState;
				cancellationCode = c.getCancellationCode();
				onHoldReason = null;
				notes.add(c.getWorkNote());
			} else {
				throw new UnsupportedOperationException();
			}
		}

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			return ON_HOLD_NAME;
		}
	}

	/**
	 * Incident is Resolved
	 * 
	 * @author griffin
	 */
	public class ResolvedState implements IncidentState {

		/**
		 * Update the ManagedIncident based on the given Command.
		 * An UnsupportedOperationException is thrown if the CommandValue
		 * is not a valid action for the given state.
		 * 
		 * @param c Command describing the action that will update the ManagedIncident's
		 * state.
		 * @throws UnsupportedOperationException if the CommandValue is not a valid action
		 * for the given state.
		 */
		public void updateState(Command c) {
			if (c == null) {
				throw new IllegalArgumentException();
			}
			if (c.getCommand().equals(CommandValue.HOLD)) {
				if (c.getOnHoldReason() == null) {
					throw new IllegalArgumentException();
				}
				state = onHoldState;
				onHoldReason = c.getOnHoldReason();
				resolutionCode = null;
				notes.add(c.getWorkNote());
			} else if (c.getCommand().equals(CommandValue.REOPEN)) {
				state = inProgressState;
				resolutionCode = null;
				notes.add(c.getWorkNote());
			} else if (c.getCommand().equals(CommandValue.CONFIRM)) {
				state = closedState;
				notes.add(c.getWorkNote());
			} else if (c.getCommand().equals(CommandValue.CANCEL)) {
				state = canceledState;
				cancellationCode = c.getCancellationCode();
				resolutionCode = null;
				notes.add(c.getWorkNote());
			} else {
				throw new UnsupportedOperationException();
			}
		}

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			return RESOLVED_NAME;
		}
	}

	/**
	 * Incident is Closed
	 * 
	 * @author griffin
	 */
	public class ClosedState implements IncidentState {

		/**
		 * Update the ManagedIncident based on the given Command.
		 * An UnsupportedOperationException is thrown if the CommandValue
		 * is not a valid action for the given state.
		 * 
		 * @param c Command describing the action that will update the ManagedIncident's
		 * state.
		 * @throws UnsupportedOperationException if the CommandValue is not a valid action
		 * for the given state.
		 */
		public void updateState(Command c) {
			if (c == null) {
				throw new IllegalArgumentException();
			}
			if (c.getCommand().equals(CommandValue.REOPEN)) {
				state = inProgressState;
				resolutionCode = null;
				notes.add(c.getWorkNote());
			} else {
				throw new UnsupportedOperationException();
			}
		}

		/**
		 * Returns the name of the current state as a String
		 * 
		 * @return the name of the current state as a String
		 */
		public String getStateName() {
			return CLOSED_NAME;
		}
	}

	/**
	 * Incident is canceled
	 * 
	 * @author griffin
	 */
	public class CanceledState implements IncidentState {

		/**
		 * Update the ManagedIncident based on the given Command.
		 * An UnsupportedOperationException is thrown if the CommandValue
		 * is not a valid action for the given state.
		 * 
		 * @param c Command describing the action that will update the ManagedIncident's
		 * state.
		 * @throws UnsupportedOperationException if the CommandValue is not a valid action
		 * for the given state.
		 */
		public void updateState(Command c) {
			throw new UnsupportedOperationException();
		}

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		public String getStateName() {
			return CANCELED_NAME;
		}
	}

	/**
	 * Categories of incidents
	 * 
	 * @author griffin
	 */
	public enum Category {

		/** Inquiry category */
		INQUIRY,
		/** Software category */
		SOFTWARE,
		/** Hardware category */
		HARDWARE,
		/** Network category */
		NETWORK,
		/** Database category */
		DATABASE

	}

	/**
	 * Priorities of incidents
	 * 
	 * @author griffin
	 */
	public enum Priority {

		/** Urgent priority */
		URGENT,
		/** High priority */
		HIGH,
		/** Medium priority */
		MEDIUM,
		/** Low priority */
		LOW

	}

}
