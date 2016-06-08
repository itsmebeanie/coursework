import java.io.*;
import java.util.*;
// using max flow
// • Maximum Cardinality Bipartite Matching (graph modeling + Max Flow)
// Used http://acm.hust.edu.cn/vjudge/contest/viewSource.action?id=2095562 -> given code by lecture notes
// https://www.youtube.com/watch?v=GiN3jRdgxU4
// recieved help on this code -- also used book
// Uses Edmonds Karp Algorithm
public class Sabotage {
	static int pred[];
	static int adj[][];
	static int residual[][];
	StringBuilder ansSet;
	// types of shirts
	// static ArrayList<String>TYPES = new
	// ArrayList<String>(Arrays.asList("XXL","XL","L","M","S", "XS"));
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		while (true) {
			int n = in.nextInt();
			int m = in.nextInt();
			if (n == 0 && m == 0)
				break;
			
			adj = new int[n][n]; //use adjacency matrix
			residual = new int[n][n];
			// int source = 1;
			// int sink = 2; // not 1 and 2
			int source = 0;
			int sink = 1;

			for (int i = 0; i < m; i++) {
				int u = in.nextInt() - 1; // city a node
				int v = in.nextInt() - 1; // city b node
				int c = in.nextInt(); // cost
				
				adj[u][v] = c;
				adj[v][u] = c;
				//cost 
				//caps[u][v] =c;
				residual[u][v] = residual[v][u] = c;

			}
			maxflow(source, sink);
			// run again
			// after max flow you havea graph that has been modified
			// do bfs from sourecand if adj[i][j]>0 meaniing a positive edge you
			// addtoset s

			//run bfs again - from textbook
			bfs2();
			System.out.println();



		}

	}

	static void bfs2(){
		for (int i = 0; i < adj.length; i++) {
			if (pred[i] != -1) {
				for (int j = 0; j < adj.length; j++) {
					// a positive edge is found 
					if (adj[i][j] > 0) {
						if (pred[j] == -1) // cases are the same
							System.out.printf("%d %d\n", j+1,i+1);
					}
				}
			}
		}
	}

	// known algorithm:http://www.sanfoundry.com/cpp-program-implement-edmonds-karp-algorithm/

	public static int maxflow(int source, int sink) {
		int maxflow = 0;
		while (bfs(residual, source, sink)) {
			int i = sink;
			int flow = Integer.MAX_VALUE;
			while (pred[i] != i) {
				flow = Math.min(flow, residual[pred[i]][i]);
				i = pred[i];
			}
			i = sink;
			maxflow += flow;

			while (pred[i] != i) {
				residual[pred[i]][i] -= flow;
				residual[i][pred[i]] += flow;
				i = pred[i];

			}
		}
		return maxflow;
	}

	public static boolean bfs(int[][] adj, int source, int sink) {
		Queue<Integer> q = new LinkedList<Integer>();
		// int[]pred = new int[adj.length];
		q.add(source);
		pred = new int[adj.length];
		Arrays.fill(pred, -1);
		pred[source] = source; // parent is itsefl
		while (!q.isEmpty()) { 
			int u = q.poll();
			if (u == sink)
				return true;
			for (int i = 0; i < adj[u].length; i++) {
				if (pred[i] == -1 && adj[u][i] > 0) { // edge greater than 0, keep running bfs
					pred[i] = u;
					q.add(i); // visit
				}
			}
		}
		return false;
	}
}
