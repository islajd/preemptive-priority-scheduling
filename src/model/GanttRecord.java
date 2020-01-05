package model;

public class GanttRecord {
    private int inTime;
    private int outTime;
    private int processId;

    public GanttRecord() {
        inTime = 0;
        outTime = 0;
        processId = 0;
    }

    public GanttRecord(int inTime, int outTime, int processId) {
        this.inTime = inTime;
        this.outTime = outTime;
        this.processId = processId;
    }

    public int getInTime() {
        return inTime;
    }

    public void setInTime(int inTime) {
        this.inTime = inTime;
    }

    public int getOutTime() {
        return outTime;
    }

    public void setOutTime(int outTime) {
        this.outTime = outTime;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    @Override
    public String toString() {
        return "|" + inTime + "| --P" + processId + "-- |" + outTime + "|";
    }
}
