import java.io.*;
import java.util.*;

public class Main {

	static int P1, P2, BT; //P1: length of queue 1, P2: length of queue 2, BT: sum of processes' burst time
	static int q1counter, q2counter; //number of processes in each queue
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
                PrintReport1();
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

        processes[i] = new PCB(String.format("P%d", i), arrivalT, burstT, priority);

    } // end for loop
    Q1 = new PCB[P1];
    Q2 = new PCB[P2];
    MLQ();

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
            Q1[0].setTerminationTime(timer + Q1[0].getBurstT());
            Q1[0].setTurnAroundTime(Q1[0].getTerminationTime() - Q1[0].getArrivalT());
            Q1[0].setWaitingTime(Q1[0].getTurnAroundTime() - Q1[0].getBurstT());
            Q1[0].setResponseTime(Q1[0].getStartTime() - Q1[0].getArrivalT());

            int t = timer;
            timer += Q1[0].getBurstT();

            while(t < timer)
            ProcessesChart.add(t++, Q1[0].getPID());

            shift();
        }
        else if(Q2[0]finishedBurst == Q2[0].getBurstT()){
            Q2[0].setStartTime(timer);
            Q2[0].setTurnAroundTime(Q2[0].getTerminationTime() - Q2[0].getArrivalT());
            Q2[0].setWaitingTime(Q2[0].getTurnAroundTime() - Q2[0].getBurstT());
            Q2[0].setResponseTime(Q2[0].getStartTime() - Q2[0].getArrivalT());
            reschedule();
        }
    }
    else{
        ProcessesChart.add(timer++, "");//why after not before?
        BT++;

    }
    }

    static void assign(int arrivalTime){
        PCB arrivedQ[] = arrivedprocesses(arrival);//arrivedprocesses from where did we get it ?
        int c = q2counter;
        for(PCB process : arrivedQ){
            if(Q1.length >0 && process.getPriority() == 1){//where did we get Priority?
                q1[q1counter++] = process;}
                else if(Q2.length > 0){
                    Q2[q2counter++] = process;}
                    process.assigned = true; //assigned?
                }
              SJF();// shorest job first call here
              RR();//round robuin call here  
            }
        }

    


