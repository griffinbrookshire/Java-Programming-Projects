/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;

/**
 * Class for handling an array of various courses for the pack_scheduler class
 * @author mpdy
 * @author wshenry
 * @author griffin
 */
public class CourseCatalog {
	/** Sorted List of Courses */
	private SortedList<Course> catalog;
	/**
	 * Constructor that creates an empty course catalog
	 */
	public CourseCatalog() {
		this.newCourseCatalog();
	}
	/**
	 * Creates a new catalog with no elements
	 */
	public void newCourseCatalog() {
		catalog = new SortedList<Course>();
	}
	/**
	 * Creates a catalog of courses from a file with the file name fileName
	 * @param fileName file name of the file to import a course catalog
	 */
	public void loadCoursesFromFile(String fileName) {
		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}
	/**
	 * Adds a course to the catalog
	 * @param name name of the course
	 * @param title title of the course
	 * @param section section of the course
	 * @param credits credit hours of the course
	 * @param instructorId the id of the course's instructor
	 * @param cap enrollment cap for the course
	 * @param meetingDays string of days that the course meets on
	 * @param startTime start time of the course
	 * @param endTime end time of the course
	 * @return true if the course was added
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId, int cap, String meetingDays, int startTime, int endTime) {
		Course c;
		c = new Course(name, title, section, credits, instructorId, cap, meetingDays, startTime, endTime);
		for(int i = 0; i < catalog.size(); i++) {
			if(c.isDuplicate(catalog.get(i)) && c.getSection() == catalog.get(i).getSection()) {
				return false;
			}
		}
		catalog.add(c);
		return true;
	}
	/**
	 * Removes a course from the catalog
	 * @param name name of the course to be removed
	 * @param section section of the course to be removed
	 * @return true if the course was removed successfully
	 */
	public boolean removeCourseFromCatalog(String name, String section) {
		for(int i = 0; i < catalog.size(); i++) {
			if(name.equals(catalog.get(i).getName()) && section.equals(catalog.get(i).getSection())) {
				catalog.remove(i);
				return true;
			}
		}
		return false;
	}
	/**
	 * Gets a course from the catalog
	 * @param name name of the course to get
	 * @param section section of the course to get
	 * @return the course object if it exists within the catalog
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for(int i = 0; i < catalog.size(); i++) {
			if(name.equals(catalog.get(i).getName()) && section.equals(catalog.get(i).getSection())) {
				return catalog.get(i);
			}
		}
		return null;
	}
	/**
	 * Returns a string array of courses from the catalog
	 * @return the array of course strings in the short display array form
	 */
	public String[][] getCourseCatalog() {
		String[][] array = new String[catalog.size()][4];
		for(int i = 0; i < array.length; i++) {
			array[i] = catalog.get(i).getShortDisplayArray();
		}
		return array;
	}
	/**
	 * Saves the course catalog to a file with the given file name
	 * @param fileName file name of the file which the course will save to
	 */
	public void saveCourseCatalog(String fileName) {
		try {
			CourseRecordIO.writeCourseRecords(fileName, catalog);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + fileName);
		}
	}
}
