import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Uniprogrammed {
	public static void main(String[] args) throws IOException {
		Scheduler uni = new Scheduler();
		Scanner input2 = null;
		if (args[0].equals("--verbose")) {
			uni.verbose = true;
			uni.fileHandler(args[1]);
			input2 = new Scanner(new BufferedReader(new FileReader(args[2])));

		} else {
			uni.fileHandler(args[0]);
			input2 = new Scanner(new BufferedReader(new FileReader(args[1])));

		}

		ArrayList<Process> processList = uni.processList;
		uni.arrivalSort(processList);
		Process p = null;
		int time = 0;
		ArrayList<Process> readyQueue = new ArrayList<Process>();

		uni.verbose(processList, time);
		uni.addWaitTime(processList);

		// while the processes are not all terminated
		while (!uni.isDone(processList)) {

			// add processes to queue
			for (int i = 0; i < processList.size(); i++) {
				Process t = processList.get(i);
				if (!readyQueue.contains(t) && t.getArrivalTime() <= time
						&& (t.getStatus() == t.UNSTARTED || t.getStatus() == t.READY)) {
					// processes are ready
					t.setStatus(t.READY);
					readyQueue.add(t);
				}
			}

			// there are processes that are ready to run
			if (!readyQueue.isEmpty()) {

				// FcfsScheduler.verbose(processList, time);
				// choose ready process to run
				p = readyQueue.get(0);
				readyQueue.remove(0);

				// set its CPU burst and IO burst
				p.setCurrentcpuBurst(uni.randomOS(input2.nextInt(), p.getCpuBurst()));

				// if the cpu burst is larger than the remaining cpu time
				if (p.getCurrentcpuBurst() > p.getRemainingCPUTime()) {
					p.setCurrentcpuBurst(p.getRemainingCPUTime());
				}

				// set io time
				p.setCurIOTime(p.getCurrentcpuBurst() * p.getIoBurst());
				// set status to running
				p.setStatus(p.RUNNING);

				// run until burst is over
				while (p.getRemainingCPUTime() > 0) {
					// add processes to queue
					for (int i = 0; i < processList.size(); i++) {
						Process t = processList.get(i);
						if (!readyQueue.contains(t) && t.getArrivalTime() <= time && (t.getStatus() == t.UNSTARTED)) {
							// processes are ready
							t.setStatus(t.READY);
							readyQueue.add(t);
						}
					}

					if (p.getCurrentcpuBurst() > 0) {
						time++;
						uni.verbose(processList, time);
						uni.addWaitTime(processList);
						p.setCurrentcpuBurst(p.getCurrentcpuBurst() - 1);
						p.setRemainingCPUTime(p.getRemainingCPUTime() - 1);

					}
					if (p.getRemainingCPUTime() == 0) {
						p.setStatus(p.END);
						p.setFinishingTime(time);
						p.setTurnAroundTime(p.getFinishingTime() - p.getArrivalTime());
						break;
					}

					if (p.getCurrentcpuBurst() == 0) {
						// get new random io
						p.setStatus(p.BLOCKED);
						while (p.getCurIOTime() > 0) {
							uni.IOUtilization++;
							for (int i = 0; i < processList.size(); i++) {
								Process t = processList.get(i);
								if (!readyQueue.contains(t) && t.getArrivalTime() <= time
										&& (t.getStatus() == t.UNSTARTED)) {
									// processes are ready
									t.setStatus(t.READY);
									readyQueue.add(t);
								}
							}
							time++;

							uni.verbose(processList, time);
							uni.addWaitTime(processList);
							p.setCurIOTime(p.getCurIOTime() - 1);
							p.setTotalioTime(p.getTotalioTime() + 1);

						}
						p.setCurrentcpuBurst(uni.randomOS(input2.nextInt(), p.getCpuBurst()));

					}
					if (p.getCurIOTime() == 0) {
						p.setCurIOTime(p.getCurrentcpuBurst() * p.getIoBurst());

						p.setStatus(p.RUNNING);
					}
				}
			}

		}

		uni.setFinishingTime(time);
		System.out.println("The scheduling algorithm used was Uniprocessing\n");
		uni.printProcesses();
		uni.printSummary();

	}

}
