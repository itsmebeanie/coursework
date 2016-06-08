import java.io.*;
import java.util.*;

public class PeriodicLength {
	public static void main(String[]args){
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		while (T-->0){
			String s = in.next();
			for (int i = 1; i<=s.length();i++){
				String t = s.substring(0,i);
				int sub = t.length();
				String rep = "";
				for (int j = 1; j<=s.length()/sub;j++){
					rep+=t;
					if (rep.equals(s)){
						System.out.println(sub);
						i = s.length();
						break;
					}
				}
			}
			if (T!=0){
				System.out.println();
			}
		}
	}
}
