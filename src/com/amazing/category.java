package com.amazing;

public class category {
	private String name;

	protected category() { //Constructor
		this.name = filter.filter_s("Name of the new category: ");
	}
	
	protected category(String input) { //Constructor for something defined
		this.name = input;
	}
	
	protected String name() { //Return a name
		return this.name;
	}
}
