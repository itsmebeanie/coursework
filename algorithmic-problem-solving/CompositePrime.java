import java.io.*;
import java.util.*;

public class CompositePrime {
	static int[] sieve;

	static int upperbound = 1048576; // whta is the upperboundddddddwdalkwdmk

	// Adjust the sieve to mark composite primes
	public static void sieve() {
		sieve = new int[upperbound + 1];
		Arrays.fill(sieve, 0); 
		sieve[0] = 0;
		sieve[1] = 0;
		for (int i = 2; i<=Math.sqrt(upperbound); i++) {
			if (sieve[i] == 0) { 
				for (int j = i * i; j <= upperbound; j += i) {
					sieve[j] = 1;
					//System.out.println(j);
				}
			}

		}
		for (int i = 4; i <= upperbound; i++) {
			if (sieve[i] == 1) {
				for (int j = i * 2; j <= upperbound; j += i)
					sieve[j] = 0;
			}
		}

	}

	public static void main(String[] args) {
		sieve();
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			int N = in.nextInt();
			int count = 0;
			while (N-- > 0) {
				if (sieve[in.nextInt()] == 1) {
					count++;
				}
			}
			System.out.println(count);
		}
	}

}
