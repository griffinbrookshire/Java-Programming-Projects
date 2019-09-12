package edu.ncsu.csc316.rentals.ui;

import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.ncsu.csc316.rentals.manager.RentalManager;

/**
 * UI class responsible for running the program
 * @author griffin
 *
 */
public class RentalManagerUI {

	/**
	 * Runs the program
	 * @param args Command Line arguments
	 */
	public static void main(String[] args) {
		boolean badFile = false;
		RentalManager r = null;
		Scanner input;
		do {
			System.out.print("Welcome to RentalManager!\nPlease enter an input file: ");
			input = new Scanner(System.in);
			String pathToFile = input.next();
			try {
				r = new RentalManager(pathToFile);
				badFile = false;
			} catch (FileNotFoundException e) {
				badFile = true;
				System.out.println("Invalid input file.");
			}
		} while (badFile);
		String b = "";
		while (!b.toLowerCase().equals("q")) {
			System.out.println("\nWhat would you like to do?");
			System.out.println("Generate cheapest rental sequence (1)");
			System.out.println("Query rentals for a specific day (2)");
			System.out.println("Quit (Q)");
			b = input.next();
			if (b.equals("1")) {
				System.out.print("Enter the start day: ");
				int start = input.nextInt();
				System.out.print("Enter the end day: ");
				int end = input.nextInt();
				System.out.println("\n" + r.getRentals(start, end));
			} else if (b.equals("2")) {
				System.out.print("Enter the day to search for: ");
				int day = input.nextInt();
				System.out.println("\n" + r.getRentalsForDay(day));
			}
		}
		System.out.println("Goodbye!");
		input.close();
	}
}