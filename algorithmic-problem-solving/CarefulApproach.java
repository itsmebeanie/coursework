import java.io.*;
import java.util.*;

public class CarefulApproach {
	static int[] planes;

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		Scanner in = new Scanner(System.in);
		int caseNum = 1;
		while (true) {
			int n = in.nextInt();
			if (n == 0)
				break;
			double[] arrivals = new double[n];
			double[] departures = new double[n];
			planes = new int[n];
			for (int i = 0; i < n; i++) {
				// in seconds
				arrivals[i] = in.nextDouble() * 60.0;
				departures[i] = in.nextDouble() * 60.0;
				planes[i] = i;

			}

			double maxGap = -1.0;
			double gap;
			while (true) {
				gap = -1;
				double low = 0.0;
				double high = 86400.0;
				// run on all permutations of planes
				// System.out.println("ayy");

				while (Math.abs(low - high) >= .001) {

					gap = (low + high) / 2.0;
					double poss = poss(arrivals, departures, gap, n);
					if (poss <= .01) {
						low = gap;
					} else {
						high = gap;
					}
				}
				
				maxGap = Math.max(maxGap, gap);
				if (nextPermutation(planes) == false) {
					break;
				}
			}

			maxGap = (int) (maxGap + .5);
			System.out
					.print("Case " + caseNum + ": " + (int) maxGap / 60 + ":");
			int seconds = (int) maxGap % 60;
			if (seconds <= 9) {
				System.out.print("0");
			}
			System.out.print((int) maxGap % 60);
			System.out.println();
			caseNum++;
		}
	}

	static double poss(double[] arrivals, double[] departures,
			double attempted, int n) {
		// first plane lands
		double curr = arrivals[planes[0]];
		for (int i = 1; i < n; i++) {
			// next plane with attempted time
			double next = curr + attempted;
			// landing is possible
			if (next <= departures[planes[i]])
				curr = Math.max(arrivals[planes[i]], next);
			else
				return 1;
		}
		// max (Y)
		return curr - departures[planes[n - 1]];
	}

	// adapted from textbook
	static boolean nextPermutation(int[] array) {
	    // Find longest non-increasing suffix
	    int i = array.length - 1;
	    while (i > 0 && array[i - 1] >= array[i])
	        i--;
	    // Now i is the head index of the suffix
	    
	    // Are we at the last permutation already?
	    if (i <= 0)
	        return false;
	    
	    // Let array[i - 1] be the pivot
	    // Find rightmost element that exceeds the pivot
	    int j = array.length - 1;
	    while (array[j] <= array[i - 1])
	        j--;
	    // Now the value array[j] will become the new pivot
	    // Assertion: j >= i
	    
	    // Swap the pivot with j
	    int temp = array[i - 1];
	    array[i - 1] = array[j];
	    array[j] = temp;
	    
	    // Reverse the suffix
	    j = array.length - 1;
	    while (i < j) {
	        temp = array[i];
	        array[i] = array[j];
	        array[j] = temp;
	        i++;
	        j--;
	    }
	    
	    // Successfully computed the next permutation
	    return true;
	}
}