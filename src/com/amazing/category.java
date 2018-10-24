package com.amazing;

public class category {
	private String name;

	protected category() { //Constructor
		String aux[] = new String[1];
		this.name = filter.filter_s("Name of the new category: ");
		aux[0] = this.name;
		io_text.write("d_category", aux);
	}
	
	protected category(String input) { //Constructor for something defined
		this.name = input;
	}
	
	protected String r_name() { //Return a name
		return this.name;
	}
}
