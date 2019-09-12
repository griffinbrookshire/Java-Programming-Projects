/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * This class reads in a list of Faculty from a given input file and their information and 
 * outputs the list of students to a specified output file.
 * @author griffin
 */
public class FacultyRecordIO {
	
	/**
	 * Reads in an input file containing a list of faculty information and transferring 
	 * the faculty to a LinkedList
	 * @param filename specified input file
	 * @return faculty linked list containing faculty from file
	 * @throws FileNotFoundException if specified input file is not found
	 */
	public static LinkedList<Faculty> readFacultyRecords(String filename)  throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(filename));
		Faculty f = null;
	    LinkedList<Faculty> faculty = new LinkedList<Faculty>();
	    while (fileReader.hasNextLine()) {
	        try {
	            f = processFaculty(fileReader.nextLine());
	            faculty.add(f);
	        } catch (IllegalArgumentException e) {
	            //skip the line
	        }
	    }
	    fileReader.close();
	    return faculty;
	}
	
	private static Faculty processFaculty(String line) {
		String firstName = null;
		String lastName = null;
		String id = null;
		String email = null;
		String password = null;
		int maxCourses = 0;
		Scanner process = new Scanner(line);
		process.useDelimiter(",");
		if (process.hasNext()) //need to do this because faculty may be missing information
			firstName = process.next();
		if (process.hasNext())
			lastName = process.next();
		if (process.hasNext())
			id = process.next();
		if (process.hasNext())
			email = process.next();
		if (process.hasNext())
			password = process.next();
		if (process.hasNextInt())
			maxCourses = process.nextInt();
		Faculty f = new Faculty(firstName, lastName, id, email, password, maxCourses);
		process.close();
		return f;
	}
	
	/**
	 * Writes the list of Faculty to a specified output file
	 * @param fileName name of output file
	 * @param list list of Faculty
	 * @throws IOException if output file is not found
	 */
	public static void writeFacultyRecords(String filename, LinkedList<Faculty> list) throws IOException {
		try{
			PrintStream fileWriter = new PrintStream(new File(filename));
			for(int i = 0; i < list.size(); i++) {
				fileWriter.println(list.get(i).toString());
			}
			fileWriter.close();
		}
		catch (IOException e) {
			throw new IOException(filename + " (Permission denied)");
		}
	}

}
