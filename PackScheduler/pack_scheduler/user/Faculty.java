/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user;

/**
 * Faculty is a User type in PackScheduler
 * 
 * @author griffin
 *
 */
public class Faculty extends User {
	
	/** Max courses this teacher can take */
	private int maxCourses;
	/** Minimum courses any teacher can teach */
	public static final int MIN_COURSES = 1;
	/** Maximum courses any teacher can teach */
	public static final int MAX_COURSES = 3;

	/**
	 * Constructs a faculty member with the specified details
	 * 
	 * @param firstName first name of faculty
	 * @param lastName last name of faculty
	 * @param id id of faculty
	 * @param email faculty email
	 * @param pw password of faculty
	 * @param maxCourses Max courses this teacher can teach
	 */
	public Faculty(String firstName, String lastName, String id, String email, String pw, int maxCourses) {
		super(firstName, lastName, id, email, pw);
		setMaxCourses(maxCourses);
	}

	/**
	 * Sets the max courses this teacher can teach
	 * @param max
	 */
	public void setMaxCourses(int max) {
		if (max < MIN_COURSES || max > MAX_COURSES) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		this.maxCourses = max;
	}
	
	/**
	 * Gets the max courses this teacher can teach
	 * @return
	 */
	public int getMaxCourses() {
		return maxCourses;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCourses;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Faculty))
			return false;
		Faculty other = (Faculty) obj;
		if (maxCourses != other.maxCourses)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + "," + getMaxCourses();
	}
	
	
	
}
