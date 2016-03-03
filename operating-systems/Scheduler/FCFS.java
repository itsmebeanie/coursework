import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class represents scheduling algorithm First Come First Serve in which
 * processes that come first have the highest priority
 * 
 * @author Kaitlin Gu
 *
 */
public class FCFS {
	public static void main(String[] args) throws IOException {
		Scheduler FcfsScheduler = new Scheduler();
		Scanner input2 = null;

		// file handling
		if (args[0].equals("--verbose")) {
			FcfsScheduler.verbose = true;
			FcfsScheduler.fileHandler(args[1]);
			input2 = new Scanner(new BufferedReader(new FileReader(args[2])));

		} else {
			FcfsScheduler.fileHandler(args[0]);
			input2 = new Scanner(new BufferedReader(new FileReader(args[1])));

		}
		// process list
		ArrayList<Process> processList = FcfsScheduler.processList;
		FcfsScheduler.arrivalSort(processList);

		Process p = null;
		int time = 0;

		// ready processes
		ArrayList<Process> readyQueue = new ArrayList<Process>();

		FcfsScheduler.verbose(processList, time);

		// while the processes are not all terminated
		while (!FcfsScheduler.isDone(processList)) {

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
				// choose ready process to run
				p = readyQueue.get(0);
				readyQueue.remove(0);

				// set its CPU burst and IO burst
				p.setCurrentcpuBurst(FcfsScheduler.randomOS(input2.nextInt(), p.getCpuBurst()));

				// if the cpu burst is larger than the remaining cpu time
				if (p.getCurrentcpuBurst() > p.getRemainingCPUTime()) {
					p.setCurrentcpuBurst(p.getRemainingCPUTime());
				}

				// set io time
				p.setCurIOTime(p.getCurrentcpuBurst() * p.getIoBurst());
				// set status to running
				p.setStatus(p.RUNNING);

				// run until burst is over
				while (p.getCurrentcpuBurst() > 0) {
					time++;
					FcfsScheduler.verbose(processList, time);
					FcfsScheduler.addWaitTime(processList);
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
						FcfsScheduler.IOUtilization++;
					}

					// add to queue
					for (int i = 0; i < processList.size(); i++) {
						Process t = processList.get(i);
						if (!readyQueue.contains(t) && t.getArrivalTime() <= time
								&& (t.getStatus() == t.UNSTARTED || t.getStatus() == t.READY)) {
							t.setStatus(t.READY);
							readyQueue.add(t);
						}
					}
					// as long as the remaining cpu time is greater than 0
					// continue to decrement
					if (p.getRemainingCPUTime() > 0) {
						p.setCurrentcpuBurst(p.getCurrentcpuBurst() - 1);
						p.setRemainingCPUTime(p.getRemainingCPUTime() - 1);
						if (p.getRemainingCPUTime() == 0) {
							p.setStatus(p.END);
							p.setFinishingTime(time);
							p.setTurnAroundTime(p.getFinishingTime() - p.getArrivalTime());
							break;
						}
					}
				}

				// if state == running, make it blocked
				if (p.getStatus() == p.RUNNING)
					p.setStatus(p.BLOCKED);

			} else {
				time++;
				FcfsScheduler.verbose(processList, time);
				FcfsScheduler.addWaitTime(processList);

				boolean processBlocked = false;

				// unblock the process and decrement io time
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
					FcfsScheduler.IOUtilization++;
				}
			}

		}
		FcfsScheduler.setFinishingTime(time);
		System.out.println("The scheduling algorithm used was First Come First Serve\n");
		FcfsScheduler.printProcesses();
		FcfsScheduler.printSummary();

	}

}