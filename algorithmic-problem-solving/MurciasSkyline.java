import java.io.*;
import java.util.*;

public class MurciasSkyline {

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(in.readLine(), 10);
		int C = 1;
		while (T-- > 0) {
			int N = Integer.parseInt(in.readLine(), 10);
			int[] heights = new int[N];
			int[] widths = new int[N];

			StringTokenizer h = new StringTokenizer(in.readLine());
			for (int i = 0; i < N; i++) {
				heights[i] = Integer.parseInt(h.nextToken(), 10);
			}
			StringTokenizer w = new StringTokenizer(in.readLine());
			for (int i = 0; i < N; i++) {
				widths[i] = Integer.parseInt(w.nextToken(), 10);
			}
			int increaseL = LIS(heights, widths);
			int decreaseL = LDS(heights, widths);

			String s = "";
			if (increaseL >= decreaseL)
				s = "Increasing (" + increaseL + ")." + " Decreasing ("
						+ decreaseL + ").";
			else if (decreaseL > increaseL)
				s = "Decreasing (" + decreaseL + ")." + " Increasing ("
						+ increaseL + ").";
			System.out.println("Case " + C + ". " + s);
			C++;
		}
	}

	public static int LIS(int[] h, int[] w) {
		if (h == null || h.length == 0) {
			return 0;
		}

		int n = h.length;

		int increasing = w[0];
		int[] lis = new int[n];
		lis[0] = w[0];
		for (int i = 1; i < h.length; i++) {
			// each has 1
			int maxIncrease = 0;
			for (int j = 0; j < i; j++) {
				if (h[i] > h[j]) {
					maxIncrease = Math.max(maxIncrease, lis[j]);
					// System.out.println(h[j]);
				}
			}
			lis[i] = maxIncrease + w[i];
			increasing = Math.max(increasing, lis[i]);

		}
		return increasing;

	}

	public static int LDS(int[] h, int[] w) {
		if (h == null || h.length == 0) {
			return 0;
		}

		int n = h.length;

		int decreasing = w[0];
		int[] lis = new int[n];
		lis[0] = w[0];
		for (int i = 1; i < h.length; i++) {
			// each has 1
			int maxDecrease = 0;
			for (int j = 0; j < i; j++) {
				if (h[i] < h[j]) {
					maxDecrease = Math.max(maxDecrease, lis[j]);
					// System.out.println(h[j]);
				}
			}
			lis[i] = maxDecrease + w[i];
			decreasing = Math.max(decreasing, lis[i]);

		}
		return decreasing;

	}

}