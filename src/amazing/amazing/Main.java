package amazing.amazing;

import java.util.ArrayList;

import amazing.inside.Custom_Exception;
import amazing.inside.Localization;
import amazing.inside.Task_ex;

public class Main {
	private static ArrayList<Thread> thread_l = new ArrayList<>();
	
	public static void main (String[] args) {
		//Define the runnable tasks
		Runnable main_run = new Amazing();
		Runnable task_run = new Task_ex();
		
		//Add the threads into the arraylist
		thread_l.add(new Thread(main_run));
		thread_l.add(new Thread(task_run));
		
		//Starts the threads
		thread_l.get(0).start();
		thread_l.get(1).start();

		//Checks until the main thread dies and then closes the application
		while (thread_l.get(0).isAlive()) { //Main thread alive
			while (!Task_ex.task_l_empty()) {	//Task thread is NOT empty
				notify_thread(1);					//Wakes up the Task thread
				thread_wait(0, 0);					//Makes the Main thread wait
				if (Task_ex.get_completed())		//If the task is completed
					Task_ex.ack_cmplt();				//Acknowledges the action
			}
		}
		
		while (thread_l.get(1).isAlive()) { //Wait for Task thread to die
			notify_thread(1);					//Wakes up the Task thread
		}
	}
	
	public static boolean thread_alive (int thread_num) { //Thread alive return
		return thread_l.get(thread_num).isAlive();			//Returns boolean
	}
	
	public static void thread_wait (int thread_num, int time) { //Makes the selected thread wait
		synchronized (thread_l.get(thread_num)) {					//Synchronizes with the selected thread
			try {
				thread_l.get(thread_num).wait(time);					//Waits selected amount of time
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();												//Catches interruption
				throw new Custom_Exception(Localization.get("inside", "thread_interrupt"), e); 	//Throw exception
			}
		}
	}
	
	public static void notify_thread (int thread_num) {	//Notifies the selected thread
		synchronized (thread_l.get(thread_num)) {			//Synchronizes with the selected thread
			thread_l.get(thread_num).notify();					//Notifies the thread
		}
	}
}
