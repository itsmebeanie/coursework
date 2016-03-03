package Project03;

import java.io.*;
import java.util.Scanner;

/**
 * Class provides the user interface for Console Calculator. The program needs
 * to be called with two command line arguments. One contains mathematical
 * expressions to be used by the calculator and the other contains the
 * results of these expressions.
 * 
 * @author Kaitlin Gu
 * @creation 2 October 2014
 *
 */
public class ConsoleCalculator {
	/**
	 * Validates the file name and read its content.
	 * 
	 * @param args
	 *            an array containing command line argument, the program expects
	 *            two command line arguments. One that contains mathematical
	 *            expressions to be used by the calculator and the other that contains
	 *            the results of the expressions.
	 * @throws PostFixException
	 */

	public static void main(String[] args) throws PostFixException {

		// validate user entered correct number of command line arguments
		if (args.length < 2) {
			System.err.print("Error: Invalid number of arguments. The program needs 2 arguments.");
			System.exit(1);
		}

		// validate existence of input file
		File inputFile = new File(args[0]);
		if (!inputFile.canRead()) {
			System.err.println("Error: Cannot read the input file");
			System.exit(1);
		}


		// Open input file for reading
		Scanner inputIn = null;
		try {
			inputIn = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			System.err.println("Error: Cannot read the input file " + args[0]);
			System.exit(1);
		}

		// Open output file for writing
		PrintWriter outputIn = null;
		try {
			outputIn = new PrintWriter(args[1]);
		} catch (FileNotFoundException e) {
			System.err.println("Error: Cannot write to the output file "
					+ args[1]);
			System.exit(1);
		}

		// Read from input file
		while (inputIn.hasNext()) {
			// Infix expression to evaluate
			String infixExpression = inputIn.nextLine();
			try {
				// Convert infix to postfix 
				String postFixExpression = ExpressionTools
						.infixtoPostfix(infixExpression);
				int result = ExpressionTools.evaluate(postFixExpression);
				// Print out result if there are no exceptions
				outputIn.println(result);
			} catch (PostFixException e) {
				outputIn.println("INVALID");
			}
			catch (ArithmeticException e){
				outputIn.println("INVALID");
			}

		}
		outputIn.close();
	}

}
