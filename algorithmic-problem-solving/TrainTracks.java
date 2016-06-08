import java.io.*;
import java.util.*;

public class TrainTracks {
	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);
		int numCases = Integer.parseInt(stdin.nextLine(), 10);

		for (int i = 0; i < numCases; i++) {
			String s = stdin.nextLine();
			// strip white spaces
			s = s.replaceAll("\\s+", "");
			String[] array = s.split("");
			int counter = 0;
			for (int j = 0; j < array.length; j++) {
				if (array[j].equals("M")) {
					counter++;
				} else if (array[j].equals("F")) {
					counter--;
				}
			}
			if (counter == 0 && array.length != 2) {
				System.out.println("LOOP");
			} else {
				System.out.println("NO LOOP");
			}
		}
	}

}
