package Project01;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * Find Words is the driver class that represents the Find Words game. Reads
 * input from the user and input file, and outputs the dictionary words that are
 * found based on the permutations of the 2-10 characters that the user inputs.
 * 
 * @author Kaitlin Gu
 * @creation September 24, 2014
 * 
 */
public class FindWords {

	public static void main(String[] args) throws IOException {
		// If there are no arguments then the program prints an error message
		if (args.length < 1) {
			System.out.print("Must enter a file.");
			System.exit(1);
		}

		try {
			File dictionaryFile = new File(args[0]);

			Scanner fin = new Scanner(dictionaryFile);

			System.out.println("Here is your file: " + dictionaryFile.getAbsolutePath());

			// If the dictionary file does not exist then the program prints an
			// error message
			if (!dictionaryFile.exists()) {
				System.out.println("File " + args[0] + "does not exist");
			}

			// If the program cannot read the file then an error message is
			// printed
			else if (!dictionaryFile.canRead()) {
				System.out
						.println("File " + args[0] + " cannot be read.");
			}

			// Create a new dictionary object
			Dictionary dict = new Dictionary();

			// While the file has a next line, add it to the dictionary
			while (fin.hasNext()) {
				dict.add(fin.nextLine());
			}

			// Create a Scanner object
			Scanner input = new Scanner(System.in);
			// Prompt user for a set of 2-10 characters
			System.out.print("Enter a set of letters "
					+ "(Only 2-10 characters are permitted): ");
			String userInput = input.nextLine();
			char[] charArray = userInput.toCharArray();

			// If the user has entered more than 10 or less than 2 characters,
			// then print error message
			if (charArray.length > 10) {
				System.out
						.print("Too many characters. Cannot enter more than 10 characters");
				System.exit(2);
			} else if (charArray.length < 2) {
				System.out
						.println("Too few characters. Cannot enter less than 2 characters");
				System.exit(3);

			}
			// If user enters non-letters, print an error message
			for (int i = 0; i < charArray.length; i++) {
				if (!Character.isLetter(charArray[i])) {
					System.out.println("You must enter only letters.");
					System.exit(4);
				}
			}

			// Convert all characters to lower-case
			for (int i = 0; i < charArray.length; i++) {
				charArray[i] = Character.toLowerCase(charArray[i]);
			}
			

			// Convert character array to one String
			String userString = new String(charArray);

			// Create a UserInput object
			UserInput userWords = new UserInput(dict);

			// Generate permutations of a String
			userWords.permutation(userString);

			// Create a ArrayList that contains permutations generated from user
			// Input
			ArrayList<String> userPermutations = userWords.getUserInput();

			// Create an ArrayList that will store the words found in the
			// dictionary from user input
			ArrayList<String> foundWords = new ArrayList<String>();
			
			// If the permutations match a dictionary word, then add it to the list of found words
			for (int i = 0; i < userPermutations.size(); i++) {
				if (dict.binaryWord(userPermutations.get(i), 0, dict.size()-1) == 0) {
					foundWords.add(userPermutations.get(i));

				}

			}

			// Remove any duplicates in the array list

			Collections.sort(foundWords);

			for (int i = 0; i < foundWords.size(); i++) {
				foundWords = removeDuplicates(foundWords);
			}

			
			// Print out the found words to console
			if (foundWords.size()>0){
				System.out
				.println("Here are the words found in the dictionary: ");
			}
			
			else {
				System.out.println("Sorry! There are no matching words in the dictionary");
			}
		
			for (int i = 0; i < foundWords.size(); i++) {
				System.out.println(foundWords.get(i));
			}
			input.close();

		} catch (FileNotFoundException e) {
			System.out.println("No such file found: " + args[0]);
		}

	}

	/**
	 * Method that removes duplicates given an ArrayList of Strings
	 * 
	 * @param ArrayList
	 *            <String>list
	 * @return list without duplicates
	 */

	public static ArrayList<String> removeDuplicates(ArrayList<String> list) {
		// loop through the array list
		for (int i = 1; i < list.size(); i++) {
			String a = list.get(i);
			String b = list.get(i - 1);
			// if list elements are equal then remove one
			if (a.equals(b)) {
				list.remove(a);
			}
		}

		// return the list without duplicates
		return list;

	}

}
