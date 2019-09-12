package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Checks if a course name is valid or not
 * 
 * @author Griffin
 *
 */
public class CourseNameValidator {
	/** Tells if final state */
	private boolean validState;
	/** Tells if char is not illegal */
	private boolean goodChar;
	/** Holds number of letters */
	private int letterCount;
	/** Holds number of digits */
	private int digitCount;
	/** Digit state */
	private State stateDigit;
	/** Letter state */
	private State stateLetter;
	/** Initial state */
	private State stateInitial;
	/** Suffix state */
	private State stateSuffix;
	/** Current state */
	private State currentState;

	/**
	 * Sets up validator
	 */
	public CourseNameValidator() {
		validState = false;
		goodChar = true;
		letterCount = 0;
		digitCount = 0;
		stateDigit = new DigitState();
		stateLetter = new LetterState();
		stateInitial = new InitialState();
		stateSuffix = new SuffixState();
		currentState = stateInitial;
	}

	/**
	 * Checks if a course name is valid or not
	 * 
	 * @param s Course name to test
	 * @return true if Course name is valid, false if not
	 * @throws InvalidTransitionException if the fsm makes an illegal transition
	 */
	public boolean isValid(String s) throws InvalidTransitionException {
		int charIndex = 0;
		char c;
		while(charIndex < s.length()) {
			c = s.charAt(charIndex);
			switch(currentState.getState()) {
			case "INITIAL":
				if(Character.isDigit(c)) {
					throw new InvalidTransitionException("Course name must start with a letter.");
				} else if(Character.isLetter(c)) {
					currentState.onLetter();
				} else {
					goodChar = false;
					throw new InvalidTransitionException("Course name can only contain letters and digits.");
				}
				currentState.onOther();
				charIndex++;
				break;
			case "LETTER":
				if(Character.isDigit(c)) {
					currentState.onDigit();
				} else if(Character.isLetter(c)) {
					currentState.onLetter();
				} else {
					goodChar = false;
					throw new InvalidTransitionException("Course name can only contain letters and digits.");
				}
				currentState.onOther();
				charIndex++;
				break;
			case "DIGIT":
				if(Character.isDigit(c)) {
					currentState.onDigit();
				} else if(Character.isLetter(c)) {
					currentState.onLetter();
				} else {
					goodChar = false;
					throw new InvalidTransitionException("Course name can only contain letters and digits.");
				}
				currentState.onOther();
				charIndex++;
				break;
			case "SUFFIX":
				if(Character.isDigit(c)) {
					currentState.onDigit();
				} else if(Character.isLetter(c)) {
					currentState.onLetter();
				} else {
					goodChar = false;
					throw new InvalidTransitionException("Course name can only contain letters and digits.");
				}
				currentState.onOther();
				validState = true;
				charIndex++;
				break;
			default:
				throw new InvalidTransitionException();
			}
		}
		return validState;
	}

	/**
	 * Abstract class that holds general instructions for states
	 * 
	 * @author Griffin Brookshire
	 *
	 */
	public abstract class State {
		
		/** Holds string rep of current state */
		public String state;

		/**
		 * Constructs a state with given state
		 * 
		 * @param s String rep of state
		 */
		public State(String s) {
			state = s;
		}

		/** Instructions if char is a letter 
		 * @throws InvalidTransitionException if illegal state transition 
		 */
		public abstract void onLetter() throws InvalidTransitionException;
		/** Instructions if char is a digit 
		 * @throws InvalidTransitionException if illegal state transition */	
		public abstract void onDigit() throws InvalidTransitionException;

		/**
		 * Instructions if char isn't digit or letter
		 * 
		 * @throws InvalidTransitionException if illegal transition
		 */
		public void onOther() throws InvalidTransitionException {
			if (state.equals("OTHER") || !goodChar) {
				if (letterCount > 4) {
					throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
				}
				if (digitCount != 3) {
					throw new InvalidTransitionException("Course name must have 3 digits.");
				}
			}
		}

		/**
		 * Gets the current state
		 * 
		 * @return state String rep of current state
		 */
		public String getState() {
			return state;
		}
	}

	/**
	 * Holds instructions for initial state
	 * 
	 * @author Griffin Brookshire
	 *
	 */
	public class InitialState extends State {
		private InitialState() {
			super("INITIAL");
			validState = false;
			goodChar = true;
		}
		
		/**
		 * Instructions if char is a letter
		 */
		public void onLetter() {
			state = "LETTER";
			currentState = stateLetter;
			letterCount++;
		}
		
		/**
		 * Instructions if char is a digit
		 */
		public void onDigit() {
			state = "OTHER";
		}
	}

	/**
	 * Holds instructions for letter state
	 * 
	 * @author Griffin Brookshire
	 *
	 */
	public class LetterState extends State {
		private LetterState() {
			super("LETTER");
		}
		
		/**
		 * Instructions if char is a letter
		 */
		public void onLetter() {
			if (letterCount == 4) {
				state = "OTHER";
			}
			letterCount++;
			currentState = stateLetter;
		}

		/**
		 * Instructions if char is a digit
		 */
		public void onDigit() {
			state = "DIGIT";
			digitCount++;
			currentState = stateDigit;
		}
	}

	/**
	 * Holds instructions for digit state
	 * 
	 * @author Griffin Brookshire
	 *
	 */
	public class DigitState extends State {
		private DigitState() {
			super("DIGIT");
		}

		/**
		 * Instructions if char is a letter
		 */
		public void onLetter() {
			letterCount++;
			if(digitCount == 3) {
				state = "SUFFIX";
				currentState = stateSuffix;
			} else {
				state = "OTHER";
			}
		}

		/**
		 * Instructions if char is a digit
		 * @throws InvalidTransitionException if invalid state transition
		 */
		public void onDigit() throws InvalidTransitionException {
			digitCount++;
			if(digitCount == 3) {
				state = "SUFFIX";
				validState = true;
			} else if (digitCount > 3) {
				state = "OTHER";
				throw new InvalidTransitionException("Course name can only have 3 digits.");
			}
		}
	}

	/**
	 * Holds instructions for suffix state
	 * 
	 * @author Griffin Brookshire
	 *
	 */
	public class SuffixState extends State {
		private SuffixState() {
			super("SUFFIX");
		}
		
		/**
		 * Instructions if char is a letter
		 * @throws InvalidTransitionException if invalid state transition
		 */
		public void onLetter() throws InvalidTransitionException {
			letterCount++;
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
		}

		/**
		 * Instructions if char is a digit
		 * @throws InvalidTransitionException if illegal state transition
		 */
		public void onDigit() throws InvalidTransitionException {
			digitCount++;
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
			}
		}

}
