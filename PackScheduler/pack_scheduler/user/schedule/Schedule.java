package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;
/**
 * Schedule represents a class schedule, able to manage an array list of courses
 * @author wshenry
 * @author ndeggens
 *
 */
public class Schedule {
	/** ArrayList schedule **/
	private ArrayList<Course> schedule;
	/** Title of schedule **/
	private String title;
	
	/**
	 * Constructs the schedule of type ArrayList
	 */
	public Schedule() {
		resetSchedule();
	}
	
	/**
	 * Adds a course to the ArrayList schedule 
	 * @param c is the course to be added
	 * @return if course is added
	 */
	public boolean addCourseToSchedule(Course c) {
		try {
			for(int i = 0; i < schedule.size(); i++) {
				c.checkConflict(schedule.get(i));
			} 
		} catch (ConflictException e) {
				throw new IllegalArgumentException();
		}
		for(int i = 0; i < schedule.size(); i++) {
			if(c.isDuplicate(schedule.get(i))) {
				throw new IllegalArgumentException("You are already enrolled in " + schedule.get(i).getName());
			}
		} 
		try {
			schedule.add(c);
			return true;
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Removes the course from the ArrayList schedule
	 * @param c is the course to be removed
	 * @return if the course is removed
	 */
	public boolean removeCourseFromSchedule(Course c) {
		return schedule.remove(c);
	}
	
	/**
	 * Resets the ArrayList to a blank ArrayList
	 * Resets the title to My Schedule
	 */
	public void resetSchedule() {
		schedule = new ArrayList<Course>();
		setTitle("My Schedule");
	}
	
	/**
	 * Gets all of the courses in the ArrayList
	 * @return Array of the courses and their information
	 */
	public String[][] getScheduledCourses() {
		String[][] courses = new String[schedule.size()][5];
		for(int i = 0; i < courses.length; i++) {
			courses[i] = schedule.get(i).getShortDisplayArray();
		}
		return courses;
	}
	
	/**
	 * Sets the title of the schedule
	 * @param s the name of the schedule
	 */
	public void setTitle(String s) {
		if(s == null) {
			throw new IllegalArgumentException();
		}
		title = s;
	}
	
	/**
	 * Returns the title of the schedule
	 * @return String schedule name
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Returns the total credits in the schedule
	 * @return total - total credits in schedule
	 */
	public int getScheduleCredits() {
		int total = 0;
		for (Course c : schedule) {
			total += c.getCredits();
		}
		return total;
	}
	
	/**
	 * Checks if the course can be added to the students schedule
	 * @param c Course trying to add
	 * @return true if it can be added, otherwise false
	 */
	public boolean canAdd(Course c) {
		if (c == null) {
			return false;
		}
		for (int i = 0; i < schedule.size(); i++) {
			try {
				schedule.get(i).checkConflict(c);
			} catch (ConflictException e) {
				return false;
			}
			if (schedule.get(i).isDuplicate(c)) {
				return false;
			}
		}
		return true;
	}
	
}
