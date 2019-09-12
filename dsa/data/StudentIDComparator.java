/**
 * 
 */
package edu.ncsu.csc316.dsa.data;

import java.util.Comparator;

/**
 * Helps compare Students by their ID.
 * @author griffin
 *
 */
public class StudentIDComparator implements Comparator<Student> {

	@Override
	public int compare(Student one, Student two) {
		if (one.getId() > two.getId()) 
			return 1;
		else {
			return -1;
		}
	}

}