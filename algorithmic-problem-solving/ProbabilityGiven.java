import java.io.*;
import java.util.*;

public class ProbabilityGiven {
	/**
	 * Used: http://www.questtosolve.com/browse.php?pid=11181 The probability of
	 * a given bit string is the product from 1 to n of either pi or (1-pi)
	 * depending on whether the ith bit is a 1 or 0 respectively.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		int caseNum = 1;
		while (true) {
			int N = in.nextInt(); // n friends
			int r = in.nextInt(); // r has bought something
			if (N == 0 && r == 0)
				break;
			double[] person = new double[N]; // prob of each person
			double[] xandr = new double[N]; // prob xi and r buy something
			for (int i = 0; i < N; i++) {
				person[i] = in.nextDouble();
				// System.out.println(person[i]);
				xandr[i] = 0;
			}
			double sum = 0.0;
			int totalPoss = (int) Math.pow(2, N);// all possible perms of
			//System.out.println(totalPoss); // total combinations
			for (int mask = 0; mask < totalPoss; mask++) {
				// all bit strings have r 1s
				if (Integer.bitCount(mask) != r)
					continue;
				double product = 1;
				for (int prod = 0; prod < N; prod++) {
					if (((1 << prod) & mask) == 0)
						product *= (1 - person[prod]);
					else
						product *= person[prod];
				}
				for (int prod = 0; prod < N; prod++) {
					if (((1 << prod) & mask) != 0) {
						xandr[prod] += product;
					}
				}
				sum += product;
			}
			System.out.printf("Case %d:\n", caseNum++);

			for (int i = 0; i < N; i++) {
				//System.out.println(sum);
				System.out.printf("%.6f\n",xandr[i] / sum);
			}

		}
	}

}
