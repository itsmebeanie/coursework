import java.util.*;
import java.io.*;

/***
 * we can attach one extra information to each vertex:
whether we come to that vertex using cycle or not; then, run Dijkstra’s
to solve SSSP on this modified graph
 * @author kaitlingu
 *
 */
class RoughRoads {
	static int N;
	static int R;
	static ArrayList<V> vertices;

	public static void djistkra() {
		PriorityQueue<V> queue = new PriorityQueue<V>(2 * N, new VComparator());
		V first = vertices.get(0);
		first.weight = 0;
		queue.add(first);
		for (int i = 1; i < 2 * N; i++) {
			queue.add(vertices.get(i));
		}
		while (queue.isEmpty() == false) {
			V currentV = queue.poll();
			for (int i = 0; i < currentV.edges.size(); i++) {
				E currE = currentV.edges.get(i);
				V end1 = currE.end1;
				V end2 = currE.end2;
				int weight = currE.weight;
				if (end2.weight == -1 || (end2.weight > end1.weight + weight)) {
					if (end1.weight != -1) {
						queue.remove(end2);
						end2.weight = (end1.weight + weight);
						end2.prev = (end1);
						queue.add(end2);
					}
				}
			}
		}

		V ans = vertices.get(N - 1);
		int weight = ans.weight;
		if (weight == -1) {
			System.out.println("?");
		} else {
			System.out.println(weight);
		}
	}

	public static void setup(Scanner s){
		N = s.nextInt();
		R = s.nextInt();
		vertices = new ArrayList<V>();
		
		for (int i = 0; i < 2 * N; i++) {
			V vertex = new V(i);
			vertices.add(vertex);
		}

		for (int i = 0; i < R; i++) {
			int r1 = s.nextInt();
			int r2 = s.nextInt();
			int r3 = s.nextInt();
			// for every path from r1 to r2 create 4 paths
			V vr1 = vertices.get(r1);
			V vrn1 = vertices.get(r1 + N);
			V vr2 = vertices.get(r2);
			V vrn2 = vertices.get(r2 + N);

			E edge1 = new E(vr1, vrn2, r3);
			E edge2 = new E(vrn2, vr1, r3);
			E edge3 = new E(vrn1, vr2, r3);
			E edge4 = new E(vr2, vrn1, r3);

			vr1.edges.add(edge1);
			vrn2.edges.add(edge2);
			vrn1.edges.add(edge3);
			vr2.edges.add(edge4);
		}
	}
	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(System.in);
		int setNum = 1;
		while (in.hasNextInt()) {
			System.out.println("Set #" + setNum++);
			setup(in);
			djistkra();
		}
	}
}

class E {
	public V end1;
	public V end2;
	public int weight;
	public E(V end1, V end2, int weight) {
		this.end1 = end1;
		this.end2 = end2;
		this.weight = weight;
	}
}

class V {
	public int n;
	public ArrayList<E> edges = new ArrayList<E>();
	public int weight = -1;
	public V prev;
	public V(int n) {
		this.n = n;
	}
}

class VComparator implements Comparator<V> {
	public int compare(V v1, V v2) {
		if (v1.weight == v2.weight) {
			return 0;
		} else if (v1.weight == -1 || v2.weight == -1 || v1.weight < v2.weight) {
			return -1;
		} else if (v1.weight > v2.weight) {
			return 1;
		}
		return 0;
	}

}
