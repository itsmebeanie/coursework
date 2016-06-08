import java.io.*;
import java.util.*;

public class Cipher {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			int n = Integer.parseInt(in.readLine());
			if (n == 0)
				break;
			int[] input = new int[n];
			StringTokenizer s = new StringTokenizer(in.readLine());
			for (int i = 0; i < n; i++) { // 0 based
				input[i] = Integer.parseInt(s.nextToken()) - 1; // 0 indices
			}
			// cycle created among numbers
			// 1 2 3 4 5 6 7 8 9 10
			// 4 5 3 7 2 8 1 6 10 9
			ArrayList<ArrayList<Integer>> cycles = createCycles(input, n);
			while (true) {
				String line = in.readLine();
				if (line.equals("0"))
					break;
				/**
				 * The length of the message is always less or equal than n. If
				 * the message is shorter than n, then spaces are added to the
				 * end of the message to get the message with the length n.
				 */

				char[] message = new char[n];// each message has to be n
												// characters long
				Arrays.fill(message, ' '); // initially blank spaces
				// s = new StringTokenizer(line);

				int start = 0;
				for (int i = 0; i < line.length(); i++) {
					if (line.charAt(i) == ' ') {
						start = i;
						break;
					}
				}
				int k = Integer.parseInt(line.substring(0, start));
				start += 1;

				for (int j = 0; start < line.length(); j++) {
					message[j] = line.charAt(start++);
				}

				/**
				 * for (int i = 0;i <message.length; i++){
				 * System.out.println(message[i]); }
				 * System.out.println("WEFRWER");
				 */
				char[] decrypted = new char[n];
				for (ArrayList<Integer> cycle : cycles) { // each cycle
					for (int j = 0; j<cycle.size();j++) { // each number in the cycle
						int a = cycle.get(j);
						//System.out.println(a);
						int i = (cycle.indexOf(a) + k) % cycle.size();
						decrypted[cycle.get(i)] = message[a];
					}
				}
				System.out.println(decrypted);

			}
			System.out.println();
		}
	}

	// Cycle creation
	public static ArrayList<ArrayList<Integer>> createCycles(int[] input, int n) {
		boolean[] visited = new boolean[n];
		Arrays.fill(visited, false); // all not visited
		ArrayList<ArrayList<Integer>> cycles = new ArrayList<>(); // hold cycles
		for (int i = 0; i < n; i++) {
			if (visited[i] == false) {
				cycles.add(new ArrayList<Integer>()); // new cycle
			}
			int upcoming = i;
			while (visited[upcoming] == false) { // cycle creation
				visited[upcoming] = true;
				int indexToAdd = cycles.size() - 1; // current cycle
				cycles.get(indexToAdd).add(upcoming);
	
				upcoming = input[upcoming];
			}

		}
		return cycles;
	}
}
