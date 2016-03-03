package Project03;

/**
 * 
 * Postfix Exception for errors encountered with evaluating postfix.
 * 
 * @author Joanna Klukowska
 * @creation November 10, 2014
 *
 */

public class PostFixException extends Exception {

	// Default constructor
	public PostFixException() {
		super();
	}

	// Constructor that takes in a message
	public PostFixException(String message) {
		super(message);
	}
}