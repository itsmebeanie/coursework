import java.io.*;
import java.util.*;

public class DotheUntwist {
	public static void main(String[]args){
		//System.out.println('_' - 'a' + 1);
		Scanner in = new Scanner(System.in);
		while (true){
			int k = in.nextInt();
			if (k == 0) break;
			String s = in.next();
			int n = s.length();
			char[]plain = new char[n];
			for (int i = 0; i<n;i++){
				int ciphercode;
				int plaincode;
				if (s.charAt(i) == '.'){
					ciphercode = 27;
				}
				else if (s.charAt(i) == '_'){
					ciphercode = 0;
				}
				else{
					ciphercode = s.charAt(i)- 'a'+1;
				}
				char plaintext;
				plaincode = (ciphercode+i)%28;
				if (plaincode == 0){
					plaintext = '_';
				}
				else if(plaincode == 27){
					plaintext = '.';
				}
				else{
					plaintext = (char) (plaincode - 1 + 'a');
				}
				plain[(k*i)%n] = plaintext;
			}
			for (int i = 0; i<n;i++){
				System.out.print(plain[i]);
			}
			System.out.println();
			
		}
	}
}
