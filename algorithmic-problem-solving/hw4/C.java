import java.io.*;
import java.util.*;

public class C {
	static int adjacencyMatrix[][];
	static int dominationMatrix[][];
	static int[] isDomination;
	static int[]state;
	static int DISCOVERED = 1;
	public static void dfs(int node, int itself, int N){
		if (node == itself){
			return;
		}
		// label node a discovered 
		state[node] = DISCOVERED;
		// for all adjacency/ possible paths
		for (int i = 0; i < N; i++){
			if ((adjacencyMatrix[node][i] == 1) && (state[i] != DISCOVERED)){
				// recursively call dfs on i
				dfs(i,itself,N);
			}
		}
	}
	public static void main(String[]args){
		Scanner in = new Scanner(new InputStreamReader(System.in));
		int T = in.nextInt();
		int caseNum = 1;
		while (T-->0){
			int N = in.nextInt();
			
			// Create matrix that represents adjacnecy 
			adjacencyMatrix = new int[N][N];
			// Create matrix that represents domination
			dominationMatrix = new int[N][N];
			/**
			 * If the j-th (0 based) integer of i-th (0 based) 
			 * line is ‘1’, it means that there is an edge from 
			 * node i to node j and similarly a ‘0’ means there 
			 * is no edge.
			 */
			
			for (int i = 0; i< N; i++){
				for (int j = 0; j<N; j++){		
					int isEdge = in.nextInt();
					if (isEdge == 1){
						adjacencyMatrix[i][j] = 1;
					}
				}
			}
			// array to mark state
			state = new int[N];
			// array to check if path creates domination
			isDomination = new int[N];
			// mark roots as discovered
			dfs (0, -100000, N);
			// currently marks all roots
			isDomination = state;
			for (int i = 0; i<N;i++){
				state = new int[N];
				// run domination from 0 to the desired node to see if path exist
				dfs(0, i, N);
				for (int j = 0; j<N;j++){
					if (isDomination[j] == 1 && state[j] != DISCOVERED){
						dominationMatrix[i][j] = 1;
					}
				}
			}
			
			System.out.printf("Case %d:\n", caseNum);
			
			// Printing 
			String inBetween = "";
			int bound = 2*N+1;
			for (int i = 0; i<bound; i++){
				if (i == 0 || i == bound-1){
					inBetween += "+";
				}
				
				else{
					inBetween+= "-";
				}
			}
			for (int i = 0; i<N; i++){
				System.out.println(inBetween);
				System.out.print("|");

				for (int j = 0; j <N; j++){
					if(dominationMatrix[i][j] == 1){
						System.out.print("Y");
					}
					else{
						System.out.print("N");

					}
					System.out.print("|");
				}
				System.out.println();
			}
			System.out.println(inBetween);
			caseNum++;
			
		}
		
	}
}