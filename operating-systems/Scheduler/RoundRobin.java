import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
/** 
 * The scheduling algorithm used is Round Robin with a quantam of 2 
 * 
 * @author kaitlingu
 *
 */
public class RoundRobin {
	public static void main(String[] args) throws IOException {
		Scheduler rr = new Scheduler();
		int QUANTAM = 2;
		Scanner input2 = null;
		if (args[0].equals("--verbose")) {
			rr.verbose = true;
			rr.fileHandler(args[1]);
			input2 = new Scanner(new BufferedReader(new FileReader(args[2])));
		} else {
			rr.fileHandler(args[0]);
			input2 = new Scanner(new BufferedReader(new FileReader(args[1])));

		}

		ArrayList<Process> processList = rr.processList;
		rr.arrivalSort(processList);
		rr.setOrder(processList);

		Process p = null;
		int time = 0;
		int io = 0;
		ArrayList<Process> readyQueue = new ArrayList<Process>();

		rr.verbose(processList, time);

		// while the processes are not all terminated
		while (!rr.isDone(processList)) {

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

				QUANTAM = 2;
				// choose ready process to run
				p = readyQueue.get(0);
				readyQueue.remove(0);

				if (p.getCurrentcpuBurst() == 0) {
					// set its CPU burst and IO burst
					p.setCurrentcpuBurst(rr.randomOS(input2.nextInt(), p.getCpuBurst()));

					// if the cpu burst is larger than the remaining cpu time
					if (p.getCurrentcpuBurst() > p.getRemainingCPUTime()) {
						p.setCurrentcpuBurst(p.getRemainingCPUTime());
					}
					p.setCurIOTime(p.getCurrentcpuBurst() * p.getIoBurst());

				}

				// set io time

				// set status to running
				p.setStatus(p.RUNNING);

				// run until burst is over
				while (QUANTAM > 0) {
					time++;
					rr.verbose(processList, time);
					rr.addWaitTime(processList);
					// decrement while process is running
					boolean processBlocked = false;

					for (int j = 0; j < processList.size(); j++) {
						Process currentPro = processList.get(j);
						// if the status of the current process is blocked
						if (currentPro.getStatus() == currentPro.BLOCKED) {
							processBlocked = true;
							currentPro.setCurIOTime(currentPro.getCurIOTime() - 1);
							currentPro.setTotalioTime(currentPro.getTotalioTime() + 1);

							// incremement io time
							if (currentPro.getCurIOTime() == 0) {
								// set the status to ready if io time is up
								currentPro.setStatus(currentPro.READY);
							}
						}

					}
					if (processBlocked) {
						rr.IOUtilization++;
					}

					if (p.getRemainingCPUTime() > 0) {
						QUANTAM = QUANTAM - 1;
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
						p.setStatus(p.BLOCKED);
						break;
					}

					if (QUANTAM > 0) {
						// add to queue
						for (int i = 0; i < processList.size(); i++) {
							Process t = processList.get(i);
							if (!readyQueue.contains(t) && t.getArrivalTime() <= time
									&& (t.getStatus() == t.UNSTARTED || t.getStatus() == t.READY)) {
								t.setStatus(t.READY);
								readyQueue.add(t);
							}
						}
					}
				}
				if (p.getStatus() == p.RUNNING) {
					p.setStatus(p.READY);
					QUANTAM = 2;
				}
			}

			else {
				time++;
				rr.verbose(processList, time);
				rr.addWaitTime(processList);

				boolean processBlocked = false;
				// unblock the process
				for (int i = 0; i < processList.size(); i++) {
					Process t = processList.get(i);

					if (t.getStatus() == t.BLOCKED) {
						processBlocked = true;
						t.setCurIOTime(t.getCurIOTime() - 1);
						t.setTotalioTime(t.getTotalioTime() + 1);
						if (t.getCurIOTime() == 0) {
							t.setStatus(t.READY);
						}
					}
				}
				if (processBlocked) {
					rr.IOUtilization++;
				}
			}

		}
		rr.setFinishingTime(time);
		System.out.println("The scheduling algorithm used was Round Robin\n");
		rr.printProcesses();
		rr.printSummary();

	}

}