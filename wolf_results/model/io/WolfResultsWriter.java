/**
 * 
 */
package edu.ncsu.csc216.wolf_results.model.io;

import java.io.File;
import java.io.PrintStream;

import edu.ncsu.csc216.wolf_results.race_results.RaceList;

/**
 * A class for writing WolfResults files
 * @author Sammy Penninger, Griffin Brookshire
 */
public class WolfResultsWriter {

	/**
	 * Writes a race list from the WolfResults file
	 * @param file the file to write to
	 * @param rl RaceList to write to file 
	 */
	public static void writeRaceFile(String file, RaceList rl) {

		try {
			PrintStream fileWriter = new PrintStream(new File(file));
			for(int i = 0; i < rl.size(); i++) {
				fileWriter.println("# " + rl.getRace(i).getName());
				fileWriter.println(rl.getRace(i).getDistance());
				fileWriter.println(rl.getRace(i).getDate());
				fileWriter.println(rl.getRace(i).getLocation());
				fileWriter.println();
				for(int j = 0; j < rl.getRace(i).getResults().size(); j++) {
					fileWriter.println("* " + rl.getRace(i).getResults().getResult(j).getName() + "," + rl.getRace(i).getResults().getResult(j).getAge()
							+ "," + rl.getRace(i).getResults().getResult(j).getTime());
				}
				fileWriter.println();
			}
			fileWriter.close();
		} catch (Exception e) {
			throw new IllegalArgumentException("File not saved");
		}
	}

}