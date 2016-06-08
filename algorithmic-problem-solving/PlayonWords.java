import java.io.*;
import java.util.*;
/*
 * Eulerian path
 * Used resources to find out:
 * http://www.ctl.ua.edu/math103/euler/ifagraph.htm
 * https://www.quora.com/How-do-I-solve-the-SPOJ-Play-on-Words-problem
 */


public class PlayonWords {
	public static void dfs(int from) {
		for (int to = 0; to < 26; to++) {
			if (adjacency[from][to] > 0) {
				// remove edges and then go to neighbors
				adjacency[from][to]--;
				dfs(to);
			}
		}
	}
	static int[][] adjacency;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		while(T-->0) {
			int numwords = in.nextInt();
			adjacency = new int[26][26];
			int[] indegree = new int[26];
			int[] outdegree = new int[26];
			int startNode =-1;
			boolean eulerian = true;
			//HashSet<Integer>fb = new HashSet<Integer>();
			ArrayList<Integer> fb = new ArrayList<Integer>();
			while(numwords-->0) {
				String curr = in.next();
				int from = curr.charAt(0) - 'a';
				int to = curr.charAt(curr.length() - 1) - 'a';
				// keeping track of neighbor size
				if (fb.contains(from) == false ) fb.add(from);
				if (fb.contains(to) == false) fb.add(to);
				indegree[from]++;
				outdegree[to]++;
				adjacency[from][to]++; //edges
			}
		
			
			/** Finding  start node: */
			/**START Node- The node that has  
			 * |inDeg[a]−outDeg[a]|=1|inDeg[a]−outDeg[a]|=1 ,
			 *  or a random node (with  outDeg[a]>0outDeg[a]>0 )
			 *   if no such node exists
			 */

			for (int i = 0; i < fb.size(); i++) {
				int a = fb.get(i);
				if ((indegree[a] - outdegree[a]) == 1) {
					startNode =a;
				}
			}
			
			
			if (startNode == -1) {
				for (int i = 0; i < fb.size(); i++) {
					if (outdegree[fb.get(i)] > 0) {
						startNode = fb.get(i);
					}
				}
			}
			//if (startNode == -1){
			//	eulerian = false;
			//}
			// System.out.println(startNode);
			/**
			 *  Now after the dfs is over. 
			 *  Just check that no single edge exists in the graph. 
			 *  If no edge exists, it means that we have walked over all the edges in the graph 
			 *  without repeating any one , which means that the answer is yes. Else the answer is no.
			 */
			dfs(startNode);
			for (int i = 0; i < fb.size(); i++) {
				for (int j = 0; j < 26; j++) {
					// from to edges then no cycle
					if (adjacency[fb.get(i)][j] > 0) {
						eulerian = false;
					}
				}
			}
			System.out.println(eulerian ? "Ordering is possible." : "The door cannot be opened.");

		}
	}



}
