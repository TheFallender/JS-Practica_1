package amazing.inside;

public class Task {
	private String class_name = "";		//Class name
	private String method_name = "";	//Method name
	private Object[] parameters;		//Parameters
	private boolean complete = false;	//Task complete status
	
	protected Task (String cls_n, String mth_n, Object[] param) { //Task constructor
		this.class_name = cls_n;
		this.method_name = mth_n;
		this.parameters = param;
	}
	
	protected String get_class () { 	//Gets the class
		return this.class_name;
	}

	protected String get_method () {	//Gets the method
		return this.method_name;
	}
	
	protected Object[] get_param () {	//Gets the parameters
		return this.parameters;
	}
	
	protected boolean get_cmplt () {	//Gets the complete status
		return this.complete;
	}
	
	protected void set_completed () {	//Sets the complete status to true
		this.complete = true;
	}
}
