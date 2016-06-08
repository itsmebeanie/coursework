import java.io.*;
import java.util.*;

public class Cantor {
	public static void main(String[]args){
		Scanner in = new Scanner(System.in);
		while(true){
			String curr = in.nextLine();
			if (curr.equals("END")) break;
			boolean isCantor = true;
			double num = Double.parseDouble(curr);
			if (num == 0 || num == 1) {
				System.out.println("MEMBER");
				continue;
			}
			for (int i = 0; i<=6;i++){
				num*=3; // convert o base 3
				int digit = 0;
				if (num >= 1){
					digit = (int)num;
					if (digit == 1) {
						System.out.println("NON-MEMBER");
						isCantor = false;
						break;
					}
				}
				num-= digit;
				
			}
			if (isCantor)
				System.out.println("MEMBER");
		}
		
	}
}
