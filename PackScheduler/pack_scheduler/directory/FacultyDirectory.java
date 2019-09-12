/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Holds the faculty
 * @author griffin
 */
public class FacultyDirectory {
	
	/** List of students in the directory */
	private LinkedList<Faculty> facultyDirectory;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	/**
	 * Constructs a FacultyDirectory with an empty directory
	 */
	public FacultyDirectory() {
		newFacultyDirectory();
	}
	
	/**
	 * Resets the faculty directory
	 */
	public void newFacultyDirectory() {
		facultyDirectory = new LinkedList<Faculty>();
	}
	
	/**
	 * Loads faculty from a file
	 * @param filename name of the file to load from
	 */
	public void loadFacultyFromFile(String filename) {
		try {
			facultyDirectory = FacultyRecordIO.readFacultyRecords(filename);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file");
		}
	}
	
	/**
	 * Adds a faculty to the directory
	 * @param firstName first name of faculty
	 * @param lastName last name of faculty
	 * @param id id of faculty
	 * @param email email of faculty
	 * @param pw password of faculty
	 * @param maxCourses maximum number of courses faculty can teach
	 * @return true if faculty was added, false otherwise
	 */
	public boolean addFaculty(String firstName, String lastName, String id, String email, String pw, String repeatedPW, int maxCourses) {
		String hashPW = "";
		String repeatHashPW = ""; 
		if (pw == null || repeatedPW == null || pw.equals("") || repeatedPW.equals("")) {
			throw new IllegalArgumentException("Invalid password");
		}
		try { 
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			hashPW = new String(digest1.digest()); 

			MessageDigest digest2 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest2.update(repeatedPW.getBytes());
			repeatHashPW = new String(digest2.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}

		if (!hashPW.equals(repeatHashPW)) {
			throw new IllegalArgumentException("Passwords do not match");
		}
		Faculty newF = new Faculty(firstName, lastName, id, email, pw, maxCourses);
		if (facultyDirectory.add(newF)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Removes a faculty with the specified id
	 * @param facultyId id of faculty to remove
	 * @return true if faculty was removed, false otherwise
	 */
	public boolean removeFaculty(String facultyId) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			if (facultyDirectory.get(i).getId().equals(facultyId)) {
				facultyDirectory.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the faculty directory as a 2-d array
	 * @return 2-d array of faculty directory
	 */
	public String[][] getFacultyDirectory() {
		String[][] faculty = new String[facultyDirectory.size()][3];
		for (int i = 0; i < facultyDirectory.size(); i++) {
			faculty[i][0] = facultyDirectory.get(i).getFirstName();
			faculty[i][1] = facultyDirectory.get(i).getLastName();
			faculty[i][2] = facultyDirectory.get(i).getId();
		}
		return faculty;
	}
	
	/**
	 * Saves the current faculty directory to a file
	 * @param filename name of file to save to
	 */
	public void saveFacultyDirectory(String filename) {
		try {
			FacultyRecordIO.writeFacultyRecords(filename, facultyDirectory);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file");
		}
	}
	
	/**
	 * Gets a faculty from the directory with the specified id
	 * @param id id of faculty to get
	 * @return faculty with the specified id
	 */
	public Faculty getFacultyById(String id) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			if (facultyDirectory.get(i).getId().equals(id)) {
				return facultyDirectory.get(i);
			}
		}
		return null;
	}
	
}
