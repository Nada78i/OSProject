/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author saraalmogren
 */
public class PCB {

    private String processId;

    private int priority;

    private int arrivalTime;

    private int cpuBurst;

    private int startTime;

    private int terminationTime;

    private int turnaroundTime;

    private int waitingTime;

    private int responseTime;

    int finishedBurst=0;
    boolean assigned=false;



    public PCB(String processId, int priority, int arrivalTime, int cpuBurst) {

        this.processId = processId;

        this.priority = priority;

        this.arrivalTime = arrivalTime;

        this.cpuBurst = cpuBurst;

        this.startTime = 0;

        this.terminationTime = 0;

        this.turnaroundTime = 0;

        this.waitingTime = 0;

        this.responseTime = 0;

    }



    // Add getters and setters.
    public String getProcessId() {
        return processId;
   }

    public int getPriority() {
        return priority; }
        

    public int getArrivalTime() {
        return arrivalTime;}



    public int getCpuBurst() {

        return cpuBurst;

    }



    public int getStartTime() {

        return startTime;

    }



    public void setStartTime(int startTime) {

        this.startTime = startTime;

    }



    public int getTerminationTime() {

        return terminationTime;

    }



    public void setTerminationTime(int terminationTime) {

        this.terminationTime = terminationTime;

    }



    public int getTurnaroundTime() {

        return turnaroundTime;

    }



    public void setTurnaroundTime(int turnaroundTime) {

        this.turnaroundTime = turnaroundTime;

    }



    public int getWaitingTime() {

        return waitingTime;

    }



    public void setWaitingTime(int waitingTime) {

        this.waitingTime = waitingTime;

    }



    public int getResponseTime() {

        return responseTime;

    }



    public void setResponseTime(int responseTime) {

        this.responseTime = responseTime;

    }
    
    @Override
    public String toString(){
        return String.format("ProcessID: %s \nPriority: %d \nArrivalTime: %d \nCpuBurst: %d \nStartTime: %d \nTerminationTime: %d \nTurnAroundTime: %d \nWaitingTime: %d \nResponseTime: %d\n", 
               processId, priority, arrivalTime, cpuBurst, startTime, terminationTime, turnaroundTime, waitingTime, responseTime);
    }

}
