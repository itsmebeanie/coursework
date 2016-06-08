import java.io.*;
import java.util.*;

public class Beverages {

	static int[] indegree;
	static String[] drinkList;
	static int[][] adjList;
	static HashMap<Integer, String> indexDrinks;
	static HashMap<String, Integer> drinksIndex;
	static int[] hasVisited;
	static int N;
	static int M;

	public static String topologicalSortBFS() {
		String ans = "";
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
		hasVisited = new int[N];
		for (int i = 0; i < N; i++) {
			if (indegree[i] == 0) {
				hasVisited[i] = 1;
				queue.add(i);
			}
		}
		while (queue.isEmpty() != true) {
			// get head of queue
			int curr = queue.poll();
			ans += indexDrinks.get(curr) + " ";
			for (int neighbor = 0; neighbor < N; neighbor++){
				if (adjList[curr][neighbor] == 1){
					indegree[neighbor]--;
					if (hasVisited[neighbor] == 0 && indegree[neighbor] == 0){
						hasVisited[neighbor] = 1;
						queue.add(neighbor);
					}
				}
			}

		}
		return ans.trim() + "."; // if it equals the number of vertices then its a valid topoligical sort

	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		Scanner in = new Scanner(new InputStreamReader(System.in));
		int caseNum = 1;
		while (in.hasNext()) {
			N = in.nextInt();
			indexDrinks = new HashMap<Integer,String>();
			drinksIndex = new HashMap<String, Integer>();
			indegree = new int [N];
			for (int i = 0; i < N; i++) {
				String currbeverage = in.next();
				indexDrinks.put(i, currbeverage);
				drinksIndex.put(currbeverage, i);
			}

			adjList = new int[N][N];
			M = in.nextInt();
			for (int i = 0; i < M; i++) {
				int u = drinksIndex.get(in.next());
				int v = drinksIndex.get(in.next());

				if (adjList[u][v] == 0) {
					adjList[u][v] = 1;
					indegree[v]+=1;
				}
			}
			String ans = topologicalSortBFS();
			System.out.printf("Case #%d: Dilbert should drink beverages in this order: %s\n\n", caseNum++, ans);
		}
	}
}
