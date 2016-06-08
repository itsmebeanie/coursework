import java.io.*;
import java.util.*;

public class AnnagramaticPrimes {
	static int N;
	static int[]sieve;
	static ArrayList<Integer>primes;
	static ArrayList<Integer>annagramatic;
	static ArrayList<Integer>permutations;

	//static int upperbound = 1000;
		static int upperbound = 10000000;
	 // generate the sieve
	public static void sieve(){
		primes = new ArrayList<Integer>();
		sieve = new int[upperbound+1];
		Arrays.fill(sieve, 1); // mark all as prime
		for (int i = 2; i  <= Math.sqrt(upperbound); i++){
			for (int j = i*i; j<= 1000; j+=i){
				sieve[j]=0;// not prime
			}
				
		}
		for (int i = 2; i <= 1000; i++){
			if (sieve[i] == 1)
				primes.add(i);
		}
	}
	
	// generate the annagramatic primes
	public static void annagramatic(){
		annagramatic = new ArrayList<Integer>();
		for (int i = 0; i< primes.size();i++){
			isAnagramatic(primes.get(i));			
		}
	}
	
	public static void isAnagramatic(int n){
		String str = String.valueOf(n);
		permutations = new ArrayList<Integer>();
		permutation("", str); 
		if(permutations.size() == factorial(str.length())){
			//System.out.println(permutations.size() + " " + n);
			annagramatic.add(n);
		}
	}
	
	 public static int factorial(int n) {
	       int result = 1;
	       for (int i = 1; i <= n; i++) {
	           result = result * i;
	       }
	       return result;
	   }

	/**
	 * Permutations
	 * @param prefix
	 * @param str
	 */
	private static void permutation(String prefix, String str) {
	    int n = str.length();
	    
	    if (n == 0){
	    	//System.out.println("this is the " + prefix);
	    	if (sieve[Integer.parseInt(prefix)] == 1){
	    		//System.out.println("and it gets added!");
	    		permutations.add(Integer.parseInt(prefix));
	    	}
	    } //System.out.println("");
	    else {
	        for (int i = 0; i < n; i++){
	            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
	        }
	    }
	}
	
	public static void main(String[]args){
		Scanner in = new Scanner(System.in);
		sieve();
		annagramatic();
		/*for (int i = 0; i < annagramatic.size();i++){
			System.out.println(annagramatic.get(i));
		}*/
		//System.out.println(annagramatic.size());
		while(true){
			int N = in.nextInt();
			if (N == 0) break;
			int high = 1;
			// http://stackoverflow.com/questions/1306727/way-to-get-number-of-digits-in-an-int
			int length =(int) Math.floor(Math.log10(N) + 1);
			//System.out.println(length);
			for (int j = 1; j <= length; j++){ 
				/*
				 *  less than the next power of 10 greater than n.
				 */
				high*= 10;
			}
			boolean isAnagramatic = false;
			// loop through all the anagramatic primes
			for (int i = 0; i<annagramatic.size();i++){ // loop through all annagramtic numbers
				if(annagramatic.get(i)>N && annagramatic.get(i) <high){
					isAnagramatic = true;
					System.out.println(annagramatic.get(i));
					break;
				}
				
			}
			if (isAnagramatic == false) System.out.println(0);
		}
		//System.out.println(primes.size());
	}
}
