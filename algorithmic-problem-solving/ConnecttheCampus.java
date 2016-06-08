import java.util.*;
import java.io.*;

/*
 * Adapting code from Tourist Guide, which employs Kruskal's algorithm for 
 *  finding edges of least possible weight that connects any two trees in a forest
 */


/**
MST - Minimum spanning tree 
Connected undirected graph with the minimum weight.

Sort all edges in non decreasing order of their ways
Smallest edge --- if it forms a cycle then don't include it but if it doesnt include it
Can be accomplish by unioning all vertices without the same parents

1. F - Each vertex in a graph is a seperate tree
2. S - All edges of the graph
3. While S is not empty and F is not yet spanning -remove edge and add to F if it connects two different trees

End of algorithm - MST of graph.
 */


public class ConnecttheCampus {
	// Initialize it with maxes
	public static buildingCoordinates[] coordinates = new buildingCoordinates[751];
	public static Cable[] cables = new Cable[751 * 751];
	public static int[] parent = new int[751]; // parent[i] -> parent of i

	public static class buildingCoordinates {
		public int x;
		public int y;

		buildingCoordinates(int x, int y) {
			this.x = x;
			this.y = y;
		}

		double distance(buildingCoordinates other) {
			double distance = Math.sqrt((double) ((this.x - other.x) * (this.x - other.x))
					+ (double) ((this.y - other.y) * (this.y - other.y)));
			return distance;
		}

	}

	public static class Cable{
		public int u;
		public int v;
		public double weight;

		public Cable(int u, int v, double weight) {
			this.u = u;
			this.v = v;
			this.weight = weight;
		}

		
	}

	public static class CablesComparator implements Comparator<Cable> {

		public int compare(Cable c1, Cable c2) {
			if (c1.weight == c2.weight)
				return 0;
			else if (c1.weight < c2.weight)
				return -1;
			else
				return 1;
		}

	}

	// union find
	public static int find(int node) {
		int root = node;
		while (parent[root] != root) {
			root = parent[root];
		}
		while (parent[node] != root) {
			int temp = node;
			node = parent[node];
			parent[temp] = root;
		}
		return root;
	}

	// union
	public static void union(int a, int b) {
		int pa = find(a);
		int pb = find(b);
		if (pa == pb)
			return;
		parent[pb] = pa;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(new InputStreamReader(System.in));

		while (in.hasNext()) {
			int N = in.nextInt(); // -> number of buildings
			// Initialize parents and ranks

			for (int i = 1; i <= N; i++) {
				int x = in.nextInt();
				int y = in.nextInt();
				coordinates[i] = new buildingCoordinates(x, y);
			}

			// parent, each is independent
			for (int i = 1; i <= N; i++) {
				parent[i] = i;
			}

			int u, v;
			int M = in.nextInt();
			// testing - System.out.println(M);
			for (int i = 1; i <= M; i++) {
				// union these two, belong to same set
				u = in.nextInt();
				v = in.nextInt();
				union(u, v);
			}
			int numEdges = 0;
			for (int i = 1; i <= N; i++) {
				for (int j = i + 1; j <= N; j++) {
					// each coordinate has a distance from i to j
					double weight = coordinates[i].distance(coordinates[j]);
					// an edge exists between i and j
					cables[numEdges] = new Cable(i, j, weight);
					numEdges++;
				}
			}

			/* Implement Kruskal's algorithm */
			double c = 0;
			// sort
			Arrays.sort(cables, 0, numEdges, new CablesComparator());
			int j = 0;
			for (int i = 0; i < numEdges; i++) {
				int pu = find(cables[i].u);
				int pv = find(cables[i].v);
				if (pu != pv){
					c+= cables[i].weight;
					union(cables[i].u, cables[i].v);
				}
			}

			System.out.printf("%.2f\n",c);
		}

	}

}
