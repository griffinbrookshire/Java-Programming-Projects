package edu.ncsu.csc316.dsa.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import edu.ncsu.csc316.dsa.data.Student;

/**
 * Processes text files with Student information
 *
 * @author Dr. King
 * @author Griffin Brookshire - glbrook2
 */
public class StudentReader {

    /**
     * Returns an array of Student objects processed
     * from the given input file.
     *
     * @param filePath - the path to the input file
     * @return an array of Students
     */
    public static Student[] readInputAsArray(String filePath)
    {
        Student[] list = new Student[10];
        try(Scanner scan = new Scanner(new FileInputStream(filePath), "UTF8"))
        {
            scan.nextLine(); // SKIP HEADER LINE
            int index = 0;
            while(scan.hasNextLine())
            {
                if(index >= list.length)
                {
                    list = Arrays.copyOf(list, list.length * 2 + 1);
                }
                list[index] = processLine(scan.nextLine());
                index++;
            }
            list = Arrays.copyOf(list, index);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found: " + e.getMessage());
        }
        return list;
    }

    /**
     * Processes a line of a text file to create a Student object.
     * @param line The line to process
     * @return s Student that was processed from the line
     */
    private static Student processLine(String line) {
    	Student s;
    	String first;
        String last;
        String unityid;
        int idNumber;
        double gpa;
        int credits;
        try (Scanner scan = new Scanner(line)) {
        	scan.useDelimiter(",");
        	first = scan.next();
        	last = scan.next();
        	unityid = scan.next();
        	idNumber = scan.nextInt();
        	gpa = scan.nextDouble();
        	credits = scan.nextInt();
            s = new Student(first, last, idNumber);
        }
        s.setCreditHours(credits);
        s.setGpa(gpa);
        s.setUnityID(unityid);
        return s;
    }
}