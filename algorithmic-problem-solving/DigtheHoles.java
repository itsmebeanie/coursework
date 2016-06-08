import java.io.*;
import java.util.*;


public class DigtheHoles {
	
	static ArrayList<String>permutations = new ArrayList<String>();

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(in.readLine());
		
		/**
		 * . Each guess was given a reply in terms of two integers n1 and n2. 
		 * n1 tells the no. of colors which had same position in the hole 
		 * and Mr.Fruit’s guess. n2 tells the no. of colors which are both in the 
		 * one of the holes and in Mr.Fruit’s guess but in different position
		 */
		
		// Generate all permutations
		
		
		while (T-->0){
			// Ignore spaces
			in.readLine();
			
			// all the possible 4 letter permutations
			permutation("BVGYOR");
			//System.out.println(permutations.size());
			for (int i = 0; i < permutations.size(); i++){
				
			}
			
			/* First guess */ 
			StringTokenizer g1 = new StringTokenizer(in.readLine());
			String guess = g1.nextToken();
			int n1 = Integer.parseInt(g1.nextToken());
			int n2 = Integer.parseInt(g1.nextToken());
			ArrayList<String>valid1 = new ArrayList<String>();
			for (int i = 0; i<permutations.size();i++){
				solve(valid1, permutations.get(i), guess, n1, n2);
			}
			
			/*Second guess*/
			StringTokenizer g2 = new StringTokenizer(in.readLine());
			String guess2 = g2.nextToken();
			int n21 = Integer.parseInt(g2.nextToken());
			int n22 = Integer.parseInt(g2.nextToken());
			ArrayList<String>valid2 = new ArrayList<String>();

			for (int i = 0; i<valid1.size();i++){
				solve(valid2, valid1.get(i), guess2, n21, n22);
			}
			//System.out.println(valid2.size());
			if (valid2.size()>0){
				System.out.println("Possible");
			}
			else{
				System.out.println("Cheat");
			}
			
			
			
		}
		
		
	}
	
	public static void solve(ArrayList<String>solver,String permutation, String guess, int n1, int n2){
		
		int[]locationMatch = new int[guess.length()];
		int[]colorMatch = new int[guess.length()];
		int correctPlace = 0;
		int correctColor = 0;
		//System.out.println(guess + "," + permutation);
		//System.out.println(guess.length());
		
		
		for (int i = 0; i<guess.length();i++){
			if (permutation.charAt(i) == guess.charAt(i)){
				colorMatch[i] = 1;
				//not a possible location, since the color places match
				locationMatch[i]= -1;
				correctPlace++;
			}			
		}
		
		//System.out.println(correctColor);
		
		// check for possible places for the places to match
		for (int i = 0; i<guess.length();i++){
			if (colorMatch[i] == 1){
				continue;
			}			
			for (int j = 0; j < guess.length();j++){
				if (locationMatch[j] == -1){
					continue;
				}		
				if (guess.charAt(i) == permutation.charAt(j)){
					correctColor++;
					locationMatch[j] = -1;
				}
			}
		}
		
		if (n1 == correctPlace && n2 == correctColor){
			//System.out.println(permutation);
			solver.add(permutation);
		}
	}
	
	/**
	 * Permutations
	 * @param str
	 */
	public static void permutation(String str) { 
	    permutation("", str); 
	}

	/**
	 * Permutations
	 * @param prefix
	 * @param str
	 */
	private static void permutation(String prefix, String str) {
	    int n = str.length();
	    if (n == 0){} //System.out.println("");
	    else {
	        for (int i = 0; i < n; i++){
	        	if (prefix.length() == 4){
	        		permutations.add(prefix);
	        		//System.out.println(prefix);
	        	}
	            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
	        }
	    }
	}
}