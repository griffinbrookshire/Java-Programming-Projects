package edu.ncsu.csc316.wordcloud.manager;

import java.io.FileNotFoundException;

import edu.ncsu.csc316.dsa.list.ArrayBasedList;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.Map.Entry;
import edu.ncsu.csc316.dsa.map.SearchTableMap;
import edu.ncsu.csc316.dsa.queue.Queue;
import edu.ncsu.csc316.dsa.stack.Stack;
import edu.ncsu.csc316.dsa.wordcloud.io.InputFileReader;
import edu.ncsu.csc316.dsa.wordcloud.io.OutputFileWriter;
import edu.ncsu.csc316.dsa.wordcloud.io.WordCloudGenerator;
import edu.ncsu.csc316.wordcloud.factory.DSAFactory;
import java.util.Iterator;

/**
 * The WordCloudManager processes input from text files and generates a word
 * cloud based on the frequencies of the words that appear in the input file.
 * Optionally, the program accepts an input text file that contains filter words
 * that should be excluded from the frequency analysis.
 * 
 * /**
 * 
 * @author Griffin Brookshire (glbrook2)
 * @author Ty Smith (ltsmith3)
 */
public class WordCloudManager {
	
	/** list of words in the word cloud */
	private Map<String, Integer> wordList;

	/** list of banned words */
	private Map<String, String> bannedList;
	
	/** list of words in the word cloud */
	//private Map<String, Integer> orderedWordList;

	/**
	 * Constructs a new Word Cloud manager with the given input txt file and input
	 * filter file (See UC1)
	 * 
	 * @param pathToText   - the path to the input text file
	 * @param pathToFilter - the path to the input filter file
	 * @throws FileNotFoundException if either file does not exist
	 */
	public WordCloudManager(String pathToText, String pathToFilter) throws FileNotFoundException {
		bannedList = loadFilterWords(InputFileReader.readFilterFile(pathToFilter));
		wordList = loadFrequencies(InputFileReader.readFile(pathToText));
		//orderedWordList = null;
	}

	/**
	 * Constructs a new Word Cloud manager with the given input txt file. No filter
	 * file is used. (See UC1)
	 * 
	 * @param pathToText the path to file
	 * @throws FileNotFoundException throws an exception if the file cannot be found
	 */
	public WordCloudManager(String pathToText) throws FileNotFoundException {
		wordList = loadFrequencies(InputFileReader.readFile(pathToText));
		bannedList = null;
		//orderedWordList = null;
	}

	/**
	 * loads the frequency of every word and the words into this class
	 * 
	 * @param words a stack of words from the input files
	 * @return a map representing the words in sorted order
	 */
	private Map<String, Integer> loadFrequencies(Stack<String> words) {
		SearchTableMap<String, Integer> list = DSAFactory.getSearchTableMap();
		String word;
		while (!words.isEmpty()) {
			word = words.pop().toLowerCase();
			if (bannedList == null || bannedList.get(word) == null) { //should return true if not banned, false if it is
				if (list.get(word) != null) {
					 list.put(word, list.get(word) + 1);
				} else {
					list.put(word, 1);
				}
			}
		}
		return list;
	}

	/**
	 * loads in the filter words from a queue of words
	 * 
	 * @param words a queue containing the banned words
	 * @return a map representing the banned words
	 */
	private Map<String, String> loadFilterWords(Queue<String> words) {
		SearchTableMap<String, String> list = DSAFactory.getSearchTableMap();
		String holder = null;
		while (!words.isEmpty()) {
			holder = words.dequeue();
			list.put(holder, holder);
		}
		return list;
	}

	/**
	 * Returns a frequency report for a given word. The frequency report is in the
	 * format: "The word (w) is contained in the text X times." (See UC2)
	 * 
	 * @param word - the word for which to return the frequency
	 * @return the frequency report
	 */
	public String getFrequencyOfWord(String word) {
		@SuppressWarnings("unused")
		List<Object> list = DSAFactory.getIndexedList();
		Integer freq = wordList.get(word.toLowerCase());
		if (freq == null) {
			return "The word (" + word + ") is contained in the text 0 times.";
		}
		return "The word (" + word + ") is contained in the text " + freq + " times.";
	}

	/**
	 * Returns a report of the top X most frequent words. (See UC3)
	 * 
	 * @param numberOfWords - the number of words to include in the report
	 * @return the report of the top X most frequent words in the input text
	 */
	public String getTopWordsReport(int numberOfWords) {
		if (numberOfWords <= 0) {
			return "MostFrequentWords[\n   Number of words must be greater than 0.\n]";
		}
		if (numberOfWords > wordList.size()) {
			numberOfWords = wordList.size();
		}
		String topWords = "MostFrequentWords[\n";
		int maxFreq = 0;
		String maxWord = "";
		ArrayBasedList<Entry<String, Integer>> input = (ArrayBasedList<Entry<String, Integer>>) wordList.entrySet();
		for (int i = 0; i < numberOfWords; i++) {
			int idx = 0;
			for (int j = 0; j < input.size(); j++) {
				Integer val = input.get(j).getValue(); // one less "get" by doing this
				if (val > maxFreq) {
					maxFreq = val;
					maxWord = input.get(j).getKey();
					idx = j;
				}
			}
			input.remove(idx);
			topWords = topWords + "   " + maxWord + " - " + maxFreq + "\n";
			maxFreq = 0;
		}
		return topWords + "]";
	}

	/**
	 * outputs the word cloud as html text to a file
	 * 
	 * @param title   the title of the wordcloud
	 * @param output  the name of the output file
	 * @param wordNum the number of words
	 * @throws FileNotFoundException If file does not exist
	 */
	public void outputWordCloud(String title, String output, int wordNum) throws FileNotFoundException {
		OutputFileWriter.outputFile(output, WordCloudGenerator.getWordCloudHTML(title, getTopWordsList(wordList.size())));
	}
	
//	private boolean contains(List<String> list, String entry) {
//		for (int i = 0; i < list.size(); i++) {
//			if (list.get(i).equals(entry)) {
//				return true;
//			}
//		}
//		return false;
//	}
	
	private Map<String, Integer> getTopWordsList(int numberOfWords) {
		String maxWord = "";
		int maxFreq = 0;
		ArrayBasedList<Entry<String, Integer>> input = (ArrayBasedList<Entry<String, Integer>>) wordList.entrySet();
		Map<String, Integer> output = DSAFactory.getUnorderedMap();
		for (int i = 0; i < numberOfWords; i++) {
			for (int j = 0; j < input.size(); j++) {
				if (input.get(j).getValue() > maxFreq && !containsKey(output, input.get(j).getKey())) {
					maxWord = input.get(j).getKey();
					maxFreq = input.get(j).getValue();
				}
			}
			output.put(maxWord, maxFreq);
			maxFreq = 0;
		}
		return output;
	}

	private boolean containsKey(Map<String, Integer> check, String key) {
		Iterator<String> it = check.iterator();
		while (it.hasNext()) {
			if (it.next().equals(key)) {
				return true;
			}
		}
		return false;
	}

}