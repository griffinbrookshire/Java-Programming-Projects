package edu.ncsu.csc316.dsa.manager;

import edu.ncsu.csc316.dsa.data.Student;
import edu.ncsu.csc316.dsa.io.StudentReader;
import edu.ncsu.csc316.dsa.sorter.InsertionSorter;
import edu.ncsu.csc316.dsa.sorter.Sorter;

/**
 * Manages a roster of Students
 *
 * @author Dr. King
 * @author Griffin Brookshire - glbrook2
 */
public class StudentManager {

    /** A roster of Students **/
    private Student[] roster;
    
     /** The sorter used when sorting Students **/
    private Sorter<Student> sorter;
    
    /**
     * Creates a StudentManager from a file with the given pathname, and a specified Sorter.
     * @param pathToFile The name of the file
     * @param sorter The sorter to sort the students from the file
     */
    public StudentManager(String pathToFile, Sorter<Student> sorter) {
        roster = StudentReader.readInputAsArray(pathToFile);
        this.sorter = sorter;
    }
    
    /**
     * Creates a new StudentManager given the path
     * to an input file that contains Student information
     * @param pathToFile The pathname of the file to use
     */
    public StudentManager(String pathToFile) {
        this(pathToFile, new InsertionSorter<Student>(null));
    }
        
    /**
     * Returns a sorted array of Students
     *
     * @return the sorted array of Students
     */
    public Student[] sort()
    {
        sorter.sort(roster);
        return roster;
    }
  
}