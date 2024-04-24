import java.io.*;
import java.util.*;

public class Main {

	static int P1, P2, BT; //P1: length of queue 1, P2: length of queue 2, BT: sum of processes' burst time
	static int q1counter, q2counter; //keep track of the number of processes currently in queue 1 and queue 2.
	static PCB processes[], Q1[], Q2[]; 
	static LinkedList<String> ProcessesChart;
	static Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		int choice = 0;
		System.out.println("This program simulates how a CPU schedules tasks.");

		while(choice != 4) {
			System.out.println("What do you want to do next: ");
			System.out.println("1. Enter processes' information");
			System.out.println("2. Report detailed information about each process and different scheduling criteria.");
			System.out.println("3. Exit the program.");
			choice = input.nextInt();
			switch(choice) {
			case 1:
				ProcessInfo();
				break;
			case 2:
				if(processes == null) {
					System.out.println("you need to enter processes first.");
					break;
				}
                //PrintReport1();
				break;
			case 3:
				System.out.println("Goodbye.");
				break;
			default: choice = 0;
			}
		}
		input.close();
	}
    static void ProcessInfo() { //take user input and store it in processes array

        P1=0; P2=0; BT=0; q1counter=0; q2counter=0;
		ProcessesChart=null; processes=null; Q1=null; Q2=null;

       int numOfProcesses, priority, arrivalT, burstT;
       do {
        System.out.print("Enter the number of processes: ");
        numOfProcesses = input.nextInt();
    } while(numOfProcesses<=0);

    processes = new PCB[numOfProcesses];
    
    for (int i = 0; i < numOfProcesses; i++) {


        do {
            System.out.printf("Enter priority of process P%d: ", i);
            priority = input.nextInt();
        } while(priority != 1 && priority !=2); 

        do {
            System.out.printf("Enter arrival time of process P%d: ", i);
            arrivalT = input.nextInt();
        } while(arrivalT<0);

        do {
            System.out.printf("Enter burst time of process P%d: ", i);
            burstT = input.nextInt();
        } while(burstT<=0);

        BT += burstT;

        if (priority == 1)
            P1++;
        else
            P2++;

        processes[i] = new PCB(String.format("P%d", i), arrivalT, burstT, priority);// create PCB object and stores them in process array

    } // end for loop

    // create Q1 and Q2 array based on the priority
    Q1 = new PCB[P1];
    Q2 = new PCB[P2];
    MLQ(); // for scheduling the multilevel queue

    }

    static void MLQ() { //multilevel queue scheduler with fixed preemption
        ProcessesChart = new LinkedList<String>();
        int timer = 0;
    
        while(timer < BT){
            assign(timer);
            if(Q1.length > 0 && Q1[0] != null){
                if(Q2.length > 0 && Q2[0] != null && Q2[0].finishedBurst != 0)
                reschedule();
    
                Q1[0].setStartTime(timer);
                Q1[0].setTerminationTime(timer + Q1[0].getCpuBurst());
                Q1[0].setTurnaroundTime(Q1[0].getTerminationTime() - Q1[0].getArrivalTime());
                Q1[0].setWaitingTime(Q1[0].getTurnaroundTime() - Q1[0].getCpuBurst());
                Q1[0].setResponseTime(Q1[0].getStartTime() - Q1[0].getArrivalTime());
    
                int t = timer;
                timer += Q1[0].getCpuBurst();
    
                while(t < timer)
                ProcessesChart.add(t++, Q1[0].getProcessId());
    
                shift();
            }
            else if(Q2.length > 0 && Q2[0] != null) {
                if(Q2[0].finishedBurst == 0)
                    Q2[0].setStartTime(timer);
    
                ProcessesChart.add(timer++, Q2[0].getProcessId());
    
                if(Q2[0].finishedBurst < Q2[0].getCpuBurst())
                    Q2[0].finishedBurst++;
            
            
            if(Q2[0].finishedBurst == Q2[0].getCpuBurst()){
                Q2[0].setStartTime(timer);
                Q2[0].setTurnaroundTime(Q2[0].getTerminationTime() - Q2[0].getArrivalTime());
                Q2[0].setWaitingTime(Q2[0].getTurnaroundTime() - Q2[0].getCpuBurst());
                Q2[0].setResponseTime(Q2[0].getStartTime() - Q2[0].getArrivalTime());
                reschedule();
            }
        }
        else {
            ProcessesChart.add(timer++, "  ");
            BT++;
        }
    }
}
    static void assign(int arrivalTime){
        PCB arrivedQ[] = arrivedprocesses(arrivalTime);//arrivedprocesses from where did we get it ?
        int c = q2counter;
        for(PCB process : arrivedQ){
            if(Q1.length >0 && process.getPriority() == 1){//where did we get Priority?
                Q1[q1counter++] = process;}
                else if(Q2.length > 0){
                    Q2[q2counter++] = process;}
                    process.assigned = true; //assigned?
                }
              //SJF(); shorest job first call here
              RR();
            }
	 static void SJF() {
                int numberOfProcesses = q1counter;
                for (PCB process : Q2) {
                    int minIndex = -1;
                    int minBurstTime = Integer.MAX_VALUE;
                    for (int j = 0; j < numberOfProcesses; j++) {
                        if (Q2[j].getCpuBurst() < minBurstTime) {
                            minBurstTime = Q2[j].getCpuBurst();
                            minIndex = j;
                        }
                    }
                    PCB temp = Q2[numberOfProcesses - 1];
                    Q2[numberOfProcesses - 1] = Q2[minIndex];
                    Q2[minIndex] = temp;
                }
            }
            
        

    static void shift() {
		for(int i=1; i<q1counter; i++) {
			Q1[i-1] = Q1[i];
		}
		Q1[--q1counter] = null;
	}

    // another way for shiffting
    /**static void shift1() {
        // Loop from the second element to the second-last element (exclusive)
        for (int i = 1; i < q1counter - 1; i++) {
          // Overwrite the current element with the next element
          Q1[i] = Q1[i + 1];
        }
        
        // Set the last element to null
        Q1[q1counter - 1] = null;
        
        // Decrement the counter
        q1counter--;
      }**/

      static void reschedule() {
		PCB tempProcess = Q2[0];
		for(int i=1; i<q2counter; i++) { // for shiffting
			Q2[i-1] = Q2[i];
		}
        if(tempProcess.getCpuBurst() != tempProcess.finishedBurst) {
			Q2[q2counter-1] = tempProcess;
		}
		else {
			Q2[--q2counter] = null;
		}
	}

    static PCB[] arrivedprocesses(int arrival) {
		int i=0;
		for(PCB process : processes) {
			if(process.getArrivalTime()<=arrival && !process.assigned) {
				i++;
			}
		}

		PCB arrived[] = new PCB[i];
		i=0;
		for(PCB process : processes) {
			if(process.getArrivalTime()<=arrival && !process.assigned) {
				arrived[i++] = process;
			}
		}

		return arrived;
	}
    
    static void RR() {
        int q = 3; //quantum 

        while (q1counter != 0) {
            PCB processRR = Q1[0];

            if (processRR.getCpuBurst() <= q) {
            
            //Termination Time :
            processRR.setTerminationTime(processRR.getStartTime() + processRR.getCpuBurst());
            //Turnaround Time :
            processRR.setTurnaroundTime(processRR.getTerminationTime() - processRR.getArrivalTime());
            //waiting Time :
            processRR.setWaitingTime(processRR.getTurnaroundTime() - processRR.getCpuBurst());
            //Response Time :
            processRR.setResponseTime(processRR.getStartTime() - processRR.getArrivalTime());
            processRR.setCpuBurst(0);

            shift();
            } // if end
            else
            {
            processRR.setCpuBurst(processRR.getCpuBurst() - q);
            processRR.setStartTime(processRR.getStartTime() + q);
            shift();
            Q1[q1counter++] = processRR;

            } // else end

    } // while end

}// RR end


}
