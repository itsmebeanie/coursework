
// one pass through to take into account 
// [ starts and append to string builder
import java.io.*;
import java.util.*;

public class BeijuText {
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line;
		int beijuCount = -1;
		while ((line = in.readLine()) != null) {
			HashMap<Integer, StringBuilder> Beiju = new HashMap<Integer, StringBuilder>();
			char[] charArray = line.toCharArray();
			boolean textIndex = false;
			StringBuilder s = new StringBuilder();
			StringBuilder curr = new StringBuilder();

			for (int i = 0; i < charArray.length; i++) {
				if (charArray[i] == '[') {
					textIndex = true;
					Beiju.put(beijuCount, curr);
					curr = new StringBuilder();
					beijuCount++;
				}
				if (charArray[i] == ']') {
					if (textIndex == true) {
						Beiju.put(beijuCount, curr);
					}
					textIndex = false;
				}
				if (textIndex == true && charArray[i] != '[') {
					curr.append(charArray[i]);
				}
			}
			if (textIndex == true) {
				Beiju.put(beijuCount, curr);

			}

			// System.out.println(s);

			for (int i = beijuCount; i >= 0; i--) {
				if (Beiju.get(i) != null) {
					s.append(Beiju.get(i));
				}
			}
			textIndex = false;
			for (int i = 0; i < charArray.length; i++) {
				if (charArray[i] == '[') {
					textIndex = true;
				}
				if (charArray[i] == ']') {
					textIndex = false;
				}
				if (textIndex == false && charArray[i] != ']') {
					s.append(charArray[i]);
				}
			}
			System.out.println(s);
		}

	}
}
