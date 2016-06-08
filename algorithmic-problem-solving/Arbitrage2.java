import java.io.*;
import java.util.*;

public class Arbitrage2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		int n = 0, m;
		int caseNum = 1;
		while (true) {
			n = in.nextInt();
			if (n == 0)
				break;
			HashMap<String, Integer> currencies = new HashMap<String, Integer>();
			double[][] dist = new double[n + 1][n + 1];
			for (int i = 0; i < n; i++) {
				currencies.put(in.next(), i);
				dist[i][i] = 1.0;
			}

			m = in.nextInt();
			for (int k = 0; k < m; k++) {
				String currency1 = in.next();
				double exchangeRate = in.nextDouble();
				String currency2 = in.next();
				int i = currencies.get(currency1);
				int j = currencies.get(currency2);
				dist[i][j] = exchangeRate;
			}

			// fw
			for (int k = 0; k < n; k++) {
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {
						if (dist[i][j] < dist[i][k] * dist[k][j])
							dist[i][j] = dist[i][k] * dist[k][j];
					}
				}
			}
			
			boolean isArbitrage = false;
			for (int i = 0;i <n; i++){
				if (dist[i][i] > 1){
					isArbitrage = true;
				}
			}
			
			//System.out.println(isArbitrage);
			System.out.printf("Case %d: ",caseNum++);
			if (isArbitrage){
				System.out.println("Yes");
			}
			else{
				System.out.println("No");

			}
		}

	}

}
