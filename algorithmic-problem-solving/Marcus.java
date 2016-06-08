import java.io.*;
import java.util.*;

public class Marcus {

	static String word = "IEHOVA#";

	public static void main(String[] args) throws NumberFormatException, IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(in.readLine());

		while (T-- > 0) {
			StringTokenizer steps = new StringTokenizer(in.readLine());
			int rows = Integer.parseInt(steps.nextToken(), 10);
			int columns = Integer.parseInt(steps.nextToken(), 10);

			int[][] markings = new int[rows][columns];

			for (int i = 0; i < rows; i++) {
				String incoming = in.readLine();
				for (int j = 0; j < columns; j++) {
					markings[i][j] = incoming.charAt(j);
				}
			}

			int currRow = -1;
			int currColumn = -1;
			for (int i = rows - 1; i >= 0; i--) {
				for (int j = 0; j < columns; j++) {
					if (markings[i][j] == '@') {
						currRow = i;
						currColumn = j;
						break;
					}

				}
			}

			ArrayList<String> directions = new ArrayList<String>();
			recursiveSol(directions, markings, currRow, currColumn, 0);
		}
	}

	public static void recursiveSol(ArrayList<String> directions, int[][] markings, int currRow, int currColumn,
			int current) {
		if (current == 7) {
			for (int i = 0; i < directions.size(); i++) {
				System.out.print(directions.get(i));
				if (i < directions.size() - 1)
					System.out.print(" ");

			}
			System.out.println();
		} else {
			if (currRow > 0 && markings[currRow - 1][currColumn] == word.charAt(current)) {
				directions.add("forth");
				recursiveSol(directions, markings, currRow -= 1, currColumn, current += 1);
			} else if (currColumn < markings[currRow].length - 1 && currRow < markings.length
					&& markings[currRow][currColumn + 1] == word.charAt(current)) {
				directions.add("right");
				recursiveSol(directions, markings, currRow, currColumn += 1, current += 1);
			} else if (currColumn > 0 && currRow < markings.length
					&& markings[currRow][currColumn - 1] == word.charAt(current)) {
				directions.add("left");
				recursiveSol(directions, markings, currRow, currColumn -= 1, current += 1);
			}

		}
	}
}