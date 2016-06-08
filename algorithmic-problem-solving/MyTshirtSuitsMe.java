import java.io.*;
import java.util.*;
// using max flow
// • Maximum Cardinality Bipartite Matching (graph modeling + Max Flow)
// Used http://acm.hust.edu.cn/vjudge/contest/viewSource.action?id=2095562 -> given code by lecture notes

public class MyTshirtSuitsMe {
	// types of shirts
	static ArrayList<String>TYPES = new ArrayList<String>(Arrays.asList("XXL","XL","L","M","S", "XS"));
	public static void main(String[]args) throws NumberFormatException, IOException{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			int T = Integer.parseInt(in.readLine());
			for (int t = 0; t<T; ++t){
				StringTokenizer st = new StringTokenizer(in.readLine());
				int N = Integer.parseInt(st.nextToken()), M = Integer.parseInt(st.nextToken());
				int amount = N/6, V = 1 + 1 + M + 6;
				// adjacency list
				ArrayList<Integer>[] adj = new ArrayList[V];
				for (int i = 0; i <adj.length; ++i)adj[i] = new ArrayList<Integer>();
				int[][]caps = new int[V][V];
				int source = 0, sink = 1, p = 2, sh = 2+M;
				for (int i = 0; i< M; ++i){
					adj[source].add(p+i); adj[p+i].add(source);
					caps[source][p+i] = 1;
					st = new StringTokenizer(in.readLine());
					for (int j = 0; j < 2; ++j){
						int type = TYPES.indexOf(st.nextToken());
						adj[p+i].add(sh+type); adj[sh+type].add(p+i);
						caps[p+i][sh+type] = 1;
					}
				}
				for (int i = 0; i<6; ++i){
					adj[sh+i].add(sink); adj[sink].add(sh+i);
					caps[sh+i][sink] = amount;
				}
				
				System.out.println(maxflow(adj,caps,source,sink) == M ? "YES":"NO");
				
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
