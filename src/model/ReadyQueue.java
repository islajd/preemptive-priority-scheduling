package model;

import java.util.ArrayList;

public class ReadyQueue {
    private ArrayList<Process> queue;

    public ReadyQueue()
    {
        queue = new ArrayList<>();
    }

    public Process dequeue()
    {
        Process p = null;
        if (!isEmpty())
        {
            p = queue.get(0);
            queue.remove(p);
        }
        return p;
    }

    public Process peek()
    {
        if(queue.isEmpty()){
            return null;
        }
        else{
            return queue.get(0);
        }
    }

    public void enqueue(Process process)
    {
        //rasti kur radha eshte bosh
        if (queue.isEmpty()) {
            queue.add(process);
        }
        else if(!this.contain(process)){
            int i;
            for (i = 0; i < queue.size(); i++) {
                if (queue.get(i).getPriority() > process.getPriority()) {
                    queue.add(i,process);
                    break;
                }
            }
            //rasti kur prioriteti i procesit qe shtohet eshte me i larte se i te gjithe proceseve
            if(i == queue.size() ){
                queue.add(process);
            }
        }
    }

    private boolean contain(Process process){
        for(Process p : queue){
            if(p.getProcessID() == process.getProcessID())
                return true;
            return false;
        }
        return false;
    }

    public int size()
    {
        return queue.size();
    }

    public Boolean isEmpty()
    {
        return (queue.size() == 0);
    }
}
