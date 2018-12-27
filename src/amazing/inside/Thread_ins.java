package amazing.inside;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Thread_ins extends Thread{
	private static ArrayList<Task> task_l = new ArrayList<>();	//Tasks list
	
	public static boolean get_completed () { //Get the completed status
		return task_l.get(0).get_cmplt();
	}

	public static void ack_cmplt () {
		if (task_l.get(0).get_cmplt())
			task_l.remove(0);
	}
	
	public static void task_add (String s_class, String s_method, Object[] params) { //Add task
		Task aux = new Task(s_class, s_method, params);
		task_l.add(aux);
	}
	
	@Override
	public synchronized void run () {
		if (!task_l.isEmpty()) {
			try {
				Class<?> ref_class = Class.forName(task_l.get(0).get_class());
				Method ref_method = ref_class.getMethod(task_l.get(0).get_method());
				ref_method.invoke(null, task_l.get(0).get_param());
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new Custom_Exception(Localization.get("inside", "thread_method"), e); //Throw exception
			}
			task_l.get(0).set_completed();
		}
		
		while (task_l.isEmpty())
			try {
				this.wait(100);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new Custom_Exception(Localization.get("inside", "thread_interrupt"), e); //Throw exception
			}
	}
}
