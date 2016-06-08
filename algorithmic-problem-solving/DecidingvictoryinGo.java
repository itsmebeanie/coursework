import java.io.*;
import java.util.*;

/**
 DFS on empty spaces and check to see if white and black are surrounding the empty space.
 */
public class DecidingvictoryinGo {
	// static int M;
	static int N = 9; // always 9
	static boolean[][] hasVisited;
	static char[][] grid;
	static boolean isBlackSurrounded = false;
	static boolean isWhiteSurrounded = false;
	// static int max;
	static int surroundingArea = 0;

	/** Adapting continents code **/

	public static void dfs(int x, int y) {
		// out of bounds
		if (x < 0 || x >= N || y < 0 || y >= N) {
			return;
		}
		if (grid[x][y] == 'O') {
			isWhiteSurrounded = true;
			return;
		} else if (grid[x][y] == 'X') {
			isBlackSurrounded = true;
			return;
		}
		if (hasVisited[x][y]) {
			return;
		}
		if (grid[x][y] == '.') {
			surroundingArea++;
		}

		hasVisited[x][y] = true;
		dfs(x + 1, y);
		dfs(x, y + 1);
		dfs(x - 1, y);
		dfs(x, y - 1);

	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		int T = Integer.parseInt(in.readLine());
		while (T-- > 0) {
			// System.out.println(M+"" + N);
			grid = new char[N][N];
			hasVisited = new boolean[N][N];

			for (int i = 0; i < N; i++)
				grid[i] = in.readLine().toCharArray();
			int blackScore = 0;
			int whiteScore = 0;

			// Count the number of pieces
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					// increment black pieces
					if (grid[i][j] == 'X') {
						blackScore++;
					}
					// increment white pieces
					else if (grid[i][j] == 'O') {
						whiteScore++;
					}
				}
			}

			// check if black surrounded or if white surrounded
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (hasVisited[i][j] == false && grid[i][j] == '.') {
						// area of empty intersections
						surroundingArea = 0;
						// nothing is surrounded so far
						isBlackSurrounded = false;
						isWhiteSurrounded = false;
						dfs(i, j);
						// System.out.println(surroundingArea);
						// You will only increment if there is no surrounding
						// pieces
						// of the other piece
						if (isBlackSurrounded == false) { // if black pieces do
															// not surround this
															// space
							// increment for white
							whiteScore += surroundingArea;
						} else if (isWhiteSurrounded == false) { // if white
																	// pieces do
																	// not
																	// surround
																	// this
																	// space
							// increment for black
							blackScore += surroundingArea;

						}
					}

				}
			}
			System.out.println("Black " + blackScore + " White " + whiteScore); // print

		}
	}

}
