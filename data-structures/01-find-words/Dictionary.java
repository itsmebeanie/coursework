package Project01;

import java.io.*;
import java.util.*;

/**
 * This is a class that represents and stores the dictionary. Contains 2 search
 * methods. One that allows you to search for prefix and the other allows you to
 * search for words.
 * 
 * @author Kaitlin Gu
 * @creation September 28, 2014
 * 
 */
public class Dictionary {

	// Private data fields
	private ArrayList<String> dict;

	Dictionary() {
		dict = new ArrayList<String>();
	}

	public void add(String s) {
		dict.add(s);
	}

	public int size() {
		return dict.size();
	}

	public String get(int i) {
		return dict.get(i);
	}

	public ArrayList<String> getList() {
		return dict;
	}

	/**
	 * Binary recursive search that checks if a given prefix is in an array of
	 * strings
	 * 
	 * @param prefix
	 * @param min
	 *            , lower bound of list
	 * @param max
	 *            , upper bound of list
	 * @return true if the word is found
	 * 
	 */
	public boolean binaryPrefix(String prefix, int min, int max) {
		// Base case
		// If the array is empty, return false to signify the word is not found
		if (max < min) {
			return false;
		} else {
			int mid = (max + min) / 2;

			if (this.dict.get(mid).startsWith(prefix)) {
				// If the word starts with the prefix, return true
				if (mid == 0 || !this.dict.get(mid - 1).startsWith(prefix)) {
					return true;
				} else {
					return binaryPrefix(prefix, min, mid - 1);
				}
			} else if (prefix.compareTo(this.dict.get(mid)) > 0) {
				return binaryPrefix(prefix, mid + 1, max);
			} else {
				return binaryPrefix(prefix, min, mid - 1);
			}

		}
	}

	/**
	 * Binary recursive search that checks if a given word is in an array of
	 * strings
	 * 
	 * @param word
	 *            , word to be checked
	 * @param min
	 *            , lower bound of list
	 * @param max
	 *            , upper bound of list
	 * @return 0 if the word is found
	 */

	public int binaryWord(String word, int min, int max) {
		// Base case
		// If the array is empty, return -1 to signify the word is not found
		if (max < min) {
			return -1;
		} else {
			int mid = (max + min) / 2;
			if (this.dict.get(mid).compareTo(word) > 0) {
				return binaryWord(word, min, mid - 1);
			} else if (this.dict.get(mid).compareTo(word) < 0) {
				return binaryWord(word, mid + 1, max);
			} else {
				return 0;
			}
		}
	}

}