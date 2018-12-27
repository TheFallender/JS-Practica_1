package amazing.inside;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import amazing.amazing.Main;

public class Task_ex implements Runnable{
	private static ArrayList<Task> task_l = new ArrayList<>();	//Tasks list
	
	public static boolean get_completed () { //Get the completed status
		return task_l.get(0).get_cmplt();
	}

	public static void ack_cmplt () { //Acknowledges the complete status
		if (task_l.get(0).get_cmplt())	//Checks if the task is completed
			task_l.remove(0);				//Removes the completed task
	}
	
	public static void task_add (String s_class, String s_method, Object[] params) { //Add task
		Task aux = new Task(s_class, s_method, params);									//Creates the auxiliary task
		task_l.add(aux);																//Adds the auxiliary task
	}
	
	public static boolean task_l_empty () {	//Returns task list empty status
		return (task_l.isEmpty());
	}
	
	@Override
	public void run () { //Task to run
		while (true) { //Main thread alive, if not, close this one
			if (!task_l.isEmpty() && !task_l.get(0).get_cmplt()) { //There is a task and is not completed
				try {
					//Parameters find
			        Class<?>[] param_type = new Class[task_l.get(0).get_param().length];
			        for (int i = 0; i < task_l.get(0).get_param().length; i++) { //Sets the parameters type based on the parameter of the task
			            if 		(task_l.get(0).get_param()[i] instanceof Boolean)
			            	param_type[i] = Boolean.TYPE;
			            else if (task_l.get(0).get_param()[i] instanceof Integer)
			            	param_type[i] = Integer.TYPE;
			            else if (task_l.get(0).get_param()[i] instanceof Float)
			            	param_type[i] = Float.TYPE;
			            else if (task_l.get(0).get_param()[i] instanceof String)
			            	param_type[i] = String.class;
			            else if (task_l.get(0).get_param()[i] instanceof String[])
			            	param_type[i] = String[].class;

			        }
					
			        //Method find
					Class<?> 	ref_class = Class.forName(task_l.get(0).get_class());						//Finds the class
					Method 		ref_method = ref_class.getMethod(task_l.get(0).get_method(), param_type);	//Finds the method
					
					//Method invoke
					ref_method.invoke(null, task_l.get(0).get_param());										//Calls the method
					
					//Checks the completed mark
					task_l.get(0).set_completed();
					
				} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new Custom_Exception(Localization.get("inside", "thread_method"), e); //Throw exception
				}
			}
			
			while (task_l.isEmpty() || task_l.get(0).get_cmplt()) { //Waits for the next task
				if (!Main.thread_alive(0))
					return;
				Main.notify_thread(0); 									//Notifies main thread
				Main.thread_wait(1, 0);									//Makes this thread to wait
			}
		}
	}
}
