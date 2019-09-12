package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Creates the abstract Activity class
 * @author Margarette Dy
 *
 */
public abstract class Activity implements Conflict {

	/** Constant upper military time of 2400 */
	private static final int UPPER_TIME = 2400;
	/** Constant upper value for an hour of 60 */
	private static final int UPPER_HOUR = 60;
	
	/** Course's title. */
	protected String title;
	/** Course's meeting days */
	protected String meetingDays;
	/** Course's starting time */
	protected int startTime;
	/** Course's ending time */
	protected int endTime;

	/** Creates super class of activity*/
	public Activity() {
		super();
	}

	/** String array for short display
	 * @return String array of short display
	 */
	public abstract String[] getShortDisplayArray();
	/** String array for long display
	 * @return String array of long display
	 */
	public abstract String[] getLongDisplayArray();
	/** Boolean that checks if activity is a duplicate
	 * @param activity object that is being checked
	 * @return Boolean if activity is a duplicate
	 */
	public abstract boolean isDuplicate(Activity activity);
	 
	
	/**
	 * Checks fields of activity to see if they are the same and could cause a conflict 
	 * in schedule
	 * @param possibleConflictingActivity the activity that we are checking against another object to
	 * see if they are conflicting
	 * @throws ConflictException when the activity could conflict with the object being tested
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {		
		//throws a conflict exception if the two activities have the same meeting days
		//and a start time that overlaps the end time of possibleConflictingActivity
			for(int i = 0; i < this.getMeetingDays().length(); i++) {
				for(int j = 0; j < possibleConflictingActivity.getMeetingDays().length(); j++) {
					if(this.getMeetingDays().substring(i, i + 1).equals(possibleConflictingActivity.getMeetingDays().substring(j, j + 1)) && !this.getMeetingDays().equals("A")){
						//throws a conflict exception if the start time of the activity being checked 
						//is between the start time and end time of the activity
						if(this.getStartTime() < possibleConflictingActivity.getStartTime() && possibleConflictingActivity.getStartTime() < this.getEndTime()) {
							throw new ConflictException();
						}
						
						//throws a conflict exception if the start time of the activity being checked 
						//is between the start time and end time of the activity
						if(this.getStartTime() < possibleConflictingActivity.getEndTime() && possibleConflictingActivity.getEndTime() < this.getEndTime()) {
							throw new ConflictException();
						}
						
						//throws a conflict exception if the start time of the activity being checked 
						//is between the start time and end time of the activity
						if(possibleConflictingActivity.getStartTime() < this.getStartTime() && this.getStartTime() < possibleConflictingActivity.getEndTime()) {
							throw new ConflictException();
						}
						
						//throws a conflict exception if the start time of the activity being checked 
						//is between the start time and end time of the activity
						if(possibleConflictingActivity.getStartTime() < this.getEndTime() && this.getEndTime() < possibleConflictingActivity.getEndTime()) {
							throw new ConflictException();
						}
						
						//throws a conflict exception if the activities have overlapping
						//end time and start time
						if(this.getEndTime() == possibleConflictingActivity.getStartTime()) {
							throw new ConflictException();
						}
						if(this.getStartTime() == possibleConflictingActivity.getStartTime()) {
							throw new ConflictException();
						}
						if(this.getEndTime() == possibleConflictingActivity.getEndTime()) {
							throw new ConflictException();
						}
						if(this.getStartTime() == possibleConflictingActivity.getEndTime()) {
							throw new ConflictException();
						}
					}
				}
			}
		}
	
	/**
	 * Returns the course's title
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the course's title. If the name is null an IllegalArgumentException is thrown.
	 * @param title the title to set
	 * @throws IllegalArgumentException if title is null
	 */
	public void setTitle(String title) {
		if(title == null || title.equals("")) {
			throw new IllegalArgumentException();
		}
		this.title = title;
	}

	/**
	 * Returns the course's meeting days
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Sets the course's meeting days
	 * @param meetingDays the meetingDays to set
	 * @throws IllegalArgumentException if meetingDays is a character other 
	 * than M, T, W, H, F, or A
	 * 
	 */
	public void setMeetingDays(String meetingDays) {
		if(meetingDays == null || meetingDays.equals("")) {
			throw new IllegalArgumentException("Invalid meeting days");
		}
		if (meetingDays.contains("A") && meetingDays.length() > 1) {
			throw new IllegalArgumentException("Invalid meeting days");
		}
		if(meetingDays.matches(".*[a-z].*|.*[B-E].*|G|.*[I-L].*|.*[N-S].*|.*[U-V].*|.*[X-Z].*")) {
			throw new IllegalArgumentException("Invalid meeting days");
		}
		this.meetingDays = meetingDays;
	}

	/**
	 * Returns the course's starting time
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the course's ending time
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Sets the course's starting time and ending time
	 * @param startTime the startTime to set 
	 * @param endTime the endTime to set
	 * @throws IllegalArgumentException if startTime is 
	 * not between 0000 and 2359 and if endTime is not between 
	 * 0000 and 2359 and if endTime is less than startTime
	 */
	public void setActivityTime(int startTime, int endTime) {
		if (startTime < 0 || (startTime % 100) >= UPPER_HOUR || startTime >= UPPER_TIME)
			throw new IllegalArgumentException("Invalid meeting times");
		if (endTime < 0 || (endTime % 100) >= UPPER_HOUR || endTime >= UPPER_TIME)
			throw new IllegalArgumentException("Invalid meeting times");
		if (endTime < startTime)
			throw new IllegalArgumentException("Invalid meeting times");
		if (this.getMeetingDays().equals("A") && startTime != 0 && endTime != 0)
			throw new IllegalArgumentException("Invalid meeting times");
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Sets the course's time in standard time
	 * @return days, startTime, and endTime in the correct format
	 */
	public String getMeetingString() {
		String startMeeting = "";
		String endMeeting = "";
		String days = getMeetingDays();
		if(days.equals("A")) {
			days = "Arranged";
			return days;
		}
		if(getStartTime() >= 1300) {
			int time = getStartTime() - 1200;
			if(time % 100 == 0) {
				startMeeting = (time / 100) + ":00PM";		
			}
			else {
				startMeeting = (time / 100) + ":" + (time % 100) + "PM";
			}
		}
		else if(1000 < getStartTime() && getStartTime() < 1200) {
			if(getStartTime() % 100 == 0) {
				startMeeting = (getStartTime() / 100) + ":00AM";		
			}
			else {
				startMeeting = (getStartTime() / 100) + ":" + (getStartTime() % 100) + "AM";
			}
		}
		else if(1200 <= getStartTime() && getStartTime() < 1300) {
			if(getStartTime() % 100 == 0) {
				startMeeting = (getStartTime() / 100) + ":00PM";		
			}
			else {
				startMeeting = (getStartTime() / 100) + ":" + (getStartTime() % 100) + "PM";
			}
		}
		else if(getStartTime() < 1000) {
			if(getStartTime() % 100 == 0) {
				startMeeting = (getStartTime() / 100) + ":00AM";		
			}
			else {
				startMeeting = (getStartTime() / 100) + ":" + (getStartTime() % 100) + "AM";	
			}
		}
		if(getEndTime() >= 1300) {
			int time = getEndTime() - 1200;
			if(time % 100 == 0) {
				endMeeting = (time / 100) + ":00PM";		
			}
			else {
				endMeeting = (time / 100) + ":" + (time % 100) + "PM";
			}
		}
		else if(1000 < getEndTime() && getEndTime() < 1200) {
			if(getEndTime() % 100 == 0) {
				endMeeting = (getEndTime() / 100) + ":00AM";		
			}
			else {
				endMeeting = (getEndTime() / 100) + ":" + (getEndTime() % 100) + "AM";	
			}
		}
		else if(1200 <= getEndTime() && getEndTime() < 1300) {
			if(getEndTime() % 100 == 0) {
				endMeeting = (getEndTime() / 100) + ":00PM";		
			}
			else {
				endMeeting = (getEndTime() / 100) + ":" + (getEndTime() % 100) + "PM";	
			}
		}
		else if(getEndTime() < 1000) {
			if(getEndTime() % 100 == 0) {
				endMeeting = (getEndTime() / 100) + ":00AM";		
			}
			else {
				endMeeting = (getEndTime() / 100) + ":" + (getEndTime() % 100) + "AM";	
			}
		}
		return days + " " + startMeeting + "-" + endMeeting;
	}

	/**
	 * Creates hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Checks if objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	

}