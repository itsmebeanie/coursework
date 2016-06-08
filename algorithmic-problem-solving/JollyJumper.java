import java.io.*;
import java.util.*;

public class JollyJumper {
	public static void main(String[] args) {
		Scanner stdin = new Scanner(System.in);

		while (stdin.hasNext()) {
			int num = stdin.nextInt();
			int sum = 0;
			int[] array = new int[num];

			for (int i = 0; i < num; i++) {
				array[i] = stdin.nextInt();
			}

			boolean isJolly = false;
			HashSet<Integer> nNumbers = new HashSet<Integer>();

			ArrayList<Integer> numbers = new ArrayList<Integer>();

			for (int i = 0; i < array.length - 1; i++) {
				// successive elements
				int diff = Math.abs(array[i] - array[i + 1]);
				if (diff == 0 || diff >= array.length || numbers.contains(diff)) {
					System.out.println("Not jolly");
					break;
				} else {
					numbers.add(diff);
				}

			}

			if (numbers.size() == array.length - 1) {
				System.out.println("Jolly");

			}

		}
	}
}