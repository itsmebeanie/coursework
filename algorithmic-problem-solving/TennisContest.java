import java.io.*;
import java.util.*;
/*
 * For each test case the output should contain a single line with the number representing the probability
in advance of A to win the title of best player in the world.
 */
public class TennisContest {
	static double dp[][];
	public static void main(String[]args){
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		while (T-->0){
			int N = in.nextInt(); // represents wins
			double pA = in.nextDouble(); // prob A to win the match
			double pB = (1-pA); // prob B wins the match
			dp = new double[N+2][N+2]; // p A wins v. B wins
			dp[0][0]=1; // begin the same
			for (int i = 0;i<=N;i++){ 
				for (int j=0; j<=N; j++){
					if (i != N && j != N){ // game is over
						dp[i+1][j] += dp[i][j] * pA; // a advances
						dp[i][j+1] += dp[i][j] * pB; // b adances
					}
				}
			}
			double ans = 0.0;
			for (int i = 0; i<= N; i++){ //  different scenarios where A gets to N
				ans+=dp[N][i];
			}
			
			System.out.printf("%.2f\n",ans);
		}
		
	}
}
