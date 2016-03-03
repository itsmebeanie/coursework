package Project03;

import java.util.Scanner;

/**
 * 
 * This class provides methods for infix to postfix conversion and for postfix
 * evaluation.
 * 
 * @author Kaitlin Gu
 * @creation Nobember 3rd, 2014
 *
 */

public class ExpressionTools {
	/**
	 * Method that tests precedence 
	 * 
	 * @param operator
	 * @return int the value of the precedence
	 */
	private static int precedence(char operator) {
		// test the precedence
		switch (operator) {
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
			return 2;
		default:
			return 0;
		}

	}

	/**
	 * Method that evaluates whether character is an operator
	 * @param c, character
	 * @return true if the character is an operator, false if is not
	 */
	private static boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/';
	}

	/**
	 * 
	 * Converts infix expression to postfix expression
	 * 
	 * @param infix
	 *            infix expression
	 * @return String containing postfix expression
	 */
	public static String infixtoPostfix(String infix) {
		Scanner tokenizer = new Scanner(infix);
		// postfix string expression
		String postfix = "";
		// Create an operator stack
		LinkedStack<Character> operatorStack = new LinkedStack<Character>();

		// For each token in the input infix expression
		while (tokenizer.hasNext()) {
			String token = tokenizer.next();
			char c = token.charAt(0);
			// If the token is an operand
			if (Character.isDigit(c)) {
				// append it to the postfix string expression
				postfix += (token + " ");
			}

			// if the token is a left brace
			else if (c == '(') {
				// push it onto the operator stack
				operatorStack.push(new Character(c));
			}
			// else if the token is an operator
			else if (isOperator(c)) {
				// while the stack is not empty
				while (!operatorStack.isEmpty()) {
					char top = (((Character) operatorStack.peek()).charValue());
					// if the top element on stack has higher precedence
					if (precedence(top) >= precedence(c)) {
						// pop stack and append to postfix string expression
						postfix += (top + " ");
						operatorStack.pop();
					} else {
						break;
					}
				}
				operatorStack.push(new Character(c));

			}
			// token is right brace
			else if (c == ')') {
				// while the operator stack is not empty
				while (!operatorStack.isEmpty()) {
					char top = (((Character) operatorStack.peek()).charValue());
					// if the top is not a matching left brace
					if (top != '(') {
						// pop operator stack and append it to postfix string expression
						postfix += (top + " ");
						operatorStack.pop();
					} else {
						// pop the left brace and discard
						operatorStack.pop();
						break;
					}
				}

			}

		}
		// while the stack is not empty
		while (!operatorStack.isEmpty()) {
			// pop the operator stack and append to postfix string
			// expression
			char top = (((Character) operatorStack.pop()).charValue());
			postfix += (top + " ");
		}

		return postfix;

	}

	/**
	 * Evaluates post fix expression. Taken from lecture05_adt notes,
	 * PostFixEvaluating.java
	 * 
	 * @author Joanna Klukowska
	 * 
	 * @param expression
	 *            Postfix expression
	 * @return int Result of postfix expression
	 * @throws PostFixException
	 */
	public static int evaluate(String expression) throws PostFixException {
		LinkedStack<Integer> stack = new LinkedStack<Integer>();

		int value;
		String operator;

		int operand1;
		int operand2;

		int result = 0;

		Scanner tokenizer = new Scanner(expression);

		while (tokenizer.hasNext()) {
			if (tokenizer.hasNextInt()) {
				// Process operand.
				value = tokenizer.nextInt();

				stack.push(value);
			} else {
				// Process operator.
				operator = tokenizer.next();

				// Obtain second operand from stack.
				if (stack.isEmpty()) {
					tokenizer.close();
					throw new PostFixException(
							"Not enough operands - stack underflow");
				}
				operand2 = stack.peek();
				stack.pop();

				// Obtain first operand from stack.
				if (stack.isEmpty()) {
					tokenizer.close();
					throw new PostFixException(
							"Not enough operands - stack underflow");
				}
				operand1 = stack.peek();
				stack.pop();

				// Perform operation.
				if (operator.equals("/")) {
					result = operand1 / operand2;
					if (operand2 == 0) {
						throw new ArithmeticException(
								"Arithmetic exception: Cannot divide by 0");
					}
				} else if (operator.equals("*"))
					result = operand1 * operand2;
				else if (operator.equals("+"))
					result = operand1 + operand2;
				else if (operator.equals("-"))
					result = operand1 - operand2;
				else {
					tokenizer.close();
					throw new PostFixException("Illegal symbol: " + operator);
				}

				// Push result of operation onto stack.
				stack.push(result);
			}
		}

		tokenizer.close();

		// Obtain final result from stack.
		if (stack.isEmpty())
			throw new PostFixException("Not enough operands - stack underflow");
		result = stack.peek();
		stack.pop();

		// Stack should now be empty.
		if (!stack.isEmpty())
			throw new PostFixException("Too many operands - operands left over");

		// Return the final.
		return result;
	}
}
