import java.io.*;
import java.util.*;

public class BrokenPedometer {

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int numbIterations = Integer.parseInt(in.readLine());
		for (int i = 0; i < numbIterations; i++) {
			int P = Integer.parseInt(in.readLine(), 10); // number of leds
			int N = Integer.parseInt(in.readLine(), 10); // number of symbols

			int[] symbols = new int[N];
			for (int j = 0; j < N; j++) {
				// symbols held as integers to perform bitwise operations on
				// later
				String s = in.readLine().replaceAll("\\s+", "");
				symbols[j] = Integer.parseInt(s, 2);
			}
			String s = String.format("%" + P + "s", "1").replace(' ', '1');
			int end = Integer.parseInt(s, 2);
			//System.out.println(end);
			int mask = 0;
			// number of leds need to represent if all are the same
			int min = P;
			while (mask <= end) {
				// System.out.println("****");
				HashSet<Integer> counts = new HashSet<Integer>();

				for (int j = 0; j < N; j++) {
					counts.add(symbols[j] & mask);

					// System.out.println(counts.size());
					if (counts.size() == N) {
						// System.out.println("hey");
						// System.out.println(Integer.toBinaryString(mask));
						min = Math.min(min, Integer.bitCount(mask));
					}
				}

				mask++;
			}
			System.out.println(min);
			// System.out.println(shiftEnd);

		}

	}

}