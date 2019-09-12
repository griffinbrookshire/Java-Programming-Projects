package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Registration Manager
 * @author noaheggenschwiler
 * @author wshenry
 * @author glbrook2
 *
 */
public class RegistrationManager {

	private static RegistrationManager instance;
	private CourseCatalog courseCatalog;
	private StudentDirectory studentDirectory;
	private FacultyDirectory facultyDirectory;
	private User registrar;
	private User currentUser;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	private static final String PROP_FILE = "registrar.properties";
	/**
	 * Private constructor that uses getInstance and createRegistrar to create a single instance of the Class
	 */
	private RegistrationManager() {
		studentDirectory = new StudentDirectory();
		courseCatalog = new CourseCatalog();
		facultyDirectory = new FacultyDirectory();
		createRegistrar();
		clearData();
	}
	/**
	 * Creates a Registrar using a properties file in the PackScheduler project
	 */
	private void createRegistrar() {
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			String hashPW = hashPW(prop.getProperty("pw"));

			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"), prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}
	/**
	 * Hashes the password for the Registrar
	 * @param pw password to hash
	 * @return hashed password
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return new String(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}
	/**
	 * If there is no instance of RegistrationManager, creates an instance of RegistrationManager
	 * @return the Registration manager
	 */
	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}
	/**
	 * Getter for the Course Catalog
	 * @return The course catalog of the registration manager
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}
	/**
	 * Getter for the Student Directory
	 * @return The student directory for the RegistrationManager
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}
	
	public FacultyDirectory getFacultyDirectory() {
		return facultyDirectory;
	}
	/**
	 * Logs the user into the registration manager
	 * @param id id of the user logging in
	 * @param password password of the user logging in
	 * @return true if the user could log in
	 */
    public boolean login(String id, String password) throws IllegalArgumentException {
        User s = studentDirectory.getStudentById(id);
        if (registrar.getId().equals(id)) {
            MessageDigest digest;
            try {
                digest = MessageDigest.getInstance(HASH_ALGORITHM);
                digest.update(password.getBytes());
                String localHashPW = new String(digest.digest());
                if (registrar.getPassword().equals(localHashPW)) {
                    currentUser = registrar;
                    return true;
                }
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalArgumentException("User doesn't exist");
            }
        }

        if(s == null) {
        	s = getFacultyDirectory().getFacultyById(id);
			if (s == null) {			
        		return false;
        	}
        }

        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(password.getBytes());
            String localHashPW = new String(digest.digest());
            if (s.getPassword().equals(localHashPW)) {
                currentUser = s;
                return true;
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("User doesn't exist");
        }



        return false;
	}
	/**
	 * Logs the user out of the RegistrationManager
	 */
	public void logout() {
		currentUser = null; 
	}

	/**
	 * Getter for current user
	 * @return the current user of the program
	 */
	public User getCurrentUser() {
		return currentUser;
	}
	/**
	 * Clears the data of the course catalog and the student directory
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
		facultyDirectory.newFacultyDirectory();
	}

	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * @param c Course to enroll in
	 * @return true if enrolled
	 */
	public boolean enrollStudentInCourse(Course c) {
		if (currentUser == null || !(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student)currentUser;
			Schedule schedule = s.getSchedule();
			CourseRoll roll = c.getCourseRoll();

			if (s.canAdd(c) && roll.canEnroll(s)) {
				schedule.addCourseToSchedule(c);
				roll.enroll(s);
				return true;
			}

		} catch (IllegalArgumentException e) {
			return false;
		}
		return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * @param c Course to drop
	 * @return true if dropped
	 */
	public boolean dropStudentFromCourse(Course c) {
		if (currentUser == null || !(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student)currentUser;
			c.getCourseRoll().drop(s);
			return s.getSchedule().removeCourseFromSchedule(c);
		} catch (IllegalArgumentException e) {
			return false; 
		}
	}

	/**
	 * Resets the logged in student's schedule by dropping them
	 * from every course and then resetting the schedule.
	 */
	public void resetSchedule() {
		if (currentUser == null || !(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student)currentUser;
			Schedule schedule = s.getSchedule();
			String [][] scheduleArray = schedule.getScheduledCourses();
			for (int i = 0; i < scheduleArray.length; i++) {
				Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
				c.getCourseRoll().drop(s);
			}
			schedule.resetSchedule();
		} catch (IllegalArgumentException e) {
			//do nothing 
		}
	}

	/**
	 * Internal class to manage the Registrar of the RegistrationManager
	 * @author wshenry
	 *
	 */
	private static class Registrar extends User {
		/**
		 * Create a registrar user with the user id and password 
		 * in the registrar.properties file.
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
	}
}