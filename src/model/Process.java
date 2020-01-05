package model;

public class Process {
    private int processID;
    private int priority;
    private int arrivingTime;
    private int burstTime;

    public Process() {
        this.processID = 0;
        this.priority = 0;
        this.arrivingTime = 0;
        this.burstTime = 0;
    }

    public Process(int processID, int priority, int arrivingTime, int burstTime) {
        this.processID = processID;
        this.priority = priority;
        this.arrivingTime = arrivingTime;
        this.burstTime = burstTime;
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getArrivingTime() {
        return arrivingTime;
    }

    public void setArrivingTime(int arrivingTime) {
        this.arrivingTime = arrivingTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    @Override
    public String toString() {
        return "{" +
                "processID = " + processID +
                ", priority = " + priority +
                ", arrivingTime = " + arrivingTime +
                ", burstTime = " + burstTime +
                '}';
    }

    public void reduceTime(int time) {
        if(burstTime >= time)
            burstTime = burstTime - time;
    }
}
