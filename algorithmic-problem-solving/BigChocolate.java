import java.io.*;
import java.util.*;
public class BigChocolate {
	public static void main(String[]args){
		Scanner in = new Scanner(System.in);
		while (in.hasNext()){
		int row = in.nextInt();
		int col = in.nextInt();
		System.out.println(row*col-1);
		}
	}
}
