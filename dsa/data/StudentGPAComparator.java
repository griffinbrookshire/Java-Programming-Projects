/**
 * 
 */
package edu.ncsu.csc316.dsa.data;

import java.util.Comparator;

/**
 * Helps compare Students by their GPA.
 * @author griffin
 *
 */
public class StudentGPAComparator implements Comparator<Student> {

	@Override
	public int compare(Student one, Student two) {
		if (one.getGpa() > two.getGpa()) {
			return 1;
		} else if (one.getGpa() < two.getGpa()){
			return -1;
		} else {
			return 0;
		}
	}

}