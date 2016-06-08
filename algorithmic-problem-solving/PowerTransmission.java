import java.io.*;
import java.util.*;
// using max flow
// • Maximum Cardinality Bipartite Matching (graph modeling + Max Flow)
// Used http://acm.hust.edu.cn/vjudge/contest/viewSource.action?id=2095562 -> given code by lecture notes
// https://www.youtube.com/watch?v=GiN3jRdgxU4
public class PowerTransmission {
	// types of shirts
	static ArrayList<String>TYPES = new ArrayList<String>(Arrays.asList("XXL","XL","L","M","S", "XS"));
	public static void main(String[]args) throws NumberFormatException, IOException{
			Scanner in = new Scanner(new InputStreamReader(System.in));
			while(in.hasNext()){
				//The input will start with a postive integer N (1 ≤ N ≤ 100) indicates the number of N.
				int N = in.nextInt();
				int V = 2*N + 2;
				int source = 2*N;
				int sink = 2*N+1;
				// adjacency list
				ArrayList<Integer>[] adj = new ArrayList[V];
				for (int i = 0; i <adj.length; ++i)adj[i] = new ArrayList<Integer>();
				int[][]caps = new int[V][V];
			
				//The next few lines contain N positive integers indicating the capacity of each regulator from 1 to N.
				for (int i = 0; i< N; ++i){
					int capacity= in.nextInt(); // 10 20 30 40
					adj[i].add(N+i); adj[N+i].add(i); //add to adjacency
					caps[i][N+i] = capacity;
					caps[N+i][i] = 0;

				}
				// The next line contains another positive integer M which is the number of links available among the
				// regulators.
				int M = in.nextInt(); // links
				for (int link = 0; link <M; link++){
					/**
					 * Following M lines contain 3 positive integers (i j C) each. 
					 * ‘i’ and ‘j’ is the regulator index (1 ≤ i, j ≤ N) and
					 *  C is the capacity of the link. 
					 *  Power can transfer from i’th regulator to j’th regulator
					 */
					int  i =in.nextInt()-1 + N; 
					int j = in.nextInt()-1;
					int C = in.nextInt();
					caps[i][j] = C; // power can tranfer
					caps[j][i] = 0; // but not this way
					adj[i].add(j);
					adj[j].add(i);
				}
				/**
				 * The next line contains another two positive integers B and D. 
				 * B is the number of regulators which are the entry point of the network. Power generated in Barisal must enter in the network through these
entry points. Simmilarly D is the number of regulators connected to Dhaka. These links are special
and have infinite capacity. Next line will contain B + D integers each of which is an index of regulator.
The first B integers are the index of regulators connected with Barisal. Regulators connected with
Barisal are not connected with Dhaka. Input is terminated by EOF.
				 */
				
				int B = in.nextInt(); 
				int D = in.nextInt();
				// entry points
				for (int i = 0; i<B; i++){
					int curr = in.nextInt()-1;
					caps[source][curr] = Integer.MAX_VALUE; // infinite capacity
					caps[curr][source] = 0;
					adj[source].add(curr);
					adj[curr].add(source);
					
				}
				// connected to haka
				for (int i = 0; i<D;i++){
					int curr = in.nextInt()-1 + N;
					caps[curr][sink] = Integer.MAX_VALUE; //infinite capacity
					caps[sink][curr] = 0;
					adj[sink].add(curr);
					adj[curr].add(sink);
				}
				System.out.println(maxflow(adj,caps,source,sink));
				
			}
	}
	
	/**
	 * Max Flow code
	 */
	static int maxflow(ArrayList<Integer>[]adj, int[][]caps, int source, int sink){
		int ret = 0;
		while (true){
			int f = augment(adj, caps, source, sink);
			if (f == 0) break;
			ret+= f;
		}
		return ret;
	}
	static int augment(ArrayList<Integer>[]adj, int[][]caps, int source, int sink){
		Queue<Integer>q = new ArrayDeque<Integer>();
		int[]pred = new int[adj.length];
		Arrays.fill(pred,-1);
		int[]f = new int[adj.length];
		pred[source]=source; f[source] = Integer.MAX_VALUE; q.add(source);
		while (!q.isEmpty()){
			int curr =q.poll(), currf = f[curr];
			if (curr == sink){
				update(caps,pred,curr,f[curr]);
				return f[curr];
			}
			for (int i = 0; i<adj[curr].size(); ++i){
				int j = adj[curr].get(i);
				if (pred[j]!= -1 || caps[curr][j] == 0) continue;
				pred[j] = curr; f[j] = Math.min(currf,  caps[curr][j]); q.add(j);
			}
		}
		return 0;
	}
	static void update(int[][]caps, int[] pred, int curr, int f){
		int p = pred[curr];
		if (p == curr) return;
		caps[p][curr] -= f; caps[curr][p] += f;
		update(caps,pred, p, f);
	}
}
