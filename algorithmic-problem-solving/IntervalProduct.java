import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class IntervalProduct {

	public static class Fenwick {
		public int[] table;

		public Fenwick(int maxN) {
			this.table = new int[maxN + 1];
		}

		public int sumQuery(int a, int b) {
			return sumQuery(b) - sumQuery(a - 1);
		}

		public int sumQuery(int k) {
			int ret = 0;
			while (k > 0) {
				ret += table[k];
				k &= k - 1;
			}
			return ret;
		}

		public void adjust(int i, int adj) {
			while (i < table.length) {
				table[i] += adj;
				i += (i & (-i));
			}
		}

		public int getValue(int i) {
			return sumQuery(i, i);
		}

		public String toString() {
			String returnString = "";
			for (int i = 0; i < table.length; i++) {
				returnString += table[i] + " ";
			}
			return returnString;

		}
	}

	// sequence of N integers X1..Xn
	// K rounds - at the start
	// @ each round either
	// change command - change value in sequence
	// product i,k xi xi+1 xj-1 xj positive negative or 0

	// integers N and K number elements in sequence
	// number of elements in the game
	public static void main(String[] args) throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line;
		StringTokenizer numbers;
		StringBuilder result;

		while ((line = in.readLine()) != null) {
			numbers = new StringTokenizer(line);
			result = new StringBuilder();
			int N = Integer.parseInt(numbers.nextToken(), 10);
			int K = Integer.parseInt(numbers.nextToken(), 10);
			numbers = new StringTokenizer(in.readLine());

			// init fenwick trees
			Fenwick numbZeros = new Fenwick(N + 1);
			Fenwick numbNegatives = new Fenwick(N + 1);

			for (int i = 1; i <= N; i++) {
				int incoming = Integer.parseInt(numbers.nextToken(), 10);
				if (incoming == 0) {
					numbZeros.adjust(i, 1);
				} else if (incoming < 0) {
					numbNegatives.adjust(i, 1);
				}

			}

			for (int i = 0; i < K; i++) {
				numbers = new StringTokenizer(in.readLine());
				String command1 = numbers.nextToken();
				int first = Integer.parseInt(numbers.nextToken(), 10);
				int second = Integer.parseInt(numbers.nextToken(), 10);
				// if c then adjust !
				if (command1.equals("C")) {
					if (numbZeros.sumQuery(first, first) == 0 && numbNegatives.sumQuery(first, first) == 0) {
						if (second < 0) {
							numbNegatives.adjust(first, 1);

						} else if (second == 0) {
							numbZeros.adjust(first, 1);
						}
					} else if (numbZeros.sumQuery(first, first) != 0 && numbNegatives.sumQuery(first, first) == 0) {
						if (second < 0) {
							numbNegatives.adjust(first, 1);
							numbZeros.adjust(first, -1);

						} else if (second > 0) {
							numbZeros.adjust(first, -1);
						}
					} else if (numbZeros.sumQuery(first, first) == 0 && numbNegatives.sumQuery(first, first) != 0) {
						if (second > 0) {
							numbNegatives.adjust(first, -1);

						} else if (second == 0) {
							numbZeros.adjust(first, 1);
							numbNegatives.adjust(first, -1);
						}
					}

				}

				else if (command1.equals("P")) {
					int zeroCount = numbZeros.sumQuery(first, second);
					if (zeroCount > 0) {
						result.append("0");
					} else {
						int negCount = numbNegatives.sumQuery(first, second);
						if (negCount % 2 == 0) {
							result.append("+");

						} else {
							result.append("-");

						}
					}

				}

			}
			System.out.println(result);
		}

	}

}
