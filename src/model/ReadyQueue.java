package model;

import java.util.ArrayList;

public class ReadyQueue {
    private ArrayList<Process> queue;         // array list to store queue elements

    // Constructor to initialize queue
    public ReadyQueue()
    {
        queue = new ArrayList<>();
    }

    // Utility function to remove front element from the queue
    public Process dequeue()
    {
        Process p = null;
        // check for queue underflow
        if (!isEmpty())
        {
            p = queue.get(0);
            queue.remove(p);
        }
        return p;
    }

    // Utility function to return front element in the queue
    public Process peek()
    {
        if(queue.isEmpty()){
            return null;
        }
        else{
            return queue.get(0);
        }
    }

    // Utility function to add an process to the queue by it's priority
    public void enqueue(Process process)
    {
        //case that the ready queue is empty
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
            //case that incoming process priority index is greater than priority of all processes
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

    // Utility function to return the size of the queue
    public int size()
    {
        return queue.size();
    }

    // Utility function to check if the queue is empty or not
    public Boolean isEmpty()
    {
        return (queue.size() == 0);
    }
}
