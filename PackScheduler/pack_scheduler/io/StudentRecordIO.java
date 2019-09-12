package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * This class reads in a list of Students from a given input file and their information and 
 * outputs the list of students to a specified output file.
 * @author kibrown
 */
public class StudentRecordIO {
	
	/**
	 * Reads in an input file containing a list of student information and transferring 
	 * the students to an sorted list
	 * @param fileName specified input file
	 * @return students sorted list containing students from file
	 * @throws FileNotFoundException if specified input file is not found
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		Student student = null;
	    SortedList<Student> students = new SortedList<Student>();
	    while (fileReader.hasNextLine()) {
	        try {
	            student = processStudent(fileReader.nextLine());
	            students.add(student);
	        } catch (IllegalArgumentException e) {
	            //skip the line
	        }
	    }
	    fileReader.close();
	    return students;
	}

	private static Student processStudent(String line) {
		String firstName = null;
		String lastName = null;
		String id = null;
		String email = null;
		String password = null;
		int maxCredits = 0;
		Scanner process = new Scanner(line);
		process.useDelimiter(",");
		if (process.hasNext()) //need to do this because students can be missing information
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
			maxCredits = process.nextInt();
		Student s = new Student(firstName, lastName, id, email, password, maxCredits);
		process.close();
		return s;
	}
	
	/**
	 * Writes the list of Students to a specified output file
	 * @param fileName name of output file
	 * @param studentDirectory list of Students
	 * @throws IOException if output file is not found
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		try{
			PrintStream fileWriter = new PrintStream(new File(fileName));
			for(int i = 0; i < studentDirectory.size(); i++) {
				fileWriter.println(studentDirectory.get(i).toString());
			}
			fileWriter.close();
		}
		catch (IOException e) {
			throw new IOException(fileName + " (Permission denied)");
		}
		
	}

}
