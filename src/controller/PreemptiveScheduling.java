package controller;

import model.GanttRecord;
import model.Process;
import model.ReadyQueue;

import java.util.ArrayList;

public class PreemptiveScheduling {
    //Gantt
    private ArrayList<GanttRecord> gantt;
    private int currentTime;
    private int exeTime;
    private ReadyQueue readyQueue;

    public PreemptiveScheduling(){
        gantt = new ArrayList<>();
        currentTime = 0;
        exeTime = 0;
        readyQueue = new ReadyQueue();
    }

    public ArrayList<GanttRecord> getGantt(ArrayList<Process> processes){
        //inicializimi i koheve fillestare
        currentTime = this.getFirstArrivingTime(processes);
        int in = currentTime ,out = currentTime;

        //shtohen proceset e para ne radhen gati
        ArrayList<Process> processes1 = this.getFirstArrivingProcesses(processes);

        for(Process process : processes1){
            //shtimi ne radhen gati eshte ne baze te prioritetit
            readyQueue.enqueue(process);
            processes.remove(process);
        }

        //renditen proceset e mbetura ne varesi te kohes se mberitjes
        ArrayList<Process> orderedByArrivingTime = this.orderProcessesByArrivingTime(processes);

        //sa kohe qe radha gati nuk eshte bosh
        while(!readyQueue.isEmpty()){
            //merret procesi me prioritetin me te madhe nga radha gati
            Process process = readyQueue.dequeue();


            //Dy raste qe duhet te merren parasysh, i pari eshte kur kemi procese te reja qe vijne dhe rasti tjeter
            //eshte kur nuk vijne procese te reja por trajtohen ato qe jane ne radhen gati
            if(orderedByArrivingTime.size() > 0) {
                //Handle of all arriving processes while one process has the control of CPU
                for (int i = 0; i < orderedByArrivingTime.size(); i++) {
                    Process p = orderedByArrivingTime.get(i);
                    //procesi i ri qe vjen kur CPU eshte e zene i krahasohet prioriteti dhe nese prioriteti eshte me
                    //i ulet se prioriteti i procesit qe ka kontrollin atehere procesi i ri shtohet ne radhen gati
                    if (p.getArrivingTime() >= process.getArrivingTime()
                            && p.getArrivingTime() < (process.getBurstTime() + currentTime)
                            && p.getPriority() >= process.getPriority()) {
                        readyQueue.enqueue(p);
                        orderedByArrivingTime.remove(p);
                        i--;
                    }
                    //procesi i ri qe vjen kur CPU eshte e zene i krahasohet prioriteti dhe nese prioriteti eshte me
                    //i madh se prioriteti i procesit qe ka kontrollin atehere procesi i vjeter shtohet ne radhen gati
                    //me burst time te reduktuar, kontrollin e CPU e merr procesi i ri
                    else if (p.getArrivingTime() >= process.getArrivingTime()
                            && p.getArrivingTime() < (process.getBurstTime() + currentTime)
                            && p.getPriority() < process.getPriority()) {
                        in = currentTime;
                        currentTime = p.getArrivingTime();
                        process.reduceTime(currentTime - in);
                        out = currentTime;
                        readyQueue.enqueue(process);
                        GanttRecord gR = new GanttRecord(in, out, process.getProcessID());
                        gantt.add(gR);

                        readyQueue.enqueue(p);
                        orderedByArrivingTime.remove(p);
                        i--;

                        break;
                    }
                    //nese kontrollohet e gjithe lista me procese te reja qe vijne dhe asnjera prej tyre nuk eshte valide
                    //per te marre kontrollin e CPU, procesi qe ka kontrollin vijon me kohen qe i duhet
                    if (i == orderedByArrivingTime.size() - 1) {
                        in = currentTime;
                        currentTime += process.getBurstTime();
                        out = currentTime;
                        gantt.add(new GanttRecord(in, out, process.getProcessID()));
                        //kontrollohet nese ne fund te ekzekutimit pa nderprerje te nje procesi kemi nje proces te ri qe
                        //vjen dhe shtohet ne radhen gati
                        if(orderedByArrivingTime.size() > 0
                                && readyQueue.isEmpty()) {
                            readyQueue.enqueue(orderedByArrivingTime.get(0));
                        }
                    }
                }
            }
            //rasti tjeter kur nuk kemi me procese te reja qe vijojne por trajtohen vetem ato qe ndodhen ne radhen gati
            else{
                in = currentTime;
                currentTime += process.getBurstTime();
                out = currentTime;
                gantt.add(new GanttRecord(in, out, process.getProcessID()));
            }
        }
        return gantt;
    }

    public static int getCompletionTime(Process p, ArrayList<GanttRecord> gantt) {
        int completionTime = 0;
        for(GanttRecord gR : gantt){
            if(gR.getProcessId() == p.getProcessID())
                completionTime = gR.getOutTime();
        }
        return completionTime;
    }

    public static int getTurnAroundTime(Process p, ArrayList<GanttRecord> gantt) {
        int completionTime = PreemptiveScheduling.getCompletionTime(p,gantt);
        return completionTime-p.getArrivingTime();
    }

    public static int getWaitingTime(Process p, ArrayList<GanttRecord> gantt) {
        int turnAroundTime = PreemptiveScheduling.getTurnAroundTime(p,gantt);
        return turnAroundTime-p.getBurstTime();
    }

    private ArrayList<Process> orderProcessesByArrivingTime(ArrayList<Process> processes){
        ArrayList<Process> newProcesses = new ArrayList<>();
        while(processes.size() != 0) {
            Process p = this.getFirstArrivingProcess(processes);
            processes.remove(p);
            newProcesses.add(p);
        }
        return newProcesses;
    }

    private Process getFirstArrivingProcess(ArrayList<Process> processes){
        int min = Integer.MAX_VALUE;
        Process process = null;
        for(Process p : processes){
            if(p.getArrivingTime() < min){
                min = p.getArrivingTime();
                process = p;
            }
        }
        return process;
    }

    private ArrayList<Process> getFirstArrivingProcesses(ArrayList<Process> processes){
        int min = this.getFirstArrivingTime(processes);
        ArrayList<Process> processes1 = new ArrayList<>();
        for(Process p : processes){
            if(p.getArrivingTime() == min){
                processes1.add(p);
            }
        }
        return processes1;
    }

    private int getFirstArrivingTime(ArrayList<Process> processes){
        int min = Integer.MAX_VALUE;
        for(Process p : processes){
            if(p.getArrivingTime() < min){
                min = p.getArrivingTime();
            }
        }
        return min;
    }

}