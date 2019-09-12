package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * This class creates and maintains a Student object with a given first name, last name, id, email, password
 * and max credits
 * @author wshenry
 * @author kibrown
 *
 */
public class Student extends User implements Comparable<Student> {
	/** max credits of student */
	private int maxCredits;
	/** students schedule */
	private Schedule schedule;
	/** Constant representing max credits for a student. */
	public static final int MAX_CREDITS = 18;

	/**
	 * Constructs a student object with a first name, last name, id, email, 
	 * password, and max credits.
	 * @param firstName first name of student
	 * @param lastName last name of student
	 * @param id id of student
	 * @param email email of student
	 * @param hashPW password of student
	 * @param maxCredits max credits of student
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW, int maxCredits) {
		super(firstName, lastName, id, email, hashPW);
		setMaxCredits(maxCredits);
		schedule = new Schedule();
	}

	/**
	 * Constructs a student object with a first name, last name, id, email, password, and a 
	 * default max credits of 18
	 * @param firstName first name of student
	 * @param lastName last name of student
	 * @param id id of student
	 * @param email email of student
	 * @param hashPW password of student
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW) {
		this(firstName, lastName, id, email, hashPW, MAX_CREDITS);
	}

	/**
	 * Returns the student's max credits
	 * @return the maxCredits
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets the student's max credits
	 * @param maxCredits the maxCredits to set
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits < 3 || maxCredits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		this.maxCredits = maxCredits;
	}


	/** 
	 * Returns the string representation of the student
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + "," + maxCredits;
	}

	@Override
	public int compareTo(Student o) {
		if (getLastName().toLowerCase().compareTo(o.getLastName().toLowerCase()) < 0)
			return -1;
		else if (getLastName().toLowerCase().compareTo(o.getLastName().toLowerCase()) > 0)
			return 1;
		else if (getFirstName().toLowerCase().compareTo(o.getFirstName().toLowerCase()) < 0)
			return -1;
		else if (getFirstName().toLowerCase().compareTo(o.getFirstName().toLowerCase()) > 0)
			return 1;
		else if (getId().toLowerCase().compareTo(o.getId().toLowerCase()) < 0)
			return -1;
		else if (getId().toLowerCase().compareTo(o.getId().toLowerCase()) > 0)
			return 1;
		else 
			return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCredits;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (maxCredits != other.maxCredits)
			return false;
		return true;
	}
	/**
	 * Getter for the student's schedule
	 * @return schedule of the student
	 */
	public Schedule getSchedule() {
		return schedule;
	}

	/**
	 * Tells if the course can be added to the students schedule
	 * @param c Course trying to add
	 * @return true if can be added, otherwise false
	 */
	public boolean canAdd(Course c) {
		if (schedule.canAdd(c) && schedule.getScheduleCredits() + c.getCredits() <= maxCredits) {
			return true;
		}
		return false;
	}
}
