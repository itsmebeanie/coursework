package Project01;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * UserInput class represents the set of letters with methods that, given a
 * dictionary object, can produce a list of words
 * 
 * @author Kaitlin Gu
 * @creation September 18, 2014
 * 
 */

public class UserInput {
	// Private data field dictionaryWords, represents a Dictionary object from
	// file
	private Dictionary dictionaryWords;

	// UserInput, an ArrayList that stores all the permutations from user input
	private static ArrayList<String> UserInput = new ArrayList<String>();

	/**
	 * @return the userInput, an ArrayList of all the permutations from user
	 *         input
	 */
	public ArrayList<String> getUserInput() {
		return UserInput;
	}

	// UserInput constructor that takes in dictionary object as parameter
	UserInput(Dictionary obj) {
		dictionaryWords = obj;
	}

	/**
	 * Generates all permutations of a given string. "" represents a placeholder
	 * for the prefix.
	 * 
	 * @param s
	 *            , given string
	 */

	public void permutation(String s) {
		permutation(s, "");
	}

	/**
	 * Generates all the permutations of letters. Uses backtracking to generate
	 * permutations.
	 * 
	 * @param s
	 *            , represents the current set of letters to permute
	 * @param prefix
	 *            , represents the letters that have already been chosen
	 * @return
	 * @return
	 */
	private void permutation(String s, String prefix) {
		// Base case, if there is no more letters to use for permutation
		if (s.length() == 0) {
			UserInput.add(prefix + s);
		} else {
			// For the length of the string
			for (int i = 0; i < s.length(); i++) {
				// Represents the current letters used for permutation
				String currentPermutation = s.substring(i, i + 1);
				// Removes the first letter of the String.
				String newS = s.substring(0, i)
						+ s.substring(i + 1, s.length());
				String newPrefix = prefix + currentPermutation;

				// If the prefix is in the dictionary, then continue to permute
				if (dictionaryWords.binaryPrefix(prefix, 0,
						dictionaryWords.size()-1)) {
					UserInput.add(newPrefix);
					permutation(newS, newPrefix);
				}

			}

		}

	}
}
