package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;


/**
 * Reads Course records from text files.  Writes a set of CourseRecords to a file.
 * 
 * @author Sarah Heckman
 * @author Margarette Dy
 */
public class CourseRecordIO {

    /**
     * Reads course records from a file and generates a list of valid Courses.  Any invalid
     * Courses are ignored.  If the file to read cannot be found or the permissions are incorrect
     * a File NotFoundException is thrown.
     * @param fileName file to read Course records from
     * @return a list of valid Courses
     * @throws FileNotFoundException if the file cannot be found or read
     * 
     * "Unexpected error reading " + validTestFile
     */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
	    Scanner fileReader = new Scanner(new FileInputStream(fileName));
	    SortedList<Course> courses = new SortedList<Course>();
	    while (fileReader.hasNextLine()) {
	        try {
	            Course course = readCourse(fileReader.nextLine());
	            boolean duplicate = false;
	            for (int i = 0; i < courses.size(); i++) {
	                Course c = courses.get(i);
	                if (course.getName().equals(c.getName()) &&
	                        course.getSection().equals(c.getSection())) {
	                    //it's a duplicate
	                    duplicate = true;
	                }
	            }
	            if (!duplicate) {
	                courses.add(course);
	            }
	        } catch (IllegalArgumentException e) {
	            //skip the line
	        }
	    }
	    fileReader.close();
	    return courses;
	}
    
	private static Course readCourse(String fileName){
    	Scanner fileReader = null;
    	Course read = null;
    	Scanner s = null;
    	int items = 0;
    	
    	try {
    		fileReader = new Scanner(fileName);
    		fileReader.useDelimiter(",");
    		s = new Scanner(fileName);
    		s.useDelimiter(",");
    		
    		while (s.hasNext()) {
        		items++;
        		s.next();
    		}
    		
	    	while (fileReader.hasNextLine()) {
	    		if(items == 9) {
	    			String name = fileReader.next();
	    			String title = fileReader.next();
	    			String section = fileReader.next();
	    			int credits = fileReader.nextInt();
	    			String instructorId = fileReader.next();
	    			int cap = fileReader.nextInt();
	    			String meetingDays = fileReader.next();
	    			int startTime = fileReader.nextInt();
	    			int endTime = fileReader.nextInt();
		    		read = new Course(name, title, section, credits, instructorId, cap, meetingDays, startTime, endTime);
	    		}
	    		else {
	    			String name = fileReader.next();
	    			String title = fileReader.next();
	    			String section = fileReader.next();
	    			int credits = fileReader.nextInt();
	    			String instructorId = fileReader.next();
	    			int cap = fileReader.nextInt();
	    			String meetingDays = fileReader.next();
	    			read = new Course(name, title, section, credits, instructorId, cap, meetingDays);
	    		}
	    	}

    	} catch (NoSuchElementException e){
    		s.close();
    		fileReader.close();
	    	throw new IllegalArgumentException();
    	}
    	s.close();
    	fileReader.close();
		return read;
	}
	/**
	 * Writes the CourseRecord to a file
	 * @param fileName name of file to write to
	 * @param courses sorted list of courses to write to a file
	 * @throws IOException thrown if cannot write to the given file
	 */
	public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		for (int i = 0; i < courses.size(); i++) {
		    fileWriter.println(courses.get(i).toString());
		}
		fileWriter.close();
	    
	}

}