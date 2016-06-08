import java.util.*;
import java.io.*;

public class MaximumSum {
	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		int N = stdin.nextInt();
		// create n x n matrix
		int[][] matrix = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				matrix[i][j] = stdin.nextInt();
			}
		}
		System.out.println(maximumSum(matrix, N));

	}

	// kadanes algorithm
	public static int maximumSum(int[][] m, int n) {
		int maximum = Integer.MIN_VALUE;
		int sum = 0;
		for (int i = 0; i < n; i++) {
			int[] dp = new int[n];
			for (int j = i; j < n; j++) {
				for (int k = 0; k < n; k++)
					dp[k] += m[k][j];
				sum = maxSub(dp);
				if (sum > maximum)
					maximum = sum;
			}

		}

		return maximum;

	}

	public static int maxSub(int[] a) {
		int max = Integer.MIN_VALUE;
		int sum = 0;

		for (int i = 0; i < a.length; i++) {
			sum += a[i];
			if (sum < 0) {
				sum = 0;
			} else if (sum > max)
				max = sum;
		}

		return max;
	}
}