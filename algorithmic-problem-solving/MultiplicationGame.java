import java.io.*;
import java.util.*;

public class MultiplicationGame {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			double n = in.nextDouble();
			int turn = 1;
			while(n>1) {
				if (turn % 2 == 1)
					n /= 9;
				else
					n /= 2;
				turn++;
			}
			System.out.println(turn%2==0? "Stan wins.":"Ollie wins.");
		}
	}
}
