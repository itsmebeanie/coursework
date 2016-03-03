import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class represents scheduling algorithm Shortest Job First in which the jobs
 * with the shortest CPU time have the highest priority
 * 
 * @author Kaitlin Gu
 *
 */
public class SJF {
	public static void main(String[] args) throws IOException {

		// File handling
		Scheduler sjf = new Scheduler();
		Scanner input2 = null;
		if (args[0].equals("--verbose")) {
			sjf.verbose = true;
			sjf.fileHandler(args[1]);
			input2 = new Scanner(new BufferedReader(new FileReader(args[2])));
		} else {
			sjf.fileHandler(args[0]);
			input2 = new Scanner(new BufferedReader(new FileReader(args[1])));

		}

		ArrayList<Process> processList = sjf.processList;
		sjf.arrivalSort(processList);

		Process p = null;
		int time = 0;
		ArrayList<Process> readyQueue = new ArrayList<Process>();

		sjf.verbose(processList, time);

		// while the processes are not all terminated
		while (!sjf.isDone(processList)) {

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

			// sort by cpu time
			sjf.sortTime(readyQueue);

			// there are processes that are ready to run
			if (!readyQueue.isEmpty()) {
				// FcfsScheduler.verbose(processList, time);
				// choose ready process to run
				p = readyQueue.get(0);
				readyQueue.remove(0);

				// set its CPU burst and IO burst
				p.setCurrentcpuBurst(sjf.randomOS(input2.nextInt(), p.getCpuBurst()));

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
					sjf.verbose(processList, time);
					sjf.addWaitTime(processList);
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
						sjf.IOUtilization++;
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
				sjf.verbose(processList, time);
				sjf.addWaitTime(processList);
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
					sjf.IOUtilization++;
				}
			}

		}
		sjf.setFinishingTime(time);
		System.out.println("The scheduling algorithm used was Shortest Job First\n");
		sjf.printProcesses();
		sjf.printSummary();

	}

}