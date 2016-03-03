import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Class to represent scheduler for all the algorithms
 * 
 * @author Kaitlin Gu
 *
 */
public class Scheduler {
	// Attributes
	// list to store processes
	public static ArrayList<Process> processList = new ArrayList<Process>();
	// list to store original list
	public static ArrayList<Process> inputList = new ArrayList<Process>();
	// finishing time of entire run
	public static int finishingTime = 0;
	public static double IOUtilization = 0.0;
	public static double CPUUtilization = 0.0;
	// bolean to keep track of whether or not verbose output would be printed
	public static boolean verbose = false;

	// Getters and setters for attributes
	public static int getFinishingTime() {
		return finishingTime;
	}

	public void setFinishingTime(int finishingTime) {
		this.finishingTime = finishingTime;
	}

	// Handles file with n processes and puts them in a process list
	public static void fileHandler(String file) throws IOException {
		BufferedReader input = new BufferedReader(new FileReader(file));
		String line = null;
		String[] splitString = null;

		// list of processes from scheduler
		while ((line = input.readLine()) != null) {
			line = line.replace("(", "");
			line = line.replace(")", "");
			splitString = line.split(" +");
		}

		// store list of processes
		int num = Integer.parseInt(splitString[0]);
		int n = 1;
		// Original input
		System.out.print("The original list was: " + num);
		for (int i = 0; i < num; i++) {
			Process p = new Process();
			p.setArrivalTime(Integer.parseInt(splitString[n++]));
			System.out.print(" ( " + p.getArrivalTime() + " ");
			p.setCpuBurst(Integer.parseInt(splitString[n++]));
			System.out.print(p.getCpuBurst() + " ");

			int C = Integer.parseInt(splitString[n++]);
			p.setRemainingCPUTime(C);
			p.setTotalCPUTime(C);
			System.out.print(C + " ");

			p.setIoBurst(Integer.parseInt(splitString[n++]));
			System.out.print(p.getIoBurst() + " )");
			processList.add(p);
			inputList.add(p);
		}
		System.out.println();
		// Sorted input
		arrivalSort(inputList);
		System.out.print("The (sorted) input is: " + num);
		for (int i = 0; i < num; i++) {
			Process p = inputList.get(i);
			System.out.print(" ( " + p.getArrivalTime() + " ");
			System.out.print(p.getCpuBurst() + " ");
			System.out.print(p.getTotalCPUTime() + " ");
			System.out.print(p.getIoBurst() + " ) ");
		}
		System.out.println("\n");
		if (verbose) {
			System.out.println("The detailed printout gives the state and remaining burst for each process\n");

		}

	}

	/**
	 * Method to check if all the processes have terminated
	 * 
	 * @param processList
	 * @return true if processes have all terminated, false if not
	 */
	public static boolean isDone(ArrayList<Process> processList) {
		for (int i = 0; i < processList.size(); i++) {
			Process p = processList.get(i);
			if (p.getStatus() != p.END) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Method to printall the processes' information
	 */
	public static void printProcesses() {
		for (int i = 0; i < processList.size(); i++) {
			Process p = processList.get(i);
			System.out.println("Process " + i + ":");
			System.out.println("\t(A,B,C,M) = (" + p.getArrivalTime() + ", " + p.getCpuBurst() + ", "
					+ p.getTotalCPUTime() + ", " + p.getIoBurst() + ")");
			System.out.println("\tFinishing time: " + p.getFinishingTime());
			System.out.println("\tTurnaround time: " + p.getTurnAroundTime());
			System.out.println("\tI/O time: " + p.getTotalioTime());
			System.out.println("\tWaiting time: " + p.getWaitTime());
			System.out.println();
		}
	}

	/**
	 * Print summary of entire run
	 */
	public static void printSummary() {
		double sumWaiting = 0.0;
		double sumTurnAround = 0.0;
		for (int i = 0; i < processList.size(); i++) {
			Process p = processList.get(i);
			sumWaiting += p.getWaitTime();
			sumTurnAround += p.getTurnAroundTime();

		}
		System.out.println("Summary Data");
		System.out.println("\tFinishing Time:  " + finishingTime);
		System.out.printf("\tCPU Utilization: %.6f%n", CPUUtilization / finishingTime);
		System.out.printf("\tI/O Utilization: %.6f%n", IOUtilization / finishingTime);
		System.out.printf("\tThroughput: %.6f processes per hundred cycles%n", processList.size() / (finishingTime / 100.0));
		System.out.printf("\tAverage Turnaround Time: %.6f%n", sumTurnAround / processList.size());
		System.out.printf("\tAverage Waiting Time: %.6f%n", sumWaiting / processList.size());
	}

	/**
	 * Sorts processes by arrival time
	 * 
	 * @param processes
	 */

	public static void arrivalSort(ArrayList<Process> processes) {
		for (int i = 1; i < processes.size(); i++) {
			for (int j = i; j > 0; j--) {
				if (processes.get(j).getArrivalTime() < processes.get(j - 1).getArrivalTime()) {
					Process temp = processes.get(j);
					processes.set(j, processes.get(j - 1));
					processes.set(j - 1, temp);
				}

			}
		}
	}

	/**
	 * Returns a burst generated by random text file and B
	 * 
	 * @param X
	 * @param B
	 * @return cpu burst
	 */
	public static int randomOS(int X, int B) {
		return 1 + (X % B);

	}

	public static void setOrder(ArrayList<Process> processes) {
		for (int i = 0; i < processes.size(); i++) {
			Process p = processes.get(i);
			p.setPriority(i);
		}
	}

	public static void sortOrder(ArrayList<Process> processes) {
		for (int i = 1; i < processes.size(); i++) {
			for (int j = i; j > 0; j--) {
				if (processes.get(j).getPriority() < processes.get(j - 1).getPriority()) {
					Process temp = processes.get(j);
					processes.set(j, processes.get(j - 1));
					processes.set(j - 1, temp);
				}

			}
		}
	}

	/**
	 * Sorts processes by their CPU time
	 * 
	 * @param processes
	 */

	public static void sortTime(ArrayList<Process> processes) {
		for (int i = 1; i < processes.size(); i++) {
			for (int j = i; j > 0; j--) {
				if (processes.get(j).getRemainingCPUTime() < processes.get(j - 1).getRemainingCPUTime()) {
					Process temp = processes.get(j);
					processes.set(j, processes.get(j - 1));
					processes.set(j - 1, temp);
				}
			}
		}
	}

	/**
	 * Method that adds wait time and CPU utilization
	 * 
	 * @param pro
	 */

	public static void addWaitTime(ArrayList<Process> pro) {
		for (int i = 0; i < pro.size(); i++) {
			Process p = pro.get(i);
			if (p.getStatus() == p.RUNNING) {
				CPUUtilization++;
			}
			if (p.getStatus() == p.READY) {
				p.setWaitTime(p.getWaitTime() + 1);
			}
		}

	}

	/**
	 * Method to print out the verbose output
	 * 
	 * @param pro,
	 *            list of processes
	 * @param cycle
	 */
	public static void verbose(ArrayList<Process> pro, int cycle) {
		if (verbose == true) {
			// if flag is true
			String status = "";
			int remaining = 0;
			String s = "";
			for (int i = 0; i < pro.size(); i++) {
				Process p = pro.get(i);
				if (p.getStatus() == 0) {
					status = "unstarted";
					remaining = 0;

				} else if (p.getStatus() == 1) {
					status = "running";
					remaining = p.getCurrentcpuBurst();
				} else if (p.getStatus() == 2) {
					status = "blocked";
					remaining = p.getCurIOTime();
				} else if (p.getStatus() == 3) {
					// p.setWaitTime(p.getWaitTime()+1);
					status = "ready";
					remaining = 0;
				} else if (p.getStatus() == 4) {
					status = "terminated";
					remaining = 0;
				}
				s += String.format("%15s%5s", status, remaining);
			}
			System.out.printf("Before cycle %3d:  %3s%n", cycle, s);

		}
	}
}
