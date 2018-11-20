package com.amazing;

public class Category {
	private String name;

	protected Category() { //Constructor
		String[] aux = new String[] {"category=" + this.name};
		this.name = Filter.filter_s("Name of the new category: ");
		IO.write("d_category", aux, true);
	}
	
	protected Category(String input) { //Constructor for something defined
		this.name = input;
	}
	
	protected String r_name() { //Return a name
		return this.name;
	}
}
