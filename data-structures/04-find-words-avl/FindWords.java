package Project04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Find Words is the driver class that represents the Find Words game. Reads
 * input from the user and input file, and outputs the dictionary words that are
 * found based on the permutations of the 2 to 10 characters that the user inputs.
 * 
 * @author Joanna Klukowska
 * @creation September 24, 2014
 * 
 */
public class FindWords {

	/**
	 * This method runs when the program starts. It validates the file name and 
	 * reads its content. It creates a Dictionary object to be used in the game.
	 * It obtains the collection of letters from the user and validates it. 
	 * This method displays the final results to the terminal window. 
	 * 
	 * @param args an array containing command line argument,
	 *             the program expects one command line argument
	 *             that contains the filename of a dictionary to be used
	 */
	public static void main(String[] args) {
		
		//validate existence of command line arguments
		if (args.length < 1 ) {
			System.err.printf("Error: invalid number of arguments.\n"
					+ "Usage:\n\tFindWords dictionaryFile\n\n");
			System.exit(1);
		}
		
		//verify existence of the dictionary file 
		File dictFile = new File(args[0]);
		if (!dictFile.canRead()) {
			System.err.printf("Error: cannot read the dictionary file %s\n\n",
					args[0]);
			System.exit(1);
		}
		
		//open the dictionary file for reading
		Scanner dictIn = null;
		try {
			 dictIn = new Scanner (dictFile);
		} catch (FileNotFoundException e) {
			System.err.printf("Error: cannot read the dictionary file %s\n\n",
					args[0]);
			System.exit(1);
		}
		
		//read in all the words into an StringAVL object
		StringAVL dictWords = new StringAVL ();
		while (dictIn.hasNext()) {
			dictWords.insert(dictIn.next());
		}
		//create dictionary object
		Dictionary dict = new Dictionary(dictWords);
		
		
				
		//prompt user for letters to be used
		System.out.printf("Enter letters (2-10) to be used. Do not use spaces "
				+ "or any other non-letters. \n"
				+ "Hit enter to finish.\n\n");
		Scanner in = new Scanner (System.in);
		String lettersToUse = in.next();
		
		//create a LetterBag object
		LetterBag letters = new LetterBag(lettersToUse);
		
		//get a list of all words consisting of the given letters
		ArrayList <String> words  = letters.getAllWords( dict );
		
		
		System.out.println("All words containing your letters: ");
		for (int i = 0; i < words.size(); i++) {
			System.out.printf("\t%s%n", words.get(i));
		}
		// close input
		in.close();
				
	}

}
