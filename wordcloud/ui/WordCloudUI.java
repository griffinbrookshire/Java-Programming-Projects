/**
 * 
 */
package edu.ncsu.csc316.wordcloud.ui;

import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.ncsu.csc316.wordcloud.manager.WordCloudManager;

/**
 * Executes the WordCloud program
 * 
 * @author Griffin Brookshire (glbrook2)
 * @author Ty Smith (ltsmith3)
 */
public class WordCloudUI {

	/**
	 * Runs WordCloudUI
	 * 
	 * @param args command-line arguments array
	 * @throws FileNotFoundException if file is not found
	 */
	public static void main(String[] args) throws FileNotFoundException {
		boolean badFile = false;
		WordCloudManager w = null;
		Scanner input;
		do {
			System.out.print("Welcome to WordCloud!\nPlease enter an input file: ");
			input = new Scanner(System.in);
			String pathToText = input.next();
			String pathToFilter = null;
			System.out.print("Would you like to provide a filter file? (Y/N) ");
			String a = input.next();
			System.out.print(a);
			if (a.toLowerCase().equals("y")) {
				System.out.print("\nPlease enter a filter file: ");
				pathToFilter = input.next();
				try {
					w = new WordCloudManager(pathToText, pathToFilter);
				} catch (FileNotFoundException e) {
					badFile = true;
				}
			} else {
				try {
					w = new WordCloudManager(pathToText);
				} catch (FileNotFoundException e) {
					badFile = true;
				}
			}
		} while (badFile);
		String b = "";
		while (!b.toLowerCase().equals("q")) {
			System.out.println("What would you like to do?");
			System.out.println("Retrieve frequency of a word (1)");
			System.out.println("Report most frequent words (2)");
			System.out.println("Produce WordCloud (3)");
			System.out.println("Quit (Q)");
			b = input.next();
			if (b.equals("1")) {
				System.out.println("Enter the word to search for:");
				String holder = input.next();
				System.out.println("\n" + w.getFrequencyOfWord(holder));
			} else if (b.equals("2")) {
				System.out.println("How many words to search for:");
				int holder = input.nextInt();
				System.out.println("\n" + w.getTopWordsReport(holder));
			} else if (b.equals("3")) {
				System.out.println("How many words to include in the word Cloud:");
				int words = input.nextInt();
				System.out.println("Title of the word cloud:");
				String title = input.next();
				System.out.println("What file should the html text be output to");
				String file = input.next();
				w.outputWordCloud(title, file, words);
			}
		}
		System.out.println("Goodbye!");
		input.close();
	}

}
