/**
 * 
 */
package edu.ncsu.csc216.wolf_results.model.io;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.Scanner;

import edu.ncsu.csc216.wolf_results.race_results.IndividualResult;
import edu.ncsu.csc216.wolf_results.race_results.Race;
import edu.ncsu.csc216.wolf_results.race_results.RaceList;
import edu.ncsu.csc216.wolf_results.util.RaceTime;

/**
 * A class for reading WolfResults files.
 * @author Sammy Penninger, Griffin Brookshire
 */
public class WolfResultsReader {

	/**
	 * Reads a race list from WolfResults file
	 * @param filename the file to read from
	 * @return the list of races
	 */
	@SuppressWarnings("resource")
	public static RaceList readRaceListFile(String filename) {
		boolean blankLine = false;
		//boolean needBlankLine = false;
		//boolean lastLineWasBlank = false;
		int lineCount = 0;
		RaceList raceList = new RaceList();
		Scanner fileReader = null;
		Scanner oneBehind = null;
		try {
			fileReader = new Scanner(new FileInputStream(filename));
			oneBehind = new Scanner(new FileInputStream(filename));
		} catch (Exception e) {
			throw new IllegalArgumentException("Error opening file.");
		}
		int raceCount = -1;
		while (fileReader.hasNextLine()) {
			String line = fileReader.nextLine();
			String line1 = null;
			if (lineCount != 0) {
				line1 = oneBehind.nextLine();
				lineCount++;
			}
			//			if (raceCount != -1) {
			//				needBlankLine = true;
			//			}
			//			if (line.equals("")) {
			//				lastLineWasBlank = true;
			//			} else {
			//				lastLineWasBlank = false;
			//			}
						if (blankLine && !line.equals("")) {
							throw new IllegalArgumentException("Should have been blank line");
						} else {
							blankLine = false;
						}
			if (line.startsWith("#")) {
				//				if (needBlankLine && lastLineWasBlank) {
				//					throw new IllegalArgumentException("Last was not blank");
				//				}
				if (line1 != null && !line1.equals("")) {
					throw new IllegalArgumentException();
				}
				String raceName = null;
				String distance = null;
				String date = null;
				String location = null;
				raceName = line;
				if (fileReader.hasNext()) {
					distance = fileReader.nextLine();
					oneBehind.nextLine();
					lineCount++;
				}
				if (fileReader.hasNext()) {
					date = fileReader.nextLine();
					oneBehind.nextLine();
				}
				if (fileReader.hasNext()) {
					location = fileReader.nextLine();
					oneBehind.nextLine();
				}
				int year = 0;
				int month = 0;
				int dayOfMonth = 0;
				try {
					if (date != null) {
						year = Integer.parseInt(date.substring(0, 4));
						month = Integer.parseInt(date.substring(5, 7));
						dayOfMonth = Integer.parseInt(date.substring(8));
					}
				} catch (Exception e) {
					throw new IllegalArgumentException("Error parsing date.");
				}
				try {
					if (distance != null) {
						Race race = new Race(raceName.substring(2), Double.parseDouble(distance), LocalDate.of(year, month, dayOfMonth), location);
						raceList.addRace(race);
						blankLine = true;
					}
				} catch (Exception e) {
					throw new IllegalArgumentException("Error creating/adding race.");
				}
				if (distance == null || date == null || location == null || distance.equals("") || date.equals("") || location.equals("")) {
					throw new IllegalArgumentException("Data was blank");
				}
				raceCount++;
			} else if (line.startsWith("*")) {
				//needBlankLine = false;
				Scanner resultScan = new Scanner(line);
				resultScan.useDelimiter(",");
				String runnerName = null;
				String age = null;
				String time = null;
				if (resultScan.hasNext()) {
					runnerName = resultScan.next();
					//oneBehind.nextLine();
				}
				if (resultScan.hasNext()) {
					age = resultScan.next();
					//oneBehind.nextLine();
				}
				if (resultScan.hasNext()) {
					time = resultScan.next();
					//oneBehind.nextLine();
				}
				if (runnerName == null || age == null || time == null || runnerName.equals("") || age.equals("") || time.equals("")) {
					throw new IllegalArgumentException("Data was blank");
				}
				resultScan.close();
				try {
					int runnerAge = Integer.parseInt(age);
					RaceTime rt = new RaceTime(time);
					IndividualResult i = new IndividualResult(raceList.getRace(raceCount), runnerName.substring(2).trim(), runnerAge, rt);
					raceList.getRace(raceCount).addIndividualResult(i);
				} catch (Exception e) {
					throw new IllegalArgumentException("Error parsing race time.");
				}
			}
		}
		fileReader.close();
		return raceList;
	}

}