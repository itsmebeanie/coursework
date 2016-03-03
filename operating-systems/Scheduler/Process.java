/**
 * 
 * @author Kaitlin Gu
 * 
 *         Class that represents a process
 */
public class Process {
	// States
	public final static int UNSTARTED = 0;
	public final static int RUNNING = 1;
	public final static int BLOCKED = 2;
	public final static int READY = 3;
	public final static int END = 4;

	// Process attributes
	private int endTime = 0;
	private int arrivalTime = 0;
	private int cpuBurst = 0;
	private int currentcpuBurst = 0;
	private int ioBurst = 0;
	private int finishingTime = 0;
	private int waitTime = 0;
	private int priority = 0;
	private int status = UNSTARTED;
	private int totalCPUTime = 0;
	private int remainingCPUTime = 0;
	private int totalioTime = 0;
	private int curIOTime = 0;
	private int turnAroundTime = 0;
	private int rank = 0;

	// Getters and setters

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getTurnAroundTime() {
		return turnAroundTime;
	}

	public void setTurnAroundTime(int turnAroundTime) {
		this.turnAroundTime = turnAroundTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public int getCurIOTime() {
		return curIOTime;
	}

	public void setCurIOTime(int curIOTime) {
		this.curIOTime = curIOTime;
	}

	public int getCurrentcpuBurst() {
		return currentcpuBurst;
	}

	public void setCurrentcpuBurst(int currentcpuBurst) {
		this.currentcpuBurst = currentcpuBurst;
	}

	public int getTotalioTime() {
		return totalioTime;
	}

	public void setTotalioTime(int totalioTime) {
		this.totalioTime = totalioTime;
	}

	public int getTotalCPUTime() {
		return totalCPUTime;
	}

	public void setTotalCPUTime(int totalCPUTime) {
		this.totalCPUTime = totalCPUTime;
	}

	public int getRemainingCPUTime() {
		return remainingCPUTime;
	}

	public void setRemainingCPUTime(int remainingCPUTime) {
		this.remainingCPUTime = remainingCPUTime;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getCpuBurst() {
		return cpuBurst;
	}

	public void setCpuBurst(int cpuBurst) {
		this.cpuBurst = cpuBurst;
	}

	public int getIoBurst() {
		return ioBurst;
	}

	public void setIoBurst(int ioBurst) {
		this.ioBurst = ioBurst;
	}

	public int getFinishingTime() {
		return finishingTime;
	}

	public void setFinishingTime(int finishingTime) {
		this.finishingTime = finishingTime;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	// To String method for (A B C M)
	public String toString() {
		return "( " + this.arrivalTime + " " + this.cpuBurst + " " + this.remainingCPUTime + " " + this.ioBurst + " ) "
				+ this.getStatus();
	}

}
