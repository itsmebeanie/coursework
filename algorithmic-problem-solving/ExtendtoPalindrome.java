import java.io.*;
import java.util.*;

public class ExtendtoPalindrome {
	static int max = 0;
	public static int[] buildKMPTable(String s)
	{
		int[] table = new int[s.length()+1];
		for (int i = 2; i < table.length; ++i)
		{
			int j = table[i-1];
			while (true) 
			{
				if (s.charAt(j) == s.charAt(i-1)) { table[i] = j+1; break;}
				else if (j == 0) break;
				else j = table[j];
			} 
		}
		return table;
	}
	/** Returns the final state when simulating the DFA built using pattern on the string text */
	public static int simulate(int[] table, String r, String s)
	{
		max = 0;
		int state = 0;
		for (int i = 0; i < r.length(); ++i)
		{
			while (true)
			{
				if (r.charAt(i) == s.charAt(state)) { state++; break; }
				else if (state == 0) break;
				state = table[state];
			} 
			max = Math.max(max, state);
			if (state == table.length -1) break;
		}
		return state;
	}
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line;
		//int T = Integer.parseInt(in.readLine());
		while ((line = in.readLine()) != null){
			String reversed = new StringBuilder(line).reverse().toString();
			int [] table = buildKMPTable(reversed);
			int state = simulate(table,line,reversed);
			System.out.println(line + reversed.substring(0,state));
		}
	}
}
