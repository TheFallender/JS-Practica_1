package amazing.inside;

public class Task {
	private String class_name = "";
	private String method_name = "";
	private Object[] parameters;
	private boolean complete = false;
	
	protected Task (String cls_n, String mth_n, Object[] param) {
		this.class_name = cls_n;
		this.method_name = mth_n;
		this.parameters = param;
	}
	
	protected String get_class () {
		return this.class_name;
	}

	protected String get_method () {
		return this.method_name;
	}
	
	protected Object[] get_param () {
		return this.parameters;
	}
	
	protected boolean get_cmplt () {
		return this.complete;
	}
	
	protected void set_completed () {
		this.complete = true;
	}
}
