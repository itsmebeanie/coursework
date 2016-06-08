import java.io.*;
import java.util.*;

public class ExclusivelyEdible {
	public static void main(String[] args) {
		Scanner in = new Scanner (System.in);
		int T = in.nextInt();
		while (T-->0){
			// hansel always goes first
			
			// row + column
			int n = in.nextInt();
			int m = in.nextInt();
			// bad cake
			int r = in.nextInt();
			int c = in.nextInt();
			
			int steps = r^c;
			steps ^= n-r-1;
			steps ^= m-c-1;
			System.out.println (steps > 0 ? "Gretel": "Hansel");
		}
	}
}
