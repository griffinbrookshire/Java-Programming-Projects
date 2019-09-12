/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Interface checks if an object is going to cause a conflict
 * @author Margarette
 *
 */
public interface Conflict {

	/**
	 * checks if activity is going to cause a conflict
	 * @param possibleConflictingActivity the second activity that is being checked
	 * against activity one
	 * @throws ConflictException throws conflict exception when activities are conflicting each other
	 * because of meeting days or overlapping times
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;
	
}
