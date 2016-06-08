import java.io.*;
import java.util.*;
// Finding articulation points
public class TouristGuide {
	static int [] dfs_num;
	static int [] dfs_low;
	static int [] hasVisited;
	static int [][]adjList;
	static int dfsNumberCounter;
	static HashMap<String, Integer> cityIndex;
	static HashMap<Integer, String> indexCity;
	static int ans = 0;
	// adapted from textbook
	// articulation point and bridge
	public static void articulationPointandBridge(int u, int v){
		hasVisited[v] = dfs_low[v] = ++dfsNumberCounter; // dfs_low[v] <= dfs_num[v] 
		for (int i = 0; i < adjList.length; i++){
			if (adjList[v][i] == 1){
				if (hasVisited[i] == 0){ 
					//System.out.println("hi");// a tree edge
					articulationPointandBridge(v,i);
					dfs_low[v] = Math.min(dfs_low[v], dfs_low[i]);
					if (dfs_low[i] >= hasVisited[v]){ 
						//System.out.println("this should happen");// for articulation point
						dfs_num[v]++;
					}
				}
				else if(i!=u){ // a back edge and not direct cycle
					dfs_low[v] = Math.min(dfs_low[v], hasVisited[i]); // update dfs low
				}
			}
		}
	}
	public static void main(String[]args){
		Scanner in = new Scanner(new InputStreamReader(System.in));
		int caseNum = 1;
		while (in.hasNext()){
			int N = in.nextInt();
			if (N == 0)
				break;
			adjList = new int[N][N];
			indexCity = new HashMap<Integer, String>();
			cityIndex = new HashMap<String, Integer>();
			hasVisited = new int[N];
			dfs_low = new int[N];
			dfs_num = new int[N];
			dfsNumberCounter = 0;
		
			ArrayList<String>cities = new ArrayList<String>();
			for (int i = 0; i<N;i++){
				cities.add(in.next());
			}
			Collections.sort(cities);
			for (int i = 0; i < N;i++){
				String curr = cities.get(i);
				indexCity.put(i, curr);
				cityIndex.put(curr, i);
			}
			if (caseNum > 1){
				System.out.println();
			}
			
		
			int R = in.nextInt();
			for (int i = 0; i < R; i++){
				String city1 = in.next();
				String city2 = in.next();
				int u = cityIndex.get(city1);
				int v = cityIndex.get(city2);
					adjList[u][v] = 1;
					adjList[v][u] = 1;
			}
			
			
			for (int i = 0; i < N; i++){
				if (hasVisited[i] == 0){
					articulationPointandBridge(i,i);
					dfs_num[i]--;
				}
			}
			ArrayList<String> cityAns = new ArrayList<String>();
			ans = 0;
			for (int i = 0; i < N; i++){
				if (dfs_num[i] > 0){
					ans++;
					cityAns.add(indexCity.get(i));
				}
			}
			System.out.printf("City map #%d: %d camera(s) found", caseNum++, ans);
			System.out.println();
			for(int i = 0; i<cityAns.size();i++){
				System.out.println(cityAns.get(i));
			}
		}
	}

}
