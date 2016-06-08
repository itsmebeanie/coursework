import java.io.*;
import java.util.*;


/**
 * There is a ferry across the river that can take
 * n cars across the river in t minutes and return in
 * t minutes. m cars arrive at the ferry terminal by
 * a given schedule. What is the earliest time that
 * all the cars can be transported across the river?
 * What is the minimum number of trips that the
 * operator must make to deliver all cars by that
 * time?
 */

//Involving Sorting
public class FerryLoadingII {
	public static void main(String[] args) throws NumberFormatException, IOException {
		

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int c = Integer.parseInt(in.readLine());
		while (c-->0){
			StringTokenizer info = new StringTokenizer(in.readLine());
			// n cars can be taken in t minutes, in total there are m cars
			int n = Integer.parseInt(info.nextToken(),10);
			int t = Integer.parseInt(info.nextToken(),10);
			int m = Integer.parseInt(info.nextToken(),10);
			
			int carTimes[] = new int [m];
			for (int i = 0; i <m;i++){
				carTimes[i] = Integer.parseInt(in.readLine(),10);
			}
			
			int numTrips = 0;
			int curr = 0;
			int remainder = m%n;
			
			if (remainder == 0){
				curr = carTimes[n-1];
				for (int i = 1; i < m/n; i++){
					curr = Math.max(carTimes[n*(i+1)-1],curr+2*t);
					numTrips++;
				}
			}
			else{
				curr = carTimes[remainder-1];
				for (int i = 0; i < m/n; i++){
					curr = Math.max(carTimes[n*(i+1)+remainder-1],curr+2*t);
					numTrips++;
				}
			}
			numTrips++;
			System.out.println(curr+t + " " + numTrips);
		}

	}
}