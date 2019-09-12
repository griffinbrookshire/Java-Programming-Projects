package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;
import edu.ncsu.csc216.pack_scheduler.util.LinkedQueue;
/**
 * Creates an Linked Abstract List that contains all of the students within a class
 * @author noaheggenschwiler
 *
 */
public class CourseRoll {
	/** LinkedAbstractList of Students **/
	public LinkedAbstractList<Student> roll;
	
	/** Enrollment Cap of a class **/
	private int enrollmentCap;
	
	/** Minimum Enrollment of a Class **/
	private static final int MIN_ENROLLMENT = 10;
	
	/** Maximum Enrollment of a Class **/
	private static final int MAX_ENROLLMENT = 250;
	private LinkedQueue<Student> waitList;
	private static final int WAITLIST_SIZE = 10;
	private Course course;
	
	/**
	 * Constructor for the Course Roll class, constructs an empty linked abstract list
	 * Sets the enrollment cap based on parameter
	 * @param c Course whose CourseRoll this is
	 * @param cap used to set enrollment cap
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CourseRoll(Course c, int cap) {
		roll = new LinkedAbstractList(cap);
		waitList = new LinkedQueue<Student>(WAITLIST_SIZE);
		setEnrollmentCap(cap);
		if (c == null) {
			throw new IllegalArgumentException();
		}
		this.course = c;
	}
	
	/**
	 * Returns the enrollment cap
	 * @return enrollment cap
	 */ 
	public int getEnrollmentCap() {
		return enrollmentCap;
	}
	
	/**
	 * Sets the enrollment cap, which cannot be smaller than 10 and larger than 250
	 * @param cap the capacity that will be setting the enrollment
	 * @throws IllegalArgumentException if cap is less than minimum or more than the maximum
	 */
	public void setEnrollmentCap(int cap) {
		if(cap < MIN_ENROLLMENT || cap > MAX_ENROLLMENT) {
			throw new IllegalArgumentException("Invalid enrollment cap");
		}
		else {
			roll.setCapacity(cap);
			enrollmentCap = cap;
		}	
	}
	
	/**
	 * Enrolls a student into a class and adds them to the end of the linked abstract list
	 * @param s student that will be added to the Linked Abstract List
	 * @throws IllegalArgumentException if student cannot be enrolled
	 */
	public void enroll(Student s) {
		if(!canEnroll(s)) {
			throw new IllegalArgumentException();
		}
		if(s == null) {
			throw new IllegalArgumentException();
		} else if (roll.size() == enrollmentCap) {
			if (waitList.size() < WAITLIST_SIZE) {
				waitList.enqueue(s);
			} else {
				throw new IllegalArgumentException();
			}
		}
		else {
			if (roll.size() == 0) {
				roll.add(0, s);
			} else {
				roll.add(roll.size() - 1, s);
			}
		}	
	}
	
	/**
	 * 
	 * Drops a student from a class and removes them from the Linked Abstract List
	 * @param s student that will be dropped from class
	 * @throws IllegalArgumentException if student is null
	 */
	public void drop(Student s) {
		if(s == null) {
			throw new IllegalArgumentException();
		}
		if (roll.contains(s)) {
			int idx = roll.indexOf(s);
			roll.remove(idx);
			if (!waitList.isEmpty()) {
				Student nextUp = waitList.dequeue();
				roll.add(roll.size(), nextUp);
				nextUp.getSchedule().addCourseToSchedule(course);
			}
		}
	}
	
	/**
	 * Returns the difference between the enrollmentCap and the size of the roll
	 * @return difference of enrollmentCap - roll size
	 */
	public int getOpenSeats() {
		return enrollmentCap - roll.size();
	}
	
	/**
	 * Gets the size of the waitlist
	 * @return size of waitlist
	 */
	public int getNumberOnWaitlist() {
		return waitList.size();
	}
	
	/**
	 * Checks to see if a student can be enrolled in a class
	 * Cannot be enrolled if the student already exists or if the size of the roll is at the maximum
	 * @param s student that is being checked if they can enroll
	 * @return true if student can be enrolled and false if the student cannot be enrolled
	 */
	public boolean canEnroll(Student s) {
		if((getOpenSeats() == 0 && waitList.size() == WAITLIST_SIZE ) || roll.contains(s)) {
			return false;
		}
		
		return true;	
	}
	

	
	
	
}
