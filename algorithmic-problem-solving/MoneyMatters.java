import java.io.*;
import java.util.*;

public class MoneyMatters {

	// Implementation of Union Find

	public static int[] parent; // parent[i] -> parent of i

	/**
	 * Returns identifier for the node
	 */
	static int find(int node) {
		// out of bounds
		if (node < 0 || node >= parent.length) {
			System.exit(0);
		}
		while (node != parent[node]) {
			// keep going until a parent is found
			parent[node] = parent[parent[node]];
			node = parent[node];
		}
		return node;

	}

	/**
	 * Find parents and merge
	 * 
	 * @param node1
	 * @param node2
	 */
	public static void union(int a, int b) {
		int pa = find(a);
		int pb = find(b);
		if (pa == pb)
			return;
		parent[pb] = pa;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer numbers;

		// total number of test cases
		int N = Integer.parseInt(in.readLine(), 10);
		for (int i = 0; i < N; i++) {
			numbers = new StringTokenizer(in.readLine());
			int n = Integer.parseInt(numbers.nextToken());
			int m = Integer.parseInt(numbers.nextToken());

			// array with what everyone owes and needs
			int[] money = new int[n];
			for (int j = 0; j < n; j++) {
				money[j] = Integer.parseInt(in.readLine(), 10);
			}

			// parent of n
			parent = new int[n];

			for (int j = 0; j < n; j++) {
				// each node is a singleton
				parent[j] = j;
			}

			// Unioning sets
			for (int j = 0; j < m; j++) {
				numbers = new StringTokenizer(in.readLine());
				int first = Integer.parseInt(numbers.nextToken(), 10);
				int second = Integer.parseInt(numbers.nextToken(), 10);
				union(first, second);
			}

			/*
			 * // now we have sets of friends for(int j = 0; j <parent.length
			 * ;j++){ System.out.println(parent[j]); } for(int j = 0; j
			 * <parent.length ;j++){ System.out.println(rank[j]); }
			 */

			int[] total = new int[n];

			for (int j = 0; j < n; j++) {
				int parentJ = find(j);
				//System.out.println(j + ", " + parentJ);
				total[parentJ] += money[j];
			}
			boolean isPossible = true;
			for (int term = 0; term<n;term++) {
				//System.out.println(term + " " + total[term]);
				if (total[term] != 0) {
					isPossible = false;
				}
			}


			if (isPossible) {
				System.out.println("POSSIBLE");
			} else {
				System.out.println("IMPOSSIBLE");
			}
		}

	}

}
