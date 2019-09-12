/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * Creates Course class
 * @author Margarette Dy
 *
 */
public class Course extends Activity implements Comparable<Course>  {

	/** Constant section length of 3 */
	private static final int SECTION_LENGTH = 3;
	/** Constant max name length of 6 */
	private static final int MAX_NAME_LENGTH = 6;
	/** Constant minimum name length of 4 */
	private static final int MIN_NAME_LENGTH = 4;
	/** Constant max credits */
	private static final int MAX_CREDITS = 5;
	/** Constant min credits */
	private static final int MIN_CREDITS = 1;
	
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/** Validates course name */
	private CourseNameValidator validator = new CourseNameValidator();
	/** Course roll for this course */
	private CourseRoll roll;
	
	
	/**
	 * Constructs a Course object with values for all fields.
	 * @param name name of Course
	 * @param title title of Course
	 * @param section section of Course
	 * @param credits credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param enrollmentCap max students in course
	 * @param meetingDays meeting days for Course as series of chars
	 * @param startTime start time for Course
	 * @param endTime end time for Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays,
	        int startTime, int endTime) {
	    setName(name);
	    setTitle(title);
	    setSection(section);
	    setCredits(credits);
	    setInstructorId(instructorId);
	    setMeetingDays(meetingDays);
	    setActivityTime(startTime, endTime);
	    roll = new CourseRoll(this, enrollmentCap);
	}

	/**
	 * Creates a Course with the given name, title, section, credits, instructorId, and meetingDays for 
	 * courses that are arranged.
	 * @param name name of Course
	 * @param title title of Course
	 * @param section section of Course
	 * @param credits credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param enrollmentCap max students in course
	 * @param meetingDays meeting days for Course as series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays) {
	    this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	}

	
	/**
	 * Returns the course's name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name.  If the name is null, has a length less than 4 or 
	 * greater than 6, an IllegalArgumentException is thrown.
	 * @param name the name to set
	 * @throws IllegalArgumentException if name is null or length is less than 4 or 
	 * greater than 6
	 */
	private void setName(String name) {
	    if (name == null || name.equals("")) {
	        throw new IllegalArgumentException("Invalid course name");
	    }
	    if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
	        throw new IllegalArgumentException("Invalid course name");
	    }
	    try {
			if (validator.isValid(name)) {
				this.name = name;
			}
		} catch (InvalidTransitionException e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Returns the course's section
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the course's section. If the section is not exactly 3 digits,
	 * an IllegalArgumentException is thrown.
	 * @param section the section to set
	 * @throws IllegalArgumentException if section is not exactly 3 digits
	 */
	public void setSection(String section) {
		if (section == null || section.length() != SECTION_LENGTH)
			throw new IllegalArgumentException("Invalid section number");
		this.section = section;
	}

	/**
	 * Returns the course's credit hours
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the course's credit hours
	 * @param credits the credits to set
	 * @throws IllegalArgumentException if section is not an int
	 * or is less than one or is greater than 5
	 */
	public void setCredits(int credits) {
		if(credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException();
		}
		this.credits = credits;
	}

	/**
	 * Returns the course's instructor
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the course's instructor
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException if instructorId is null or an empty string
	 */
	public void setInstructorId(String instructorId) {
		if(instructorId == null || instructorId.equals("")) {
			throw new IllegalArgumentException("Invalid instructor unity id");
		}
		this.instructorId = instructorId;
	}
	
	/**
	 *	
	 *Generates hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/**
	 * Checks if objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Returns a comma separated value String of all course fields
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if (meetingDays.equals("A")) {
	        return name + "," + title + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap() + "," + meetingDays;
	    }
	    return name + "," + title + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap() + "," + meetingDays + "," + startTime + "," + endTime; 
	}

	@Override
	public String[] getShortDisplayArray() {
		String[] shortDisplay = new String[5];
		shortDisplay[0] = getName();
		shortDisplay[1] = getSection();
		shortDisplay[2] = getTitle();
		shortDisplay[3] = getMeetingString();
		shortDisplay[4] = Integer.toString(roll.getOpenSeats());
		return shortDisplay;
	}

	@Override
	public String[] getLongDisplayArray() {
		String[] longDisplay = new String[7];
		longDisplay[0] = getName();
		longDisplay[1] = getSection();
		longDisplay[2] = getTitle();
		longDisplay[3] = Integer.toString(getCredits());
		longDisplay[4] = getInstructorId();
		longDisplay[5] = getMeetingString();
		longDisplay[6] = "";
		return longDisplay;
	}

	@Override
	public boolean isDuplicate(Activity activity) {	
		if(activity instanceof Course) {
			Course course = (Course) activity;
			if (name == null) {
				if (course.name == null) {
					return true;
				}
			} 
			else if (name.equals(course.name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int compareTo(Course o) {
		if (getName().toLowerCase().compareTo(o.getName().toLowerCase()) < 0) {
			return -1;
		}
		else if (getName().toLowerCase().compareTo(o.getName().toLowerCase()) > 0) {
				return 1;
		}
		else if (getSection().toLowerCase().compareTo(o.getSection().toLowerCase()) < 0) {
			return -1;
		}
		else if (getSection().toLowerCase().compareTo(o.getSection().toLowerCase()) > 0) {
				return 1;
		}
		else {
			return 0;
		}
	}
	
	/**
	 * Returns the courses CourseRoll
	 * @return courses CourseRoll
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}
}
