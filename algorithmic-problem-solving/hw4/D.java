import java.util.*;
import java.io.*;

public class D {
	/**
	 * Grid traversal tricks: 
	 * Pad the edges with invalid characters 
	 * (Saves you from having to handle boundary conditions)
	 * Model the directions as diffs in directions.
	 *  E.g., model the four cardinal directions as { (-1, 0), (0, 1), (1, 0), (0, -1) }
	 */
	
	
	static int[][] cardinal = new int[][]{
		{-1, 0}, 
		{0, 1}, 
		{1, 0}, 
		{0, -1}
		};
	static char[][]grid;
	static int[][]status;
	static int numGold;
	static int row, column;
	static int DISCOVERED = 1;

	public static boolean withinGrid(int nodex, int nodey){
		if (nodex<row && nodey<column && nodex>=0 && nodey>=0){
			return true;
		}
		return false;
	}
	public static void dfs(int nodex, int nodey){
		if (withinGrid(nodex,nodey) == false){
			return;
		}
		
		// also can't visit node twice
		if (status[nodex][nodey]==DISCOVERED){
			return;
		}
		// running into a wall means stop going
		if (grid[nodex][nodey] == '#' ){
			return;
		}
		
		// mark node as visited
				status[nodex][nodey] = DISCOVERED;
		// increase the number of gold for every time you see G
		if (grid[nodex][nodey] == 'G'){
			numGold++;
		}
		
		
		// traversing through cardinal directions
		for (int i = 0; i< 4; i++){
			int moveX = nodex+cardinal[i][0];
			int moveY = nodey+cardinal[i][1];
			if (grid[moveX][moveY] == 'T'){
				return;
			}
		}
		for (int i = 0; i< 4; i++){
			int moveX = nodex+cardinal[i][0];
			int moveY = nodey+cardinal[i][1];
			if (withinGrid(moveX, moveY))
				dfs(moveX,moveY);
		}
	}
	
	/**
	 * P – the player’s starting position
	 * G – a piece of gold
	 * T – a trap
	 * # – a wall
	 * . – normal floor
	 * @param args
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static void main(String[]args) throws NumberFormatException, IOException{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line;
		while ((line = in.readLine())!= null){
			numGold = 0;
			StringTokenizer rc = new StringTokenizer(line);
			column = Integer.parseInt(rc.nextToken());
			row = Integer.parseInt(rc.nextToken());
			
			
			// represents grid
			grid = new char[row][column];
			status = new int[row][column];
			for (int i = 0; i < row; i++){
				String input = in.readLine();
				for (int j=0;j<column;j++){
					grid[i][j] = input.charAt(j);	
				}
			}
			
			// reaching p indicates start
			
			for (int i = 0; i<row; i++){
				for (int j =0; j<column; j++){
					// if P is start then run dfs
					if (grid[i][j] == 'P'){
						dfs(i,j);
						break;
					}
				}
			}
			System.out.println(numGold);
			
			
		}
		
	}

}
