package Project04;

/**
 *
 * This class represents and stores a dictionary of words. It provides 2 ways of
 * searching through the dictionary. One that allows you to search for prefix and
 * the other allows you to search for words.
 * 
 * @author Kaitlin Gu
 * @creation September 28, 2014
 * 
 */

public class Dictionary {
	// storage for the words
	private StringAVL words;

	/**
	 * Creates an empty Dictionary object (no words).
	 */
	public Dictionary() {
		words = new StringAVL();
	}

	/**
	 * Creates a Dictionary object containing all words from the listOfWords
	 * passed as a parameter.
	 * 
	 * @param listOfWords
	 *            the list of words to be stored in the newly created Dictionary
	 *            object
	 */
	public Dictionary(StringAVL listOfWords) {
		words = listOfWords;

	}

	/**
	 * The actual method providing recursive implementation search of AVLTree
	 * 
	 * @param word
	 *            the word to look for in this Dictionary object.
	 * @return true if the word is in this Dictionary object, false otherwise
	 */
	public boolean isWordInDictionary(String word) {
		return words.contains(word);
	}


	/**
	 * The actual method providing recursive implementation of the binary search
	 * for the prefix of StringAVL tree
	 * 
	 * @param prefix
	 *            the prefix to look for in this Dictionary object.
	 * @return true if at least one word with the specified prefix exists in
	 *         this Dictionary object, false otherwise
	 */
	public boolean isPrefixInDictionary(String prefix) {
		return words.findPrefix(prefix);
	}


}
