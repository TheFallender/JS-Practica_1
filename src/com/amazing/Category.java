package com.amazing;

public class Category { //Category class
	
	private String name; //Name of the Category

	protected Category() { //Category basic Constructor
		this.name = Filter.filter_s("Name of the new category: "); //Gets the desired name
	}
	
	protected Category(String input) { 	//Category data Consturctor
		this.name = input; 					//Set name to the input
	}
	
	protected String r_name() { //Return the Category name
		return this.name;
	}
	
	protected void save() { //Saves the data on the file
		//String
		String[] aux = new String[] {"category=" + this.name}; //Auxiliar string to save
		
		//Write data
		IO.write("d_category", aux, true); //Writes the data on the file
	}
}
