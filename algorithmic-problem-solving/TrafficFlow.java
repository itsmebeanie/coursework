import java.util.*;
import java.io.*;

public class TrafficFlow {

	public static class Road implements Comparable<Road> {
		int u;
		int v;
		int c;

		public Road(int u, int v, int c) {
			this.u = u;
			this.v = v;
			this.c = c;
		}

		public int compareTo(Road o1) {
			int compareQuantity = ((Road) o1).c;
			return compareQuantity - this.c;
		}

		public static Comparator<Road> RoadsComparator = new Comparator<Road>() {

			public int compare(Road r1, Road r2) {

				return r1.compareTo(r2);
			}

		};

	}

	// Implementation of Union Find
	public static Road[] roads;
	public static int[] parent; // parent[i] -> parent of i
	public static int[] rank; // rank[i] -> rank of subtree rooted at i

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
	static void union(int node1, int node2) {
		// lookin for the nodes and linking them!!
		int parentNode1 = find(node1);
		int parentNode2 = find(node2);

		// lower ranking thenh higher ranking takes on parenthood
		if (rank[parentNode1] > rank[parentNode2]) {
			parent[parentNode2] = parentNode1;
		} else if (rank[parentNode1] < rank[parentNode2]) {
			parent[parentNode1] = parentNode2;

		} else {
			parent[parentNode1] = parentNode2;
			rank[parentNode2]++;
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(new InputStreamReader(System.in));
		int T = in.nextInt();
		int caseNum = 1;
		while (T-- > 0) {
			int N = in.nextInt();
			parent = new int[N];
			rank = new int[N];
			int M = in.nextInt();
			roads = new Road[M];

			for (int i = 0; i < M; i++) {
				int u = in.nextInt();
				int v = in.nextInt();
				int c = in.nextInt();
				Road curr = new Road(u, v, c);
				roads[i] = curr;
			}
			for (int i = 0; i < N; i++) {
				parent[i] = i;
				rank[i] = 0;
			}
			int c = 0;
			Arrays.sort(roads, Road.RoadsComparator);
			for (int i = 0, k = 0; i < M && k < N - 1; i++) {
				while (find(roads[i].u) == find(roads[i].v)) {
					i++;
				}
				union(roads[i].u, roads[i].v);
				k++;
				if (k == N - 1) { // at capacity
					c = roads[i].c;
				}
			}
			System.out.println("Case #" + caseNum++ + ": " + c);

		}
	}
}
