import java.io.*;
import java.util.*;

public class ThroughTheDesert {
	// events that occur
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line;
		while ((line = in.readLine()) != null) {
			if (line.equals("0 Fuel consumption 0")) {
				break;
			}
			// events
			String[] events = new String[50];

			// first event
			events[0] = line;
			// other events
			int i = 1;
			while (true) {
				line = in.readLine();
				events[i++] = line;
				if (line.contains("Goal")) {
					break;
				}

			}

			double low = 0.0;
			double mid = 0.0;
			double fuel = 0.0;
			double high = 10000.0;

			while (true) {
				mid = (low + high) / 2.0;
				// if the trip is possible then decrease
				if (isPossible(0, mid, events)) {
					high = mid;
					fuel = mid;
				} else {
					low = mid;
				}
				if (Math.abs(low - high) <= 0.00000001) {
					break;
				}
			}
			System.out.printf("%.3f\n", fuel);

		}

	}

	public static boolean isPossible(int distance, double fuelTest, String[] events) {
		boolean isPossibleTrip = true;
		double temp = fuelTest;
		double num = 0.0;
		int leaks = 0;
		for (int i = 0; i < events.length; i++) {
			String curr = events[i];
			String[] in = curr.split(" ");
			int d = Integer.parseInt(in[0]);
			if (curr.contains("Goal")) {
				fuelTest -= (num + leaks) * (Integer.parseInt(in[0]) - distance);
				break;

			} else if (curr.contains("Mechanic")) {
				fuelTest -= (num + leaks) * (Integer.parseInt(in[0]) - distance);
				distance = Integer.parseInt(in[0]);
				leaks = 0;
			} else if (curr.contains("Leak")) {
				fuelTest -= (num + leaks) * (Integer.parseInt(in[0]) - distance);
				distance = Integer.parseInt(in[0]);
				leaks++;
			} else if (curr.contains("Fuel")) {
				fuelTest -= (num + leaks) * (Integer.parseInt(in[0]) - distance);
				distance = Integer.parseInt(in[0]);
				num = Integer.parseInt(in[3]);
				num /= 100.0;
			} else if (curr.contains("Gas")) {
				fuelTest -= (num + leaks) * (Integer.parseInt(in[0]) - distance);
				if (fuelTest < 0) {
					isPossibleTrip = false;
				}
				fuelTest = temp;
				distance = Integer.parseInt(in[0]);
			}
			if (fuelTest < 0) {
				isPossibleTrip = false;
			}

		}
		if ((fuelTest > 0 || Math.abs(fuelTest) <= 0.000000001) && isPossibleTrip == true) {
			return true;
		}
		return false;
	}
}