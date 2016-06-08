import java.util.*;
import java.io.*;

public class HowManyZeros {
	/**
	 * 1. number of trialing 0s:
	 * 
	 * @param args
	 */
	 
	public static void main(String[]args){
		Scanner in = new Scanner(System.in);
		while (in.hasNext()){
			int N = in.nextInt();
			int base = in.nextInt();
			int[] primefactorization = new int[1000];
			
			// digits
			double digits = 1;
			double logB = Math.log10(base);
			for (int i = 2; i<=N; i++){
				digits +=  Math.log10(i)/logB;
			}
			
			/**Zeroes: https://uva.onlinejudge.org/board/viewtopic.php?f=9&t=7137&sid=eb73e09e8aa3f57d93399f17aeac0a2e
			 find the largest prime factor of the base and 
			 find how many of that prime factor goes into N!
			 */
			int zeros = 0;
			int largest = 0;
			int temp = base;
			int num = 0;
			// factorizing the base
			for (int i = 2; i<=temp;i++){
				num = 0; // reset for largest
				while ((temp%i) == 0){
					largest = i;
					temp/=i;
					num++;
				}
			}
			
			for(int i = largest; i<= N; i*=largest){
				zeros += (N/i)/num;
			}
			
			
			/**zeros= N/largest;*/ // double counts
			// trailing zeros and number of digits
			
			System.out.println(zeros + " " + (int)digits);
			
		}
	}
}
