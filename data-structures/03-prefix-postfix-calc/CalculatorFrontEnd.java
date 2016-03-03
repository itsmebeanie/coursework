package Project03;

import processing.core.PApplet;

/**
 * Calculator front end that allows user to input an infix expression to be
 * evaluated
 * 
 * @author Kaitlin Gu
 * @creation November 8, 2014
 *
 */
public class CalculatorFrontEnd extends PApplet {

	// Infix expression
	private static String expression = "";
	// Dimensions of button
	int x = 10;
	int y = 100;
	int w = 70;
	int h = 70;

	// Calculator window with blue background
	public void setup() {
		size(340, 430);
		background(74, 155, 185);

	}

	public void draw() {
		fill(255);
		// First row of four rectangles
		rect(x, y, w, h);
		rect(x + 80, y, w, h);
		rect(x + 160, y, w, h);
		rect(x + 240, y, w, h);
		// Second row of four rectangles
		rect(x, y + 80, w, h);
		rect(x + 80, y + 80, w, h);
		rect(x + 160, y + 80, w, h);
		rect(x + 240, y + 80, w, h);
		// Third row of four rectangles
		rect(x, y + 160, w, h);
		rect(x + 80, y + 160, w, h);
		rect(x + 160, y + 160, w, h);
		rect(x + 240, y + 160, w, h);
		// Fourth row of four rectangles
		rect(x, y + 240, w, h);
		rect(x + 80, y + 240, w + 80, h);
		rect(x + 240, y + 240, w, h);


		textSize(15);
		fill(155);
		// First row containing 1, 2, 3 +
		text("1", 40, 140);
		text("2", 120, 140);
		text("3", 200, 140);
		text("+", 280, 140);
		// Second row containing 4 5 6 -
		text("4", 40, 220);
		text("5", 120, 220);
		text("6", 200, 220);
		text("-", 280, 220);
		// Third row containing 7 8 9 *
		text("7", 40, 300);
		text("8", 120, 300);
		text("9", 200, 300);
		text("*", 280, 300);
		// Fourth row containing 0 = / 
		text("0", 40, 380);
		text("=", 160, 380);
		text("/", 280, 380);
	}

	// When the mouse is pressed, each button evaluates to a different value
	public void mousePressed() {
		background(74, 155, 185);

		// first row
		if (mouseX > x && mouseX < x + w && mouseY > y && mouseY < y + h) {
			expression += "1";
		}
		if (mouseX > x + 80 && mouseX < x + 80 + w && mouseY > y
				&& mouseY < y + h) {
			expression += "2";
		}
		if (mouseX > x + 160 && mouseX < x + 160 + w && mouseY > y
				&& mouseY < y + h) {
			expression += "3";
		}

		if (mouseX > x + 240 && mouseX < x + 240 + w && mouseY > y
				&& mouseY < y + h) {
			expression += " + ";
		}
		// second row
		if (mouseX > x && mouseX < x + w && mouseY > y + 80
				&& mouseY < y + 80 + h) {
			expression += "4";
		}
		if (mouseX > x + 80 && mouseX < x + 80 + w && mouseY > y + 80
				&& mouseY < y + 80 + h) {
			expression += "5";
		}
		if (mouseX > x + 160 && mouseX < x + 160 + w && mouseY > y + 80
				&& mouseY < y + 80 + h) {
			expression += "6";
		}

		if (mouseX > x + 240 && mouseX < x + 240 + w && mouseY > y + 80
				&& mouseY < y + 80 + h) {
			expression += " - ";
		}

		// third row
		if (mouseX > x && mouseX < x + w && mouseY > y + 160
				&& mouseY < y + 160 + h) {
			expression += "7";
		}
		if (mouseX > x + 80 && mouseX < x + 80 + w && mouseY > y + 160
				&& mouseY < y + 160 + h) {
			expression += "8";
		}
		if (mouseX > x + 160 && mouseX < x + 160 + w && mouseY > y + 160
				&& mouseY < y + 160 + h) {
			expression += "9";
		}

		if (mouseX > x + 240 && mouseX < x + 240 + w && mouseY > y + 160
				&& mouseY < y + 160 + h) {
			expression += " * ";
		}

		// fourth row

		if (mouseX > x && mouseX < x + w && mouseY > y + 240
				&& mouseY < y + 240 + h) {
			expression += "0";
		}
		if (mouseX > x + 240 && mouseX < x + 240 + w && mouseY > y + 240
				&& mouseY < y + 240 + h) {
			expression += " / ";
		}

		fill(255);
		textSize(16);
		// display expression on screen
		text(expression, 25, 50);
		// evaluate expression if = sign is pressed. Displays invalid if there is an error.
		if (mouseX > x + 80 && mouseX < x + 80 + w + 80 && mouseY > y + 240
				&& mouseY < y + 240 + h) {
			String postfix = ExpressionTools.infixtoPostfix(expression);
			int result;
			try {
				result = ExpressionTools.evaluate(postfix);
				// redraw background
				background(74, 155, 185);
				// display result
				text(result, 25, 50);
			} catch (PostFixException e) {
				// redraw background
				background(74, 155, 185);
				text("INVALID", 25, 50);
			} catch (ArithmeticException e) {
				// redraw background
				background(74, 155, 185);
				text("INVALID", 25, 50);
			}
			// reset expression
			expression = "";
		}

	}

}