/**
 * 
 */
package edu.ncsu.csc316.dsa.data;

/**
 * An object that represents a Student and all of its information.
 * @author griffin
 *
 */
public class Student implements Comparable<Student>, Identifiable {

	/** First name of student */
	private String first;

	/** Last name of student */
	private String last;

	/** Id of student */
	private int id;

	/** Credit hours of student */
	private int creditHours = 0;

	/** GPA of student */
	private double gpa = 0.0;

	/** UnityId of student */
	private String unityID = null;

	/**
	 * Constructs a Student object
	 * @param first First name of student
	 * @param last Last name of student
	 * @param id Id of student
	 */
	public Student(String first, String last, int id) {
		this.first = first;
		this.last = last;
		this.id = id;
	}

	/**
	 * Gets the first name of student
	 * @return first First name of student
	 */
	public String getFirst() {
		return first;
	}

	/**
	 * Sets the first name of student
	 * @param first First name of student
	 */
	public void setFirst(String first) {
		this.first = first;
	}

	/**
	 * Gets the last name of the student
	 * @return last Last name of the student
	 */
	public String getLast() {
		return last;
	}

	/**
	 * Sets the last name of the student
	 * @param last Last name of the student
	 */
	public void setLast(String last) {
		this.last = last;
	}

	/**
	 * Gets the Id of the students
	 * @return id The id of the student
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id of the student
	 * @param id Id of the student
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the credit hours of the student
	 * @return creditHours The credit hours of the student
	 */
	public int getCreditHours() {
		return creditHours;
	}

	/**
	 * Sets the credit hours of the student
	 * @param creditHours The credit hours of the student
	 */
	public void setCreditHours(int creditHours) {
		this.creditHours = creditHours;
	}

	/**
	 * Gets the gpa of the student
	 * @return gpa The gpa of the student
	 */
	public double getGpa() {
		return gpa;
	}

	/**
	 * Sets the gpa of the student
	 * @param gpa The gpa of the student
	 */
	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

	/**
	 * Gets the unity Id of the student
	 * @return unityID The unity id of the student
	 */
	public String getUnityID() {
		return unityID;
	}

	/**
	 * Sets the unity id of the student
	 * @param unityID The unity id of the student
	 */
	public void setUnityID(String unityID) {
		this.unityID = unityID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + id;
		result = prime * result + ((last == null) ? 0 : last.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (id != other.id)
			return false;
		if (last == null) {
			if (other.last != null)
				return false;
		} else if (!last.equals(other.last))
			return false;
		return true;
	}

	@Override
	public int compareTo(Student o) {
		if (this.last.compareTo(o.last) > 0) {
			return 1;
		} else if (this.last.compareTo(o.last) < 0) {
			return -1;
		} else { // infers same last name, so compare first name
			if (this.first.compareTo(o.first) > 0) {
				return 1;
			} else if (this.first.compareTo(o.first) < 0) {
				return -1;
			} else { // infers same last name and first name, so compare id
				if (this.id > o.id) {
					return 1;
				} else { // cannot have same id, so if its not greater it is less
					return -1;
				}
			}
		}
	}

	@Override
	public String toString() {
		return "Student [first=" + first + ", last=" + last + ", id=" + id + ", creditHours=" + creditHours + ", gpa="
				+ gpa + ", unityID=" + unityID + "]";
	}
}